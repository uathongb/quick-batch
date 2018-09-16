package com.springbatch.quickbatch;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@ComponentScan(basePackages = { "com.springbatch.quickbatch.*" }) // 将该包下的文件纳入容器中
@EnableAutoConfiguration
@EnableBatchProcessing//springbatch
@EnableScheduling//定时器
public class QuickBatchApplication {

    private static Logger logger = LoggerFactory.getLogger(QuickBatchApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QuickBatchApplication.class, args);
    }

    /**
     * JobRepository是上述所有定型的持久机制。它提供了CRUD操作JobLauncher，Job以及 Step实现。
     * 当 Job第一次启动，一个 JobExecution被从库中获得，和执行过程中StepExecution和 JobExecution实现方式是通过将它们传递到存储库坚持：
     * @param dataSource
     * @param transactionManager
     * @return
     */
    @Bean
    public JobRepository jobRepositoryFactoryBean(DataSource dataSource, PlatformTransactionManager transactionManager){
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        try {
            jobRepositoryFactoryBean.afterPropertiesSet();
            return  jobRepositoryFactoryBean.getObject();
        } catch (Exception e) {
            logger.error("出现异常！");
        }
        return null;
    }

}
