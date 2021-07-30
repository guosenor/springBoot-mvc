package com.guosen.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSON;
import org.springframework.validation.FieldError;
public class HandleFormValidateError {
    public static String format(List<FieldError> fieldErrors){
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("msg","validateFail");
        result.put("status",400);
        List<Object> errors = new ArrayList<Object>();
        fieldErrors.forEach(item->{
            HashMap<String,Object> error = new HashMap<String,Object>();
            error.put("code", item.getCode());
            error.put("message", item.getDefaultMessage());
            error.put("field", item.getField());
            errors.add(error);               
        });
        result.put("errors", errors);
        return JSON.toJSONString(result);
    }
}
