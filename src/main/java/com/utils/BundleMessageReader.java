package com.utils;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * Created by aautushk on 9/12/2015.
 */

public class BundleMessageReader {
    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();

        System.out.println("Current local is:");
        System.out.println(locale.getLanguage());

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);

        return messageSource.getMessage(key, new Object[0], locale);
    }
}
