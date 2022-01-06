package com.yu_JJ.bean;

import lombok.Data;

import java.util.List;

@Data
public class Result<T>{
    private String retCode;//0表示失败，，1表示成功
    private String retMsg;
    private T retObj;

    public Result() {
    }

    public Result(String retCode, String retMsg, T retObj) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retObj = retObj;
    }
}
