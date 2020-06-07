package controller;

import dao.ChatRoomDAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import other.ChatClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class enterInfoController {
    @FXML TextField idText;
    private UserDAO userDAO = new UserDAO();
    private ChatRoomDAO chatRoomDAO = new ChatRoomDAO();
    private MessageDAO messageDAO = new MessageDAO();
    private User user1;
    private User user2;
    private ChatClient chatClient;
    private ChatRoom chatRoom;
    public void Init(User user, ChatClient chatClient){
        this.user1 = user;
        this.chatClient = chatClient;
    }

    public void OK(ActionEvent actionEvent) throws Exception{
        int id = Integer.parseInt(idText.getText());
        List<Message> messageList;
        if(userDAO.getUserById(id) != null && id != user1.getUserId())
        {
            List<User> userList = new ArrayList<>();
            userList.add(user1);
            user2 = userDAO.getUserById(id);
            userList.add(user2);
            String roomName = "chat: " + user1.getNickname() + "|" + userDAO.getUserById(id).getNickname();
            if(chatRoomDAO.getRoomIdByUsers(user1,user2) != -1){
                int roomId = chatRoomDAO.getRoomIdByUsers(user1,user2);
                messageList = messageDAO.getMessageByRoomId(roomId);
                chatRoom = new ChatRoom(roomName,userList,messageList);
                chatRoom.setRoomId(roomId);
            }else {
                //创建新的聊天
                messageList = new ArrayList<>();
                chatRoom = new ChatRoom(roomName,userList,messageList);
                chatRoom.setRoomId(chatRoomDAO.newChatRoom(chatRoom));
            }
            Stage chatStage = new Stage();
            URL location = getClass().getResource("/ui/twoPeopleChatFXML.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load();
            chatStage.setTitle("I Chat");
            Scene scene = new Scene(root, 809, 559);
            chatStage.setScene(scene);
            twoPeopleChatController controller = fxmlLoader.getController();   //获取Controller的实例对象
            controller.Init(chatClient,chatRoom);
//            chatStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
            chatStage.setResizable(false); /* 设置窗口不可改变 */
//            chatStage.setAlwaysOnTop(true);
            chatStage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Please check the id you entered！");
            alert.show();
        }
    }

    public void enterRet(ActionEvent actionEvent) throws Exception{
        Stage mainStage = new Stage();
        URL location = getClass().getResource("/ui/mainFXML.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        mainStage.setTitle("I Chat");
        Scene scene = new Scene(root, 452, 254);
        mainStage.setScene(scene);
        mainController controller = fxmlLoader.getController();   //获取Controller的实例对象
        controller.Init(user1,chatClient);
//        mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
        mainStage.setResizable(false); /* 设置窗口不可改变 */
//        mainStage.setAlwaysOnTop(true);
        mainStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
