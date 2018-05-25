package com.lxg.work.retrofit.net;


/**
 * Created by lxg on 2017/2/3.
 */
public class ApiException extends RuntimeException {
    //    public static final int SERVICE_ERROR = 139;
    public static final int SERVICE_ERROR = 110;//
    public static final int DataFORMAT_ERROR = 112;//
    public static final int CUSTOM_ERROR = 112;//

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int code;
    private String message;

    public ApiException(int cod, String messag) {
        this.code = cod;
        this.message = messag;

    }


}
