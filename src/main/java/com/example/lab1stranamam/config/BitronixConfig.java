package com.example.lab1stranamam.config;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import jakarta.transaction.TransactionManager;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.example.lab1stranamam.repositories", entityManagerFactoryRef = "primaryPgDataSourceResource")
public class BitronixConfig {
    @Value("${bitronix.tm.serverId}")
    private String serverId;

    @Value("${bitronix.tm.logPart1Filename}")
    private String logPart1Filename;

    @Value("${bitronix.tm.logPart2Filename}")
    private String logPart2Filename;

    @Bean(name = "bitronixTransactionManager")
    public BitronixTransactionManager bitronixTransactionManager(bitronix.tm.Configuration c) throws Throwable {
        BitronixTransactionManager bitronixTransactionManager = TransactionManagerServices.getTransactionManager();
        bitronixTransactionManager.setTransactionTimeout(60);

        return bitronixTransactionManager;

    }

    @Bean
    public bitronix.tm.Configuration transactionManagerServices() {
        bitronix.tm.Configuration configuration = TransactionManagerServices.getConfiguration();

        configuration.setServerId(serverId);
        if ("".equals(logPart1Filename) && "".equals(logPart2Filename)) {
            configuration.setJournal(null);
        } else {
            configuration.setLogPart1Filename(logPart1Filename);
            configuration.setLogPart2Filename(logPart2Filename);
        }

        return configuration;
    }

    /*@Bean(name = "primaryPgDataSource")
    @Primary
    public DataSource primaryPgDataSource() {
        PoolingDataSource bitronixDataSourceBean = new PoolingDataSource();
        bitronixDataSourceBean.setMaxPoolSize(5);
        bitronixDataSourceBean.setUniqueName("primaryPgDataSourceResource");
        bitronixDataSourceBean.setClassName("org.postgresql.xa.PGXADataSource");
        bitronixDataSourceBean.setAllowLocalTransactions(true);
        Properties properties = new Properties();
        properties.put("user",  "postgres");
        properties.put("password",  "1112");
        properties.put("url", "jdbc:postgresql://localhost:5432/stranamam");
        bitronixDataSourceBean.setDriverProperties(properties);
        return bitronixDataSourceBean;
    }

    @Bean(name = "secondPgDataSource")
    public DataSource secondPgDataSource() {
        PoolingDataSource bitronixDataSourceBean = new PoolingDataSource();
        bitronixDataSourceBean.setMaxPoolSize(5);
        bitronixDataSourceBean.setUniqueName("secondPgDataSourceResource");
        bitronixDataSourceBean.setClassName("org.postgresql.xa.PGXADataSource");
        bitronixDataSourceBean.setAllowLocalTransactions(true);
        Properties properties = new Properties();
        properties.put("user",  "postgres");
        properties.put("password",  "1112");
        properties.put("url", "jdbc:postgresql://localhost:5432/mobile");
        bitronixDataSourceBean.setDriverProperties(properties);
        return bitronixDataSourceBean;
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    @DependsOn({"primaryPgDataSource"})
    public LocalContainerEntityManagerFactoryBean
    primaryPgDataEntityManagerFactory(EntityManagerFactoryBuilder builder, DataSource primaryPgDataSource) {
        return builder
                .dataSource(primaryPgDataSource)
                .persistenceUnit("primaryPgDataSourceResource")
                .packages(BitronixConfig.class)
                .build();
    }

    /*@Bean(name = "entityManagerFactory")
    @DependsOn({"secondPgDataSource"})
    public LocalContainerEntityManagerFactoryBean
    secondPgDataEntityManagerFactory(EntityManagerFactoryBuilder builder, DataSource secondPgDataSource) {
        return builder
                .dataSource(secondPgDataSource)
                .persistenceUnit("secondPgDataSourceResource")
                .packages(BitronixConfig.class)
                .build();
    }*/
}
