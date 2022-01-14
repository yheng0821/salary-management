package com.yu_JJ.dao;

import com.yu_JJ.bean.Salary;
import com.yu_JJ.bean.User;
import com.yu_JJ.db.JDBCUtil;
import com.yu_JJ.utils.DateUtil;
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

    public List<Salary> queryAllSalary(Integer page,Integer limit){
        List<Salary> salaryList = new ArrayList<>();
        Salary salary = new Salary();
        sql = "SELECT s.month_id '月份ID',u.user_name '用户名称',s.salary 当月薪资 ,s.create_time '创建时间', s.user_id \n" +
                "FROM tb_salary s,tb_user u\n" +
                "WHERE s.user_id=u.user_id\n" +
                "LIMIT ?,?" ;
        try {
            conn = JDBCUtil.getConnection();
            prst  = conn.prepareStatement(sql);
            prst.setInt(1,(page-1)*limit);
            prst.setInt(2,limit);
            rst = prst.executeQuery();
            while(rst.next()){
                salary.setMonthId(rst.getString(1));
                salary.setUserName(rst.getString(2));
                salary.setSalary(rst.getFloat(3));
                salary.setCreateTime(rst.getTimestamp(4).toString());
                salary.setUserId(Integer.valueOf(rst.getString(5)));
                salaryList.add(salary);
            }
            return salaryList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer countAllSalary(){
        Integer count = 0;
        String sql = "SELECT count(*) FROM tb_salary";
//        String sql = "SELECT * FROM tb_user WHERE user_name=? order by user_id asc ";
        List<Salary> salaryList = new ArrayList<Salary>();
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            ResultSet rst = prst.executeQuery();
            if (rst.next()){
                count = rst.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,prst,rst);
        }
        return count;
    }

    public Salary querySalaryById(Integer id){
        String sql = "select * from tb_salary where salary_id=?";
        Salary salary = new Salary();
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setInt(1,id);
            ResultSet rst = prst.executeQuery();
            if(rst.next()){
                salary.setSalaryId(rst.getInt(1));
                salary.setUserId(rst.getInt(2));
                salary.setMonthId(rst.getString(3));
                salary.setSalary(rst.getFloat(4));;
                if(rst.getTimestamp(5) != null)
                    salary.setCreateTime(rst.getTimestamp(5).toString());
                salary.setCreater(rst.getString(6));
                if(rst.getTimestamp(7) != null)
                    salary.setUpdateTime(rst.getTimestamp(7).toString());
                salary.setUpdater(rst.getString(8));

            }else {
                salary = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,prst,rst);
        }
        return salary;
    }

    public int addSalary(Salary salary){
        String sql = "insert into tb_salary(user_id, month_id, salary, create_time, creater, update_time, updater) values (?,?,?,?,?,?,?)";
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setString(1,user.getUserName());
            prst.setString(2,user.getUserAcct());
            prst.setString(3,user.getUserPwd());
            prst.setString(4,user.getAlias());
            prst.setString(5,user.getMail());
            prst.setString(6,user.getTelephone());
            prst.setString(7,user.getAddress());
            prst.setTimestamp(8, DateUtil.StrToDate(user.getCreateTime()));
            prst.setString(9, user.getCreater());
            prst.setTimestamp(10,DateUtil.StrToDate(user.getUpdateTime()));
            prst.setString(11,user.getUpdater());
            int i =  prst.executeUpdate();
            if(i > 0){
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,prst,rst);
        }
        return 0;
    }
}
