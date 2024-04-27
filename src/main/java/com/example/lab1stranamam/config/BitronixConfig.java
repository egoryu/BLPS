package com.example.lab1stranamam.config;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import jakarta.transaction.TransactionManager;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
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

}
