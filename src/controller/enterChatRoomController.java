package controller;

import dao.ChatRoomDAO;
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
import other.ChatClient;

import java.net.URL;

public class enterChatRoomController {
    @FXML TextField idText;
    ChatRoomDAO chatRoomDAO = new ChatRoomDAO();
    User user;
    ChatClient chatClient;
    ChatRoom chatRoom;
    boolean contain = false;
    public void Init(User user, ChatClient chatClient){
        this.user = user;
        this.chatClient = chatClient;
    }
    public void OK(ActionEvent actionEvent) throws Exception{
        if((chatRoom = chatRoomDAO.getChatRoomById(Integer.parseInt(idText.getText()))) != null)
        {
            for(User user: chatRoom.getUserList())
                if(user.getUserId() == this.user.getUserId()) contain = true;
            if(!contain)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error");
                alert.setContentText("you are not in this chatRoom's userList");
                alert.show();
            }
            else {
                Stage mainStage = new Stage();
                URL location = getClass().getResource("/ui/chatRoomFXML.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Parent root = fxmlLoader.load();
                mainStage.setTitle("I Chat");
                Scene scene = new Scene(root, 740, 563);
                mainStage.setScene(scene);
                chatRoomController controller = fxmlLoader.getController();   //获取Controller的实例对象
                controller.Init(user,chatClient,chatRoom);
                mainStage.show();
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Please check the id you entered！");
            alert.show();
        }
    }
}
