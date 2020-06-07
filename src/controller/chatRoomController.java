package controller;

import dao.MessageDAO;
import dao.UserDAO;
import entity.ChatRoom;
import entity.Message;
import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import other.ChatClient;

import java.net.URL;
import java.util.List;

public class chatRoomController extends Thread{
    @FXML TextArea messageArea;
    @FXML TextField messageToSend;
    @FXML Label chatRoomName;
    private UserDAO userDAO = new UserDAO();
    private MessageDAO messageDAO = new MessageDAO();
    private List<User> userList;
    private ChatClient chatClient;
    private String msg = "";
    private ChatRoom chatRoom;
    private User currentUser;
    private boolean initMessageList = false;
    public void Init(User user, ChatClient chatClient, ChatRoom chatRoom){
        this.currentUser = user;
        userList = chatRoom.getUserList();
        this.chatRoom = chatRoom;
        this.chatClient = chatClient;
        chatRoomName.setText(chatRoom.getRoomName());
        for(Message message : chatRoom.getMessageList())
            msg += message.getTime() + ": " + userDAO.getUserById(message.getUserId()).getNickname() + "\n" + message.getContent() + "\n";
        if (chatRoom.getMessageList().size() != 0) initMessageList = true;
        messageArea.setText(msg);
        this.start();
    }

    public void Send(ActionEvent actionEvent) throws Exception{
        String content = messageToSend.getText();
        Message message = new Message(currentUser.getUserId(),chatRoom.getRoomId(),content);
        String msg = message.getTime() + "&" + userDAO.getUserById(message.getUserId()).getNickname() + "&" + content + "&" + chatRoom.getRoomId();
        for(User user : userList)
            msg += "|" + user.getUserId();
        chatClient.sendMessage(msg);
        messageDAO.newMessage(message);
//        messageArea.appendText(message.getTime() + ": " + userDAO.getUserById(message.getUserId()).getNickname() + "\n" + message.getContent() + "\n");
        messageToSend.clear();
    }

    public synchronized void run(){
        String message;
        while (true) {
            try {
                if ((message = chatClient.getMessage()) != null){
                    System.out.println("接收消息: " + message);
                    String []whole = message.split("&");
                    if(chatRoom.getRoomId() == Integer.parseInt(whole[3]))
                    {
                        String msgToAdd = whole[0] + ": " + whole[1] + "\n" + whole[2] + "\n";
                        messageArea.appendText(msgToAdd);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void chatRoomRet(ActionEvent actionEvent) throws Exception{
        Stage mainStage = new Stage();
        URL location = getClass().getResource("/ui/mainFXML.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        mainStage.setTitle("I Chat");
        Scene scene = new Scene(root, 456, 282);
        mainStage.setScene(scene);
        mainController controller = fxmlLoader.getController();   //获取Controller的实例对象
        controller.Init(currentUser,chatClient);
//                    mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
//                    mainStage.setResizable(false); /* 设置窗口不可改变 */
//                    mainStage.setAlwaysOnTop(true);
        this.stop();
        mainStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
