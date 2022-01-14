package com.yu_JJ.bean;

import lombok.Data;

/**
 * @className: Salary
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
@Data
public class Salary {
    private Integer salaryId;
    private Integer userId;
    private String monthId;
    private Float salary;
    private String createTime;
    private String creater;
    private String dateTime;
    private String updater;

    private String userName;
}
