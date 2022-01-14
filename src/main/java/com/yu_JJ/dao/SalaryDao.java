package com.yu_JJ.dao;

import com.yu_JJ.bean.Salary;
import com.yu_JJ.db.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: Salary
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
public class SalaryDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private ResultSet rst = null;
    private Connection conn = null;
    private PreparedStatement prst = null;
    private String sql = null;

    public List<Salary> querySalaryList(){
        List<Salary> salaryList = new ArrayList<>();
        Salary salary = new Salary();
        sql = "SELECT s.month_id '月份ID',u.user_name '用户名称',s.salary 当月薪资 ,s.create_time '创建时间' \n" +
                "FROM tb_salary s,tb_user u\n" +
                "WHERE s.user_id=u.user_id" ;
        try {
            conn = JDBCUtil.getConnection();
            prst  = conn.prepareStatement(sql);
            rst = prst.executeQuery();
            while(rst.next()){
                salary.setMonthId(rst.getString(1));
                salary.setUserName(rst.getString(2));
                salary.setSalary(rst.getFloat(3));
                salary.setCreateTime(rst.getTimestamp(4).toString());
                salaryList.add(salary);
            }
            return salaryList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
