package com.classMetabus.web.Admin.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    public static<T> CommonResponse<T> res(final int code, final T t ){
        return res(false,code, null, t);
    }

    public static<T> CommonResponse<T> res(final boolean success,final int code, final String message,final T t){
        return CommonResponse.<T>builder()
                .data(t)
                .message(message)
                .code(code)
                .success(success)
                .build();
    }
}
