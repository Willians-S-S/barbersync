package br.com.wss.exception;

import lombok.Getter;

@Getter
public class BusinessExceptionDto {

    private final int code;
    private final String error;
    private final String path;

    public BusinessExceptionDto(int code, String error, String path){
        this.code = code;
        this.error = error;
        this.path = path;
    }
}
