package com.springbatch.quickbatch.fields;

import com.springbatch.quickbatch.model.UserInfo;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

/**
 * @Author SHOCKBLAST
 * @PackageName com.springbatch.quickbatch.fields
 * @ClassName QuicBatchFieldSet
 * @Date 2018/9/16
 * @Version 1.0
 * @Description:
 */
@Component
public class QuicBatchFieldSet implements FieldSetMapper<UserInfo> {
    @Override
    public UserInfo mapFieldSet(FieldSet fieldSet) throws BindException {
        UserInfo user = new UserInfo();
        user.setCardNo(fieldSet.readString(0));
        user.setIdCard(fieldSet.readString(1));
        user.setMobileNo(fieldSet.readString(2));
        user.setUserName(fieldSet.readString(3));
        return user;
    }
}
