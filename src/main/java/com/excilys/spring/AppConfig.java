package com.excilys.spring;

import java.util.Locale;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.excilys.cli.CommandFactory;
import com.excilys.cli.CompanyListAllCommand;
import com.excilys.cli.ComputerCreateCommand;
import com.excilys.cli.ComputerDeleteCommand;
import com.excilys.cli.ComputerListAllCommand;
import com.excilys.cli.ComputerListOneCommand;
import com.excilys.cli.ComputerUpdateCommand;
import com.excilys.cli.ExitCommand;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // Tell Spring this file contains beans definition
/**
 * Registers the DefaultAnnotationHandlerMapping and
 * AnnotationMethodHandlerAdapter beans that are required for Spring 
 */
@EnableWebMvc // Replace <mvc:annotation-driven/> in servlet-config.xml
@EnableTransactionManagement // Enable annotation-driven transaction management capability
@ComponentScan(basePackages = { "com.excilys.cache", "com.excilys.cli", "com.excilys.db", "com.excilys.service", "com.excilys.servlet"})
public class AppConfig extends WebMvcConfigurerAdapter implements TransactionManagementConfigurer {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

	@Bean
	public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	@Scope("singleton")
	public CommandFactory getCommandFactory() {
		return new CommandFactory(new ComputerListAllCommand(), new CompanyListAllCommand(), 
				new ComputerListOneCommand(), new ComputerCreateCommand(), 
				new ComputerUpdateCommand(), new ComputerDeleteCommand(),
				new ExitCommand());
	}
	
	@Bean
	@Scope("singleton")
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(getDataSource());
	}
	
	@Bean
	@Qualifier("hikariDataSource")
	@Scope("singleton")
    public DataSource getDataSource() {
		DataSource hikariDS = null;
		HikariConfig config= new HikariConfig("/hikari.properties");
		config.setMaximumPoolSize(50);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		try {
			hikariDS = new HikariDataSource(config);
		} catch (Exception e) {
			LOGGER.error("HikariCp config error", e);
			System.exit(-1);
		}
		return hikariDS;
    }

	@Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

	/**
	 * Get the Spring bean handling transaction, 
	 * allow custom method naming for the bean definition method returning a 
	 * PlatformTransactionManager
	 */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }
    
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("WEB-INF/classes/messages/messages");
        resource.setDefaultEncoding("utf-8");
        return resource;
    }
	
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
	
	/** i18n */
	
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
