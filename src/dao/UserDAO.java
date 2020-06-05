package dao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.User;

public class UserDAO {
    public boolean getUserByIdAndPassword(int id, String password){
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql ="select * from user where userId = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int id){
        User user = new User();
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql ="select * from user where userId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next())
            {
                user.setUserId(rs.getInt("userId"));
                user.setNickname(rs.getString("nickname"));
                return user;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getIdByNicknameAndPassword(String nickname, String password){
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql ="select * from user where nickname = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,nickname);
            ps.setString(2,password);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int getNewId(){
        int newId;
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from user order by userId desc limit 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
            newId = rs.getInt("userId");
            return newId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int newUser(String nickname, String password){
        User user = new User(nickname,password);
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql ="insert into User (nickname,password) values (?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,nickname);
            ps.setString(2,password);
            ps.executeUpdate();
            return getNewId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public List<User> getUserListByRoomId(int roomId){
        List<User> userList = new ArrayList<>();
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql ="select * from userList where roomId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,roomId);
            rs = ps.executeQuery();
            while (rs.next()){
                userList.add(getUserById(rs.getInt("userId")));
            }
            return userList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
