package com.springbatch.quickbatch.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.DefaultExceptionHandler;
import org.springframework.stereotype.Component;

/**
 * @Author SHOCKBLAST
 * @PackageName com.springbatch.quickbatch.handler
 * @ClassName BatchStepExceptionHandler
 * @Date 2018/9/14
 * @Version 1.0
 * @Description:
 */
@Component
public class BatchStepExceptionHandler extends DefaultExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(BatchStepExceptionHandler.class);

    public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
        logger.error("Step运行时异常："+throwable.getMessage());
        throw new JobInterruptedException("Step运行时异常："+throwable.getMessage());
    }

}
