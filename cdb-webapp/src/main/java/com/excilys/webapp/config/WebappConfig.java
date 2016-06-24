package com.excilys.webapp.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
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
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
	}
	
	/**
	 * Allows for mapping the DispatcherServlet to "/" 
	 */
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
	/**
	 * Resources exclusions from servlet mapping.
	 * (add static resources)
	 */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }
	
	/*
	 * i18n
	 */

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("WEB-INF/classes/messages/messages");
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
}