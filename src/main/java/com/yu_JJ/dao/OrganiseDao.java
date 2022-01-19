package com.yu_JJ.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
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
 * @className: OrganiseDao
 * @description: TODO 类描述
 * @author: yheng
 * @date: 2022/1/14
 **/
public class OrganiseDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    private ResultSet rst = null;
    private Connection conn = null;
    private PreparedStatement prst = null;
    private String sql = null;

    public List<Organise> queryOrganiseList(String orgName, Integer page, Integer limit){
        List<Organise> organiseList = new ArrayList<>();

        try {
            if("".equals(orgName) || orgName==null){
                conn = JDBCUtil.getConnection();
                sql = "SELECT DISTINCT o.org_id '组织ID',o.org_name '组织名称',o.org_path '组织路径',o.create_time '创建时间',o.creater\n" +
                        "FROM tb_org o,tb_user u,tb_user_org_rel uo\n" +
                        "LIMIT ?,?";
                prst = conn.prepareStatement(sql);
                prst.setInt(1,(page-1)*limit);
                prst.setInt(2,limit);
            }else {
                sql = "SELECT DISTINCT o.org_id '组织ID',o.org_name '组织名称',o.org_path '组织路径',o.create_time '创建时间',o.creater '创建人'\n" +
                        "FROM tb_org o,tb_user u,tb_user_org_rel uo\n" +
//                        "WHERE o.org_id=uo.org_id\n" +
                        "WHERE o.org_name LIKE ?\n" +
                        "LIMIT ?,?";
                conn = JDBCUtil.getConnection();
                prst = conn.prepareStatement(sql);
                prst.setString(1,"%"+orgName+"%");
                prst.setInt(2,(page-1)*limit);
                prst.setInt(3,limit);
            }
            rst = prst.executeQuery();
            while(rst.next()){
                Organise organise = new Organise();
                organise.setOrgId(rst.getInt(1));
                organise.setOrgName(rst.getString(2));
                organise.setOrgPath(rst.getString(3));
                if (rst.getTimestamp(4) != null){
                    organise.setCreateTime(rst.getTimestamp(4).toString());
                }
                organise.setCreater(rst.getString(5));
                organiseList.add(organise);
            }
            return organiseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer countAllOrganise(){
        Integer count = 0;
        String sql = "SELECT count(*) FROM tb_org";
//        String sql = "SELECT * FROM tb_user WHERE user_name=? order by user_id asc ";
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

    public Organise queryOrganiseById(Integer id){
        String sql = "select * from tb_org where org_id=?";
        Organise organise = new Organise();
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setInt(1,id);
            ResultSet rst = prst.executeQuery();
            if(rst.next()){
                organise.setOrgId(rst.getInt(1));
                organise.setParOrgId(rst.getInt(2));
                organise.setOrgName(rst.getString(3));
                organise.setOrgPath(rst.getString(4));;
                organise.setOrgLevel(rst.getInt(5));
                if(rst.getTimestamp(6) != null)
                    organise.setCreateTime(rst.getTimestamp(6).toString());
                organise.setCreater(rst.getString(7));
                if(rst.getTimestamp(8) != null)
                    organise.setUpdateTime(rst.getTimestamp(8).toString());
                organise.setUpdater(rst.getString(9));

            }else {
                organise = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,prst,rst);
        }
        return organise;
    }

    public int addOrganise(Organise organise){
        String sql = "insert into tb_org(par_org_id, org_name, org_path, org_level, create_time, creater, update_time, updater) values (?,?,?,?,?,?,?,?)";
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);

            prst.setInt(1,organise.getParOrgId());
            prst.setString(2,organise.getOrgName());
            prst.setString(3,organise.getOrgPath());

            prst.setInt(4, organise.getOrgLevel());
            prst.setTimestamp(5,DateUtil.StrToDate(organise.getCreateTime()));
            prst.setString(6,organise.getCreater());
            prst.setTimestamp(7,DateUtil.StrToDate(organise.getUpdateTime()));
            prst.setString(8,organise.getUpdater());
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

    public int updateOrganise(Organise organise){
//        User user_to_update = queryUserById(user.getUserId());//user_to_update数据库中存在的user，user是前面传入的user，user中的数据写入数据库
        String sql = "update tb_org set par_org_id=?,org_name=?,org_path=?,org_level=?,update_time=?,updater=? where org_id=?";
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setInt(1,organise.getParOrgId());
            prst.setString(2,organise.getOrgName());
            prst.setString(3,organise.getOrgPath());
            prst.setInt(4, organise.getOrgLevel());
            prst.setString(5, organise.getUpdateTime());
            prst.setString(6, organise.getUpdater());
            prst.setInt(7,organise.getOrgId());
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

    public int deleteOrganise(Integer id){
        String sql = "delete from tb_org where org_id=?";
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

    public int deleteOrganiseRel(Integer id){
        String sql = "delete from tb_user_org_rel where org_id=?";
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

    public List<Integer> queryUsersByOrgId(Integer id){
        List<Integer> userList = new ArrayList<Integer>();
        String sql = "SELECT uo.* \n" +
                "FROM tb_user_org_rel uo\n" +
                "LEFT JOIN tb_user u\n" +
                "ON u.user_id=uo.user_id\n" +
                "WHERE uo.org_id=?";
        try {
            conn = JDBCUtil.getConnection();
            prst = conn.prepareStatement(sql);
            prst.setInt(1,id);
            rst = prst.executeQuery();
            while (rst.next()){
                userList.add(rst.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
