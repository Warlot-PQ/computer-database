package com.excilys.webapp.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = { "com.excilys" })	
@EnableWebMvc
public class WebappConfig extends WebMvcConfigurerAdapter {
	public static final String PATH_MSG = "WEB-INF/classes/messages/messages";
	public static final String PATH_VIEW = "/WEB-INF/views/";
	public static final String EXT_VIEW = ".jsp";
	
	public static final String RESOURCES_HANDLER_CSS = "/css/**";
	public static final String RESOURCES_LOCATION_CSS = "/css/";
	
	public static final String URL_DASHBOARD = "/dashboard";
	public static final String URL_ADD_COMPUTER = "/add_computer";
	public static final String URL_EDIT_COMPUTER = "/edit_computer";
	
    /*
     * Spring MVC
     */
	
	/**
	 * View Resolver for JSPs.
	 * Replace org.springframework.web.servlet.view.InternalResourceViewResolver
	 * in servlet-config.xml 
	 * @return InternalResourceViewResolver object
	 */
	@Bean
	public InternalResourceViewResolver jspViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix(PATH_VIEW);
        bean.setSuffix(EXT_VIEW);
        return bean;
	}
	
	/**
	 * Resources exclusions from servlet mapping.
	 * (add static resources)
	 */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_HANDLER_CSS).addResourceLocations(RESOURCES_LOCATION_CSS);
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }
	
	/*
	 * i18n
	 */

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename(PATH_MSG);
        resource.setDefaultEncoding("utf-8");
        return resource;
    }
    
	/**
	 * Set default language as english. 
	 * SessionLocaleResolver object to keep language in memory without passing it to each URL.
	 * @return LocaleResolver Spring bean
	 */
	@Bean(name = "localeResolver")
    public LocaleResolver localeResolver(){
		SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(new Locale("en"));
	    return slr;
    }
	
	/**
	 * Set up interceptor to use the property file corresponding to the right language
	 * ex: ?language=en with interceptor.setParamName("language)
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
	
	/*
	 * Upload file handler
	 */
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    return resolver;
	}
}