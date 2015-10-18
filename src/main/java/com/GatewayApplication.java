package com;

import com.utils.SecurityContextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.security.Principal;
import java.util.Properties;

@Configuration // needed to specify that the class contains global spring configurations
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories // needed to enable JPA based repositories
@EnableTransactionManagement
@RestController // needed to enable rest end points within the class
@EnableGlobalMethodSecurity(securedEnabled=true) // needed for method based security (@Secured("ROLE_ADMIN") annotation))
@EnableJpaAuditing//needed to activate auditing(automatically managed fields createdBy, createdDate, lastModifiedBy, lastModifiedDate)

public class GatewayApplication {
    @Value("${mysql.url}")
    private String mySqlUrl;

    @Value("${mysql.username}")
    private String mySqlUsername;

    @Value("${mysql.password}")
    private String mySqlPassword;

    @Value("${mailserver.host}")
    private String mailHost;

    @Value("${mailserver.port}")
    private int mailPort;

    @Value("${mailserver.username}")
    private String mailUsername;

    @Value("${mailserver.password}")
    private String mailPassword;

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
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        //ds.setUrl("jdbc:mysql://localhost/gateway");
        ds.setUrl(mySqlUrl);
        ds.setUsername(mySqlUsername);
        ds.setPassword(mySqlPassword);

        return ds;
    }

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new SpringSecurityAuditorAware();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		return adapter;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setShowSql(true);
		vendorAdapter.setGenerateDdl(false); //true value not for production !!! update db after entityManager instantiation based on entities
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.entity");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
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

//		mailSender.setHost("localhost");
//		mailSender.setPort(25);

		mailSender.setHost(mailHost);
		mailSender.setPort(mailPort);
		mailSender.setUsername(mailUsername);
		mailSender.setPassword(mailPassword);

		Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
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

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			//2419200 means 4 weeks
			http
					.formLogin()
				.and()
					.rememberMe()
					.tokenValiditySeconds(2419200)
					.key("conspector")
					.and()
					.logout()
				.and()
					.authorizeRequests()
						.antMatchers("/#/login", "/user", "/login?logout", "/", "/index.html", "/build/**", "/views/**", "/img/**", "/fonts/**", "/gateway/createUser", "/gateway/activateUser").permitAll()
						.anyRequest().authenticated()
					.and()
					.csrf().disable();
		}

        @Value("${mysql.url}")
        private String mySqlUrl;

        @Value("${mysql.username}")
        private String mySqlUsername;

        @Value("${mysql.password}")
        private String mySqlPassword;

        @Bean
        public DataSource dataSource(){
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl(mySqlUrl);
            ds.setUsername(mySqlUsername);
            ds.setPassword(mySqlPassword);

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

	@RequestMapping("/user")
	public Principal user(Principal user) {
				return user;
	}
}