package com.yu_JJ.dao;

import com.yu_JJ.bean.Organise;
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

    public List<Salary> querySalaryList(String userName,Integer page,Integer limit){
        List<Salary> salaryList = new ArrayList<>();

        try {
            if("".equals(userName) || userName==null){
                conn = JDBCUtil.getConnection();
                sql = "SELECT s.salary_id,s.month_id '月份ID',u.user_name '用户名称',s.salary 当月薪资 ,s.create_time '创建时间', s.user_id \n" +
                        "FROM tb_salary s,tb_user u\n" +
                        "WHERE s.user_id=u.user_id\n" +
                        "LIMIT ?,?" ;
                prst = conn.prepareStatement(sql);
                prst.setInt(1,(page-1)*limit);
                prst.setInt(2,limit);
            }else {
                sql = "SELECT s.salary_id,s.month_id '月份ID',u.user_name '用户名称',s.salary 当月薪资 ,s.create_time '创建时间', s.user_id \n" +
                        "FROM tb_salary s,tb_user u\n" +
                        "WHERE s.user_id=u.user_id\n" +
                        "AND u.user_name LIKE ?\n" +
                        "LIMIT ?,?";
                conn = JDBCUtil.getConnection();
                prst = conn.prepareStatement(sql);
                prst.setString(1,"%"+userName+"%");
                prst.setInt(2,(page-1)*limit);
                prst.setInt(3,limit);
            }
            rst = prst.executeQuery();
            while(rst.next()){
                Salary salary = new Salary();
                salary.setSalaryId(rst.getInt(1));
                salary.setMonthId(rst.getString(2));
                salary.setUserName(rst.getString(3));
                salary.setSalary(rst.getFloat(4));
                if (rst.getTimestamp(5) != null){
                    salary.setCreateTime(rst.getTimestamp(5).toString());
                }
                salary.setUserId(Integer.valueOf(rst.getString(6)));
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
        String sql = "SELECT count(*) FROM tb_salary s,tb_user u\n" +
                "WHERE s.user_id=u.user_id";
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





            prst.setInt(1,salary.getUserId());
            prst.setString(2,salary.getMonthId());
            prst.setFloat(3,salary.getSalary());

            prst.setTimestamp(4, DateUtil.StrToDate(salary.getCreateTime()));
            prst.setString(5, salary.getCreater());
            prst.setTimestamp(6,DateUtil.StrToDate(salary.getUpdateTime()));
            prst.setString(7,salary.getUpdater());
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

    public int updateSalary(Salary salary){
//        User user_to_update = queryUserById(user.getUserId());//user_to_update数据库中存在的user，user是前面传入的user，user中的数据写入数据库
        String sql = "update tb_salary set user_id=?,month_id=?,salary=?,create_time=?,creater=?,update_time=?,updater=? where salary_id=?";
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setInt(1,salary.getUserId());
            prst.setString(2,salary.getMonthId());
            prst.setFloat(3,salary.getSalary());
            prst.setString(4, salary.getCreateTime());
            prst.setString(5, salary.getCreater());
            prst.setString(6, salary.getUpdateTime());
            prst.setString(7, salary.getUpdater());
            prst.setInt(8, salary.getSalaryId());
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

    public int deleteSalary(Integer id){
        String sql = "delete from tb_salary where salary_id=?";
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setInt(1,id);
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

    public List<Organise> getSaleryByOrganise(){
        List<Organise> organiseList = new ArrayList<>();
        String  sql = "SELECT sum(s.salary) ,o.org_name\n" +
                "FROM tb_salary s,tb_user u,tb_user_org_rel uo,tb_org o\n" +
                "WHERE s.user_id=u.user_id\n" +
                "AND u.user_id=uo.user_id\n" +
                "and uo.org_id=o.org_id\n" +
                "GROUP BY o.org_name\n";
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            ResultSet resultSet = prst.executeQuery();
            while(resultSet.next()){
                Organise organise = new Organise();
                organise.setSalary(resultSet.getInt(1));
                organise.setOrgName(resultSet.getString(2));
                organiseList.add(organise);
            }
            return organiseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,prst,rst);
        }
        return null;
    }
}
