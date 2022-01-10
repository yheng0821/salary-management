package com.yu_JJ.dao;

import com.yu_JJ.bean.User;
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
 * @className: UserDao
 * @description: User接口
 * @author: yheng
 * @date: 2022/1/6
 **/
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private ResultSet resultSet = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    public List<User> queryUserList(String userName,int start,int end){
        /*
                    SELECT * FROM (
                        SELECT * FROM `tb_user` tb LIMIT 7 OFFSET 2
                    ) t WHERE t.user_name='袁华'
         */
        String sql = "SELECT * FROM (\n" +
                "\tSELECT * FROM `tb_user` tb LIMIT ? OFFSET ?\n" +
                ") t WHERE t.user_name=?";
        List<User> userList = new ArrayList<User>();
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,end-start+1);
            preparedStatement.setInt(2,start);
            preparedStatement.setString(3,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setUserAcct(resultSet.getString(3));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(connection,preparedStatement,resultSet);
        }
        return userList;
    }

    public User queryUserById(Integer id){
        String sql = "select user_id,user_name,user_acct from tb_user where user_id=?";
        User user = new User();
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user.setUserId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setUserAcct(resultSet.getString(3));
            }else {
                user = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(connection,preparedStatement,resultSet);
        }
        return user;
    }

    public int addUser(User user){
        String sql = "insert into tb_user(user_name,user_acct,user_pwd) values(?,?,?)";
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getUserAcct());
            preparedStatement.setString(3,user.getUserPwd());
            int i =  preparedStatement.executeUpdate();
            if(i > 0){
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    public int updateUser(User user){
        User user_to_update = queryUserById(user.getUserId());
        String sql = "update tb_user set user_name=?,user_acct=?,user_pwd=? where user_id=?";
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getUserAcct());
            preparedStatement.setString(3,user.getUserPwd());
            preparedStatement.setInt(4,user_to_update.getUserId());
            int i =  preparedStatement.executeUpdate();
            if(i > 0){
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    public int deleteUser(Integer id){
        String sql = "delete from tb_user where user_id=?";
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            int i =  preparedStatement.executeUpdate();
            if(i > 0){
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    public User loginCheck(String userAcct, String userPwd){
        User user = new User();
        try {
            connection= JDBCUtil.getConnection();
//            select * from tb_user where user_acct = ? and user_pwd = ?
            String sql = "select user_id,user_name,user_acct from tb_user where user_acct = ? and user_pwd = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userAcct);
            preparedStatement.setString(2,userPwd);
            logger.info(sql+"##:"+userAcct+"+++++"+userPwd);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user.setUserId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setUserAcct(resultSet.getString(3));
            }else {
                user = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(connection,preparedStatement,resultSet);
        }
        return user;
    }

}

