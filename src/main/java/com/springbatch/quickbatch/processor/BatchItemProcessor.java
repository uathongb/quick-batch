package com.springbatch.quickbatch.processor;

import com.springbatch.quickbatch.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author SHOCKBLAST
 * @PackageName com.springbatch.quickbatch.processor
 * @ClassName BatchItemProcessor
 * @Date 2018/9/14
 * @Version 1.0
 * @Description:
 */
@Component
public class BatchItemProcessor implements ItemProcessor<UserInfo,UserInfo> {

    private static Logger logger = LoggerFactory.getLogger(BatchItemProcessor.class);


    @Override
    public UserInfo process(UserInfo userInfo) throws Exception {

        //TODO 进行数据的预处理
        logger.info("进行数据的预处理！");
        return null;
    }
}
