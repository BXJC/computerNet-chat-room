package entity;

import java.util.ArrayList;
import java.util.List;

public class TwoPeopleChat {
    private int roomId;
    private User user1;
    private User user2;
    private List<Message> messageList = new ArrayList<>();

    public TwoPeopleChat(int roomId, User user1, User user2, List<Message> messageList) {
        this.roomId = roomId;
        this.user1 = user1;
        this.user2 = user2;
        this.messageList = messageList;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
