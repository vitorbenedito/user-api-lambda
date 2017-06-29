package com.serverless.configuration;

import java.util.Properties

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

@Configuration
@EntityScan("com.serverless.persistence.entity")
@EnableJpaRepositories(basePackages = "com.serverless.persistence.repository")
public class HirakiConfig {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
	    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean()
	    em.setDataSource(dataSource)
	    em.setPackagesToScan("com.serverless.persistence.entity")

	    final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter()
	    em.setJpaVendorAdapter(vendorAdapter)
	    em.setJpaProperties(additionalProperties())

	    return em
	 }
	
	Properties additionalProperties() {
	    final Properties properties = new Properties();
	    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	    properties.setProperty("hibernate.format_sql", "true");
	    properties.setProperty("hibernate.showSql", "false");
	    properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
	    properties.setProperty("hibernate.hbm2ddl.auto",  "create")
	    return properties;
	  }

	 @Bean
	 public DataSource dataSource() {
		final HikariConfig hikariConfig = new HikariConfig();
	    hikariConfig.setJdbcUrl("");
	    hikariConfig.setUsername("");
	    hikariConfig.setPassword("");

	    return getHikariDataSource(hikariConfig);
	  }
	
	private DataSource getHikariDataSource(final HikariConfig hikariConfig) {
	    
	    hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
	    hikariConfig.setMaximumPoolSize(15);
	    hikariConfig.setConnectionTestQuery("select 1");
	    hikariConfig.setPoolName("springHikariCP");

	    hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
	    hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
	    hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
	    hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

	    return new HikariDataSource(hikariConfig);
	  }
}