package controller;

import entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import other.ChatClient;

import java.net.URL;


public class mainController {
    @FXML Label welcome;
    private ChatClient chatClient;
    private User user;
    public void Init(User user, ChatClient chatClient){
        this.user = user;
        welcome.setText("Welcome: " + user.getNickname());
        this.chatClient = chatClient;
    }


    public void startNewChat(ActionEvent actionEvent) throws Exception{
        Stage mainStage = new Stage();
        URL location = getClass().getResource("/ui/infoFXML.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        mainStage.setTitle("I Chat");
        Scene scene = new Scene(root, 393, 130);
        mainStage.setScene(scene);
        enterInfoController controller = fxmlLoader.getController();   //获取Controller的实例对象
        controller.Init(user,chatClient);
//        mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
        mainStage.setResizable(false); /* 设置窗口不可改变 */
//        mainStage.setAlwaysOnTop(true);
        mainStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void EnterChatRoom(ActionEvent actionEvent) throws Exception{
        Stage mainStage = new Stage();
        URL location = getClass().getResource("/ui/enterChatRoomFXML.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        mainStage.setTitle("I Chat");
        Scene scene = new Scene(root, 441, 144);
        mainStage.setScene(scene);
        enterChatRoomController controller = fxmlLoader.getController();   //获取Controller的实例对象
        controller.Init(user,chatClient);
//        mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
        mainStage.setResizable(false); /* 设置窗口不可改变 */
//        mainStage.setAlwaysOnTop(true);
        mainStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void createChatRoom(ActionEvent actionEvent) throws Exception{
        Stage mainStage = new Stage();
        URL location = getClass().getResource("/ui/newChatRoomFXML.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        mainStage.setTitle("I Chat");
        Scene scene = new Scene(root, 458, 348);
        mainStage.setScene(scene);
        newChatRoomController controller = fxmlLoader.getController();   //获取Controller的实例对象
        controller.Init(user,chatClient);
//        mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
        mainStage.setResizable(false); /* 设置窗口不可改变 */
//        mainStage.setAlwaysOnTop(true);
        mainStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void mainRet(ActionEvent actionEvent) throws Exception{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ui/loginFXML.fxml"));
        Scene scene = new Scene(root,462,256);
        primaryStage.setScene(scene);
        primaryStage.setTitle("登录");
//        primaryStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
        primaryStage.setResizable(false); /* 设置窗口不可改变 */
//        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
