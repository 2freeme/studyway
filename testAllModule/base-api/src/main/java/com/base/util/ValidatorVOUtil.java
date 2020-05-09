package com.base.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Created by 谢文如 on 2017/6/19.
 * 校验VO
 */
public class ValidatorVOUtil {

    public static String validateVO(Object paramVO) throws Exception {
        ValidatorFactory vFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = vFactory.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(paramVO);
        StringBuffer validateError = new StringBuffer();
        if(set!=null && set.size()>0){
            for(ConstraintViolation<Object> val : set){
                validateError.append(val.getMessage());
            }
        }
        //子表校验
        Class paramVOClass = paramVO.getClass();
        Field[] fields = paramVOClass.getDeclaredFields();
        List<Object> subVOList;
        Field field;
        for(int i=0; i<fields.length; i++){
            field = fields[i];
            field.setAccessible(true);
            if(field.getType() == List.class){
                subVOList = (List<Object>)field.get(paramVO);
                if(subVOList==null || subVOList.size()==0){
                    continue;
                }
                for(Object subVO:subVOList){
                    set = validator.validate(subVO);
                    if(set!=null && set.size()>0){
                        for(ConstraintViolation<Object> val : set){
                            validateError.append(val.getMessage());
                        }
                    }
                }
            }
        }
        return validateError.toString();
    }
}
