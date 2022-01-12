package com.yu_JJ.db;

import java.sql.*;

public class JDBCUtil {
    //连接MySql数据库，用户名和密码都是root
    private static String url = "jdbc:mysql://localhost:3306/02_dic_test1?characterEncoding=utf8&useUnicode=true" ;
    private static String username = "user" ;
    private static String password = "123456789" ;
    private static String driver = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url,username,password);
        return connection;
    }


    public static void closeResource(Connection conn, Statement statement,ResultSet resultSet)    {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
            if (statement != null) {
                statement.close();
                statement = null;
            }
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static PreparedStatement getPStmt(Connection conn,String sql){
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }

}
