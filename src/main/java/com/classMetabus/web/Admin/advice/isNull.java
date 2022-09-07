package com.classMetabus.web.Admin.advice;

public class isNull {
    public static Integer isNull(Integer number){
        if(number == null) return 0;
        return number;
    }
}
