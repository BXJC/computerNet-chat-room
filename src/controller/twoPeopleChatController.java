package controller;

import dao.MessageDAO;
import dao.UserDAO;
import entity.ChatRoom;
import entity.Message;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import other.ChatClient;

import java.util.List;

public class twoPeopleChatController extends Thread{
    @FXML TextArea messageArea;
    @FXML TextField messageToSend;
    @FXML Label chatWith;
    private UserDAO userDAO = new UserDAO();
    private MessageDAO messageDAO = new MessageDAO();
    private User user1;
    private User user2;
    private ChatClient chatClient;
    private String msg ="";
    private ChatRoom chatRoom;
    public void Init(ChatClient chatClient, ChatRoom chatRoom){
        this.user1 = chatRoom.getUserList().get(0);
        this.user2 = chatRoom.getUserList().get(1);
        this.chatRoom = chatRoom;
        this.chatClient = chatClient;
        chatWith.setText("Chat with: " + user2.getNickname());
        for(Message message : chatRoom.getMessageList())
            msg += message.getTime() + ": " + userDAO.getUserById(message.getUserId()).getNickname() + "\n" + message.getContent() + "\n";
        messageArea.setText(msg);
        this.start();
    }

    public void Send(ActionEvent actionEvent) throws Exception{
        String content = messageToSend.getText();
        Message message = new Message(user1.getUserId(),chatRoom.getRoomId(),content);
        String msg = message.getTime() + "&" + userDAO.getUserById(message.getUserId()).getNickname() + "&" + content + "|" + user2.getUserId();
        chatClient.sendMessage(msg);
        messageDAO.newMessage(message);
        messageArea.appendText(message.getTime() + ": " + userDAO.getUserById(message.getUserId()).getNickname() + "\n" + message.getContent() + "\n");
        messageToSend.clear();
    }

    public synchronized void run(){
        String message;
        while (true) {
            try {
                if ((message = chatClient.getMessage()) != null){
                     String []whole = message.split("&");
                     String msgToAdd = whole[0] + ": " + whole[1] + "\n" + whole[2] + "\n";
                     messageArea.appendText(msgToAdd);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
