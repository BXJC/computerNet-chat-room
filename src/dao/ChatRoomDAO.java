package dao;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChatRoomDAO {
    public boolean getChatRoomById(int roomId){
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql ="select * from chatRoom where roomId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,roomId);
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

    public int getNewId(){
        int newId;
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from chatRoom order by roomId desc limit 1";
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

    public int newChatRoom(String nickname){
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql ="insert into chatRoom (roomname) values (?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,nickname);
            ps.executeUpdate();
            return getNewId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
