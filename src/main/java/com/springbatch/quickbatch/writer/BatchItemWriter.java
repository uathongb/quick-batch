package com.springbatch.quickbatch.writer;

import com.springbatch.quickbatch.model.UserInfo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author SHOCKBLAST
 * @PackageName com.springbatch.quickbatch.writer
 * @ClassName BatchItemWriter
 * @Date 2018/9/14
 * @Version 1.0
 * @Description:
 */
@Component
public class BatchItemWriter implements ItemWriter<UserInfo> {


    @Override
    public void write(List<? extends UserInfo> list) throws Exception {
        //TODO 进行写入数据到数据库
    }
}
