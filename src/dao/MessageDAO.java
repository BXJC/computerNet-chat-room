package dao;

import entity.Message;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
//    public List<Message> getMessageByUser(User user){
//        List<Message> messageList = new ArrayList<>();
//        Connection conn = DBUtil.getconn();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            String sql ="select * from messageList where userid = ?";
//            ps = conn.prepareStatement(sql);
//            ps.setInt(1,user.getUserId());
//            rs = ps.executeQuery();
//            while(rs.next())
//            {
//                String msg = rs.getString("time") + "|" + rs.getInt("userId") + "|" + rs.getString("content");
//                messageList.add(msg);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return messageList;
//    }

    public List<Message> getMessageByRoomId(int roomId){
        List<Message> messageList = new ArrayList<>();
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql ="select * from messageList where roomid = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,roomId);
            rs = ps.executeQuery();
            while(rs.next())
            {
                Message message = new Message();
                message.setMessageId(rs.getInt("messageId"));
                message.setUserId(rs.getInt("userId"));
                message.setRoomId(roomId);
                message.setTime(rs.getString("time"));
                message.setContent(rs.getString("content"));
                messageList.add(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closeAll(conn,ps,rs);
        }
        return messageList;
    }

    public void newMessage(Message message){
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        try {
            String sql ="insert into messageList (userid,roomid,time,content) values (?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,message.getUserId());
            ps.setInt(2,message.getRoomId());
            ps.setString(3,message.getTime());
            ps.setString(4,message.getContent());
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closeAll(conn,ps,null);
        }
    }
}
