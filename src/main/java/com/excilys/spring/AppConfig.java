package com.excilys.spring;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.excilys.cache", "com.excilys.cli", "com.excilys.db", "com.excilys.service" })
public class AppConfig {
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
}
