package com.yu_JJ.bean;

import lombok.Data;

/**
 * @className: Organise
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
@Data
public class Organise {
    private Integer orgId;
    private Integer parOrgId;
    private String orgName;
    private String orgPath;
    private Integer orgLevel;
    private String createTime;
    private String creater;
    private String updateTime;
    private String updater;

}
