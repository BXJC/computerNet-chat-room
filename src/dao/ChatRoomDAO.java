package dao;

import entity.ChatRoom;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomDAO {
    public ChatRoom getChatRoomById(int roomId){
        MessageDAO messageDAO = new MessageDAO();
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ChatRoom chatRoom;
        try {
            String sql ="select * from chatRoom where roomId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,roomId);
            rs = ps.executeQuery();
            if(rs.next())
            {
                chatRoom = new ChatRoom(rs.getString("roomName"),getUserListByRoomId(roomId),messageDAO.getMessageByRoomId(roomId));
                chatRoom.setRoomId(roomId);
                return chatRoom;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closeAll(conn,ps,rs);
        }
        return null;
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
            newId = rs.getInt("roomId");
            return newId;
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closeAll(conn,ps,rs);
        }
        return -1;
    }

    public int getRoomIdByUsers(User user1, User user2){
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select roomId from userList where userId = ? and roomId = (select roomId from userList where userid = ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,user1.getUserId());
            ps.setInt(2,user2.getUserId());
            rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closeAll(conn,ps,rs);
        }
        return -1;
    }

    public int newChatRoom(ChatRoom chatRoom){
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        try {
            String sql ="insert into chatRoom (roomname) values (?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,chatRoom.getRoomName());
            ps.executeUpdate();
            chatRoom.setRoomId(getNewId());
            insertUserList(chatRoom);
            return getNewId();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.closeAll(conn,ps,null);
        }
        return -1;
    }

    public List<User> getUserListByRoomId(int roomId){
        UserDAO userDAO = new UserDAO();
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> userList =  new ArrayList<>();
        try {
                String sql ="select * from userList where roomId = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1,roomId);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    userList.add(userDAO.getUserById(rs.getInt("userId")));
                }
                return userList;
        }catch (Exception e){
            e.printStackTrace();
        }
        DBUtil.closeAll(conn,ps,null);
        return null;
    }

    public int insertUserList(ChatRoom chatRoom){
        Connection conn = DBUtil.getconn();
        PreparedStatement ps = null;
        List<User> userList =  chatRoom.getUserList();
        try {
            for(User user : userList)
            {
                String sql ="insert into userList (userId,roomId) values (?,?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1,user.getUserId());
                ps.setInt(2,chatRoom.getRoomId());
                ps.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        DBUtil.closeAll(conn,ps,null);
        return -1;
    }
}
