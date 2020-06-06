package entity;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private int MessageId;
    private int userId;
    private int roomId;
    private String time;
    private String content;

    public Message() {
    }

    public Message(int userId, int roomId, String content) {
        this.userId = userId;
        this.roomId = roomId;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("MM月dd日 HH:mm:ss");
        this.time = simpleDateFormat.format(date);
        this.content = content;
    }

    public int getMessageId() {
        return MessageId;
    }

    public void setMessageId(int messageId) {
        MessageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
