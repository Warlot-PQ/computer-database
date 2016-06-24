package com.excilys.persistence.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement // Enable annotation-driven transaction management capability
@ComponentScan(basePackages = { "com.excilys" })
public class PersistenceConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfig.class);
	
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

	/**
	 * Start JPA and Hibernate
	 * @return
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getDataSource());
		em.setPackagesToScan(new String[] { "com.excilys.core.entity" }); // Replace the persistence.xml file, look for @Entity classes
 
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    	em.setJpaVendorAdapter(vendorAdapter);
    	em.setJpaProperties(additionalProperties());    	
    	return em;
	}
	
	Properties additionalProperties() {
	  Properties properties = new Properties();
	  properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
	  properties.setProperty("hibernate.hbm2ddl.auto", "validate"); // DataBase option (create a new one each time, validate field, none, ...)
	  return properties;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
}