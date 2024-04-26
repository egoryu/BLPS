package com.example.lab1stranamam.config;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.SystemException;

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
    public BitronixTransactionManager transactionManager(bitronix.tm.Configuration c) throws SystemException {
        BitronixTransactionManager trans = TransactionManagerServices.getTransactionManager();
        trans.setTransactionTimeout(60);
        return trans;

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
