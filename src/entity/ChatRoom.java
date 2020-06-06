package entity;

import java.util.List;

public class ChatRoom {
    private int roomId;
    private String roomName;
    private List<User> userList;
    private List<Message> messageList;

    public ChatRoom(String roomName, List<User> userList, List<Message> messageList) {
        this.roomName = roomName;
        this.userList = userList;
        this.messageList = messageList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
