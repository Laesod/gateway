package com;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.utils.SecurityContextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Configuration // needed to specify that the class contains global spring configurations
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories // needed to enable JPA based repositories
@EnableTransactionManagement
@RestController // needed to enable rest end points within the class
@EnableGlobalMethodSecurity(securedEnabled=true) // needed for method based security (@Secured("ROLE_ADMIN") annotation))
@EnableJpaAuditing//needed to activate auditing(automatically managed fields createdBy, createdDate, lastModifiedBy, lastModifiedDate)

public class GatewayApplication extends SpringBootServletInitializer {
	@Value("${db.type}")
	private String dbType;

	@Value("${db.dialect}")
	private String dbDialect;

	@Value("${db.driver}")
	private String dbDriver;

	@Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${mailserver.host}")
    private String mailHost;

    @Value("${mailserver.port}")
    private int mailPort;

	@Value("${mailserver.protocol}")
	private String mailProtocol;

	@Value("${mailserver.ssl.enable}")
	private String mailEnableSSL;

    @Value("${mailserver.username}")
    private String mailUsername;

    @Value("${mailserver.password}")
	private String mailPassword;

	@Value("${mailserver.debug}")
	private String mailDebug;

    private class SpringSecurityAuditorAware implements AuditorAware<String> {
		@Override
		public String getCurrentAuditor() {
			SecurityContextReader securityContextReader = new SecurityContextReader();
			return securityContextReader.getUsername();
		}
	}

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(dbDriver);
        ds.setUrl(dbUrl);
        ds.setUsername(dbUsername);
        ds.setPassword(dbPassword);

        return ds;
    }

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new SpringSecurityAuditorAware();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.valueOf(dbType));
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform(dbDialect);
		return adapter;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.valueOf(dbType));
		vendorAdapter.setShowSql(true);
		vendorAdapter.setGenerateDdl(false); //true value not for production !!! update db after entityManager instantiation based on entities
		vendorAdapter.setDatabasePlatform(dbDialect);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.entity");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:ValidatorMessages");
		factory.setValidationMessageSource(messageSource);
		return factory;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
		cookieLocaleResolver.setCookieName("appLanguage");
		cookieLocaleResolver.setCookieMaxAge(604800);//one month
		return cookieLocaleResolver;
	}

	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages");
		// if true, the key of the message will be displayed if the key is not
		// found, instead of throwing a NoSuchMessageException
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : never reload, 0 always reload
		messageSource.setCacheSeconds(0);
		return messageSource;
	}

	@Bean
	public JavaMailSender mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(mailHost);
		mailSender.setPort(mailPort);
		mailSender.setProtocol(mailProtocol);
		mailSender.setUsername(mailUsername);
		mailSender.setPassword(mailPassword);

		Properties properties = new Properties();
        properties.setProperty("mail.smtps.auth", "true");
		properties.setProperty("mail.smtp.ssl.enable", mailEnableSSL);
		properties.setProperty("mail.transport.protocol", mailProtocol);
		properties.setProperty("mail.debug", mailDebug);
		mailSender.setJavaMailProperties(properties);

		return mailSender;
	}

	@Bean
	public ClassLoaderTemplateResolver emailTemplateResolver() {
		ClassLoaderTemplateResolver resolver =
				new ClassLoaderTemplateResolver();
		resolver.setPrefix("emailTemplates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		return engine;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GatewayApplication.class);
	}

	public static void main(String[] args) {
		File pid = new File("app.pid");
		//pid.deleteOnExit();

		SpringApplication app = new SpringApplication(GatewayApplication.class);
		app.setShowBanner(false);
		app.addListeners(new ApplicationPidFileWriter(pid));

		app.run(args);
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			AlwaysSendUnauthorized401AuthenticationEntryPoint alwaysSendUnauthorized401AuthenticationEntryPoint = new AlwaysSendUnauthorized401AuthenticationEntryPoint();
			//2419200 means 4 weeks
			http
					.formLogin()
				.and()
					.rememberMe()
					.tokenValiditySeconds(2419200)
					.key("gateway")
					.and()
					.logout()
				.and()
					.authorizeRequests()
						.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
						.antMatchers("/#/login", "/gateway/getUserProfile", "/login?logout", "/", "/index.html", "/favicon.ico", "favicon.ico", "/build/**", "/views/**", "/img/**",  "/templates/**", "/fonts/**", "/gateway/createUser", "/gateway/activateUser", "/gateway/initiateResetPassword",  "/gateway/resetPassword", "/gateway/config").permitAll()
						.anyRequest().authenticated()
					.and()
					.csrf().disable()
					.exceptionHandling().authenticationEntryPoint(alwaysSendUnauthorized401AuthenticationEntryPoint);
		}

		@Value("${db.driver}}")
		private String dbDriver;

        @Value("${db.url}")
        private String dbUrl;

        @Value("${db.username}")
        private String dbUsername;

        @Value("${db.password}")
        private String mySqldb;

        @Bean
        public DataSource dataSource(){
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName(dbDriver);
            ds.setUrl(dbUrl);
            ds.setUsername(dbUsername);
            ds.setPassword(mySqldb);

            return ds;
        }

		@Override
		protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
			auth.jdbcAuthentication().dataSource(dataSource()).passwordEncoder(new StandardPasswordEncoder("53cr3t"));

			ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			populator.setContinueOnError(true);
			populator.addScript(new ClassPathResource("/sql/db_initializer.sql"));
			populator.populate(dataSource().getConnection());
		}
	}
}

class AlwaysSendUnauthorized401AuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public final void commence(HttpServletRequest request, HttpServletResponse response,
							   AuthenticationException authException) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}