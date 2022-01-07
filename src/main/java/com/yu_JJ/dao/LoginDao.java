package com.yu_JJ.dao;

import com.yu_JJ.bean.User;
import com.yu_JJ.db.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    private static final Logger logger = LoggerFactory.getLogger(LoginDao.class);

    public User loginCheck(String userAcct, String userPwd) {
//        logger.info("loginCheck start");
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rst = null;
        User user = new User();
        try {
            conn= JDBCUtil.getConnection();
//            select * from tb_user where user_acct = ? and user_pwd = ?
            String sql = "select user_id,user_name,user_acct from tb_user where user_acct = ? and user_pwd = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1,userAcct);
            pstm.setString(2,userPwd);
            logger.info(sql+"##:"+userAcct+"+++++"+userPwd);
            rst = pstm.executeQuery();
            if(rst.next()){
                user.setUserId(rst.getInt(1));
                user.setUserName(rst.getString(2));
                user.setUserAcct(rst.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,pstm,rst);
        }

        return user;
    }

    public static void main(String[] args) {
        User user = new User();
        LoginDao loginDao = new LoginDao();
        user = loginDao.loginCheck("root","root");
        System.out.printf(user.toString());
    }
}
