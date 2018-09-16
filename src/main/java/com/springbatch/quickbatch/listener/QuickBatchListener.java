package com.springbatch.quickbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


/**
 * @Author SHOCKBLAST
 * @PackageName com.springbatch.quickbatch.listener
 * @ClassName QuickBatchListener
 * @Date 2018/9/16
 * @Version 1.0
 * @Description:
 */
@Component
public class QuickBatchListener implements JobExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(QuickBatchListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("this is job startting!");

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        logger.info("this is job ending!");

    }
}
