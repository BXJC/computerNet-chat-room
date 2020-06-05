package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import other.ChatClient;


public class mainController {
    @FXML Label welcome;
    private ChatClient chatClient;
    public void Init(String nickname, ChatClient chatClient){
        welcome.setText("Welcome: " + nickname);
        this.chatClient = chatClient;
    }


    public void startNewChat(ActionEvent actionEvent) throws Exception{
        chatClient.sendMessage("Hi");
    }

    public void EnterChatRoom(ActionEvent actionEvent) {
    }

    public void createChatRoom(ActionEvent actionEvent) {
    }
}
