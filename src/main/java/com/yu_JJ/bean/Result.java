package com.yu_JJ.bean;

import lombok.Data;

import java.util.List;

@Data
public class Result<T>{
    private Integer retCode;//0表示失败，，1表示成功,2表示邮箱不正确，3表示手机不正确
    private String retMsg;
    private Integer retCount;
    private T retObj;

    public Result(Integer retCode) {
        this.retCode = retCode;
    }

    public Result(Integer retCode, String retMsg, T retObj) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retObj = retObj;
    }

    public Result(Integer retCode, String retMsg, Integer retCount, T retObj) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retCount = retCount;
        this.retObj = retObj;
    }
}
