package com.yu_JJ.bean;

import lombok.Data;

import java.util.List;

@Data
public class Result<T>{
    private Integer retCode;//0表示失败，，1表示成功
    private String retMsg;
    private Integer count;
    private T retObj;

    public Result(Integer retCode) {
        this.retCode = retCode;
    }

    public Result(Integer retCode, String retMsg, Integer count, T retObj) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.count = count;
        this.retObj = retObj;
    }
}
