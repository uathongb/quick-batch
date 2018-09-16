package com.springbatch.quickbatch.job;

import com.springbatch.quickbatch.fields.QuicBatchFieldSet;
import com.springbatch.quickbatch.handler.BatchStepExceptionHandler;
import com.springbatch.quickbatch.listener.QuickBatchListener;
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
public class QuickBatchJob{
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
    private QuickBatchListener quickBatchListener;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    private QuicBatchFieldSet quicBatchFieldSet;

    @Scheduled(cron = "0/30 * * * * ?")
    public void runJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder().addDate("runDate",new Date()).toJobParameters();
        jobLauncher.run(testJob(),jobParameters);
    }


    @Bean
    public Job testJob(){
        return jobBuilderFactory.get("testJob").listener(quickBatchListener).flow(testStep()).end().build();
    }

    @Bean
    public Step testStep() {
        return stepBuilderFactory.get("testStep")
                //每次提交的数量
                .<UserInfo,UserInfo>chunk(1000)
                //去读取指定的文件 读取文件是一行一行进行读取，但是writer中的写是在一块写的
                .reader(fileReader())
                //由于reader的时候是一行一行进行读取的，故可以对一行数据进行数据的清洗
                .processor(batchitemprocessor)
                //写入到数据库当中
                .writer(batchitemwriter)
                .faultTolerant()
                //跳过异常
                .skip(Exception.class)
                //跳过的最大次数
                .skipLimit(10)
                //允许重试
                .allowStartIfComplete(true)
                //事务
                .transactionManager(platformTransactionManager)
                .build();
    }

    private FlatFileItemReader<UserInfo> fileReader() {
        FlatFileItemReader<UserInfo> flatFileItemReader = new FlatFileItemReader<UserInfo>();
        flatFileItemReader.setResource(new FileSystemResource("E:\\home\\20180914.txt"));
        flatFileItemReader.setEncoding("GBK");
        DefaultLineMapper<UserInfo> defaultLineMapper = new DefaultLineMapper<UserInfo>();
        defaultLineMapper.setFieldSetMapper(quicBatchFieldSet);
        defaultLineMapper.setLineTokenizer(new DelimitedLineTokenizer("|"));
        flatFileItemReader.setLineMapper(defaultLineMapper);
        return flatFileItemReader;
    }


}
