package com.yu_JJ.dao;

import com.yu_JJ.bean.User;
import com.yu_JJ.db.JDBCUtil;
import com.yu_JJ.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className: UserDao
 * @description: User接口
 * @author: yheng
 * @date: 2022/1/6
 **/
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private ResultSet rst = null;
    Connection conn = null;
    PreparedStatement prst = null;

    //批量查询
    public List<User> queryUserList(String userName,int page,int limit){
        /*
                    SELECT * FROM (
                        SELECT * FROM `tb_user` tb LIMIT 7 OFFSET 2
                    ) t WHERE t.user_name='袁华'
         */
//        String sql = "SELECT * FROM (\n" +
//                "\tSELECT * FROM `tb_user` tb LIMIT ? OFFSET ?\n" +
//                ") t WHERE t.user_name=?";
        String sql = "SELECT * FROM tb_user WHERE user_name=? order by user_id asc limit ?, ?";
//        String sql = "SELECT * FROM tb_user WHERE user_name=? order by user_id asc ";
        List<User> userList = new ArrayList<User>();
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setString(1,userName);
            prst.setInt(2,page);
            prst.setInt(3,limit);
            ResultSet rst = prst.executeQuery();
            while (rst.next()){
                User user = new User();
                user.setUserId(rst.getInt(1));
                user.setUserName(rst.getString(2));
                user.setUserAcct(rst.getString(3));
                user.setUserPwd(rst.getString(4));
                user.setAlias(rst.getString(5));
                user.setMail(rst.getString(6));
                user.setTelephone(rst.getString(7));
                user.setAddress(rst.getString(8));
                user.setCreateTime(rst.getTimestamp(9).toString());
                user.setCreater(rst.getString(10));
                user.setUpdateTime(rst.getTimestamp(11).toString());
                user.setUpdater(rst.getString(12));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,prst,rst);
        }
        return userList;
    }

    //用户id查询
    public User queryUserById(Integer id){
        String sql = "select * from tb_user where user_id=?";
        User user = new User();
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setInt(1,id);
            ResultSet rst = prst.executeQuery();
            if(rst.next()){
                user.setUserId(rst.getInt(1));
                user.setUserName(rst.getString(2));
                user.setUserAcct(rst.getString(3));
                user.setUserPwd(rst.getString(4));
                user.setAlias(rst.getString(5));
                user.setMail(rst.getString(6));
                user.setTelephone(rst.getString(7));
                user.setAddress(rst.getString(8));
                user.setCreateTime(rst.getTimestamp(9).toString());
                user.setCreater(rst.getString(10));
                user.setUpdateTime(rst.getTimestamp(11).toString());
                user.setUpdater(rst.getString(12));
//                Date time1=new Date(rst.getTimestamp(9).getTime()); //java.util.Date
//                SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String pubtime = formattime.format(time1);
//                logger.info(time1.toString());
////                Date date = DateUtil.StrToDate(pubtime);
////                System.out.println(date.toString());
//                user.setCreateTime(time1);


            }else {
                user = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,prst,rst);
        }
        return user;
    }

    //添加用户
    public int addUser(User user){
        String sql = "insert into tb_user(user_name, user_acct, user_pwd, alias, mail, telephone, address, create_time, creater, update_time, updater) values(?,?,?,?,?,?,?,?,?,?,?)";
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
            prst.setTimestamp(8,DateUtil.StrToDate(user.getCreateTime()));
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

    //更新用户
    public int updateUser(User user){
        User user_to_update = queryUserById(user.getUserId());//user_to_update数据库中存在的user，user是前面传入的user，user中的数据写入数据库
        String sql = "update tb_user set user_name=?,user_acct=?,user_pwd=?,alias=?,mail=?,telephone=?,address=?,create_time=?,creater=?,update_time=?,updater=? where user_id=?";
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setString(1,user.getUserName());
            prst.setString(2,user.getUserAcct());
            prst.setString(3,user.getUserPwd());
            prst.setString(4, user.getAlias());
            prst.setString(5, user.getMail());
            prst.setString(6, user.getTelephone());
            prst.setString(7, user.getAddress());
            prst.setString(8, user.getCreateTime());
            prst.setString(9, user.getCreater());
            prst.setString(10, user.getUpdateTime());
            prst.setString(11, user.getUpdater());
            prst.setInt(12,user_to_update.getUserId());
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

    //删除用户
    public int deleteUser(Integer id){
        String sql = "delete from tb_user where user_id=?";
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

    //登录
    public User loginCheck(String userAcct, String userPwd){
        User user = new User();
        try {
            conn= JDBCUtil.getConnection();
//            select * from tb_user where user_acct = ? and user_pwd = ?
            String sql = "select * from tb_user where user_acct = ? and user_pwd = ?";
            prst = conn.prepareStatement(sql);
            prst.setString(1,userAcct);
            prst.setString(2,userPwd);
            logger.info(sql+"##:"+userAcct+"+++++"+userPwd);
            rst = prst.executeQuery();
            if(rst.next()){
                user.setUserId(rst.getInt(1));
                user.setUserName(rst.getString(2));
                user.setUserAcct(rst.getString(3));
            }else {
                user = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,prst,rst);
        }
        return user;
    }

}

