package com.springbatch.quickbatch.job;

import com.springbatch.quickbatch.handler.BatchStepExceptionHandler;
import com.springbatch.quickbatch.model.UserInfo;
import com.springbatch.quickbatch.processor.BatchItemProcessor;
import com.springbatch.quickbatch.writer.BatchItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

import java.util.Date;

/**
 * @Author SHOCKBLAST
 * @PackageName com.springbatch.quickbatch.job
 * @ClassName QuickBatchJob
 * @Date 2018/9/14
 * @Version 1.0
 * @Description:
 */
@Configuration
public class QuickBatchJob implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(QuickBatchJob.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public PlatformTransactionManager platformTransactionManager;

    @Autowired
    public BatchStepExceptionHandler exceptionHandler;

    @Autowired
    public BatchItemWriter batchitemwriter;

    @Autowired
    public BatchItemProcessor batchitemprocessor;

    @Autowired
    JobLauncher jobLauncher;

    @Scheduled(cron = "0/30 * * * * ?")
    public void runJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder().addDate("runDate",new Date()).toJobParameters();
        jobLauncher.run(testJob(),jobParameters);
    }


    @Bean
    public Job testJob(){
        return jobBuilderFactory.get("testJob").listener(this).flow(testStep()).end().build();
    }

    @Bean
    public Step testStep() {
        return stepBuilderFactory.get("testStep")
                .<UserInfo,UserInfo>chunk(1000)
                .reader(fileReader())
                .processor(batchitemprocessor)
                .writer(batchitemwriter)
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(10)
                .allowStartIfComplete(true)
                .transactionManager(platformTransactionManager)
                .build();
    }

    private FlatFileItemReader<UserInfo> fileReader() {
        FlatFileItemReader<UserInfo> flatFileItemReader = new FlatFileItemReader<UserInfo>();
        flatFileItemReader.setResource(new FileSystemResource("E:\\home\\20180914.txt"));
        flatFileItemReader.setEncoding("GBK");
        DefaultLineMapper<UserInfo> defaultLineMapper = new DefaultLineMapper<UserInfo>();
        defaultLineMapper.setFieldSetMapper(new FieldSetMapper<UserInfo>() {
            @Override
            public UserInfo mapFieldSet(FieldSet fieldSet) throws BindException {
                UserInfo user = new UserInfo();
                user.setCardNo(fieldSet.readString(0));
                user.setIdCard(fieldSet.readString(1));
                user.setMobileNo(fieldSet.readString(2));
                user.setUserName(fieldSet.readString(3));
                return user;
            }
        });
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer("|"));
        flatFileItemReader.setLineMapper(defaultLineMapper);
        return flatFileItemReader;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("this is job startting!");

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        logger.info("this is job ending!");

    }
}
