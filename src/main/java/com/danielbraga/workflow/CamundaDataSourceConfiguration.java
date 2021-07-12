package com.danielbraga.workflow;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration(proxyBeanMethods = false)
public class CamundaDataSourceConfiguration {

    @Autowired
    private Environment env;

    public CamundaDataSourceConfiguration() {
        super();
    }

    @Bean(name="camundaBpmDataSource")
    public DataSource camundaDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("datasource.camunda.jdbc-url"));
        dataSource.setUsername(env.getProperty("datasource.camunda.username"));
        dataSource.setPassword(env.getProperty("datasource.camunda.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager camundaTransactionManager(@Qualifier("camundaBpmDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
}
