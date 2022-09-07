    package com.classMetabus.web.Admin.dto.common;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    /*public static<T> ApiResponse<T> res(final boolean success,
                                        final int code,
                                        final T t ){
        return res(success,code, null, t);
    }
    public static<T> ApiResponse<T> res(final int code,
                                        final T t ){
        return res(code, t);
    }*/
    public static<T> ApiResponse<T> res(final boolean success,
                                           final int code,
                                           final String message,
                                           final T t){
        return ApiResponse.<T>builder()
                .success(success)
                .code(code)
                .data(t)
                .message(message)
                .build();
    }
}
