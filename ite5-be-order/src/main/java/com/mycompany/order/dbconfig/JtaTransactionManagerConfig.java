package com.mycompany.order.dbconfig;

import javax.transaction.UserTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import lombok.extern.slf4j.Slf4j;

@Configuration
public class JtaTransactionManagerConfig {
    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
    	
    	UserTransaction userTransaction = new UserTransactionImp();
    	userTransaction.setTransactionTimeout(10000);
        
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        
        JtaTransactionManager manager = new JtaTransactionManager(
        		userTransaction, userTransactionManager);
        return manager;
    }
}


