package controller;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import other.ChatClient;

import java.net.URL;


public class loginController {
    private UserDAO userDAO = new UserDAO();

    @FXML private TextField userId;
    @FXML private PasswordField password;
    @FXML private Label message;

    public void Login(ActionEvent actionEvent) {
        String strId = userId.getText();
        String strPwd = password.getText();
        if(strId.isEmpty() || strPwd.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("警告");
            alert.setContentText("用户名和密码均不能为空！");
            alert.show();
        }
        else
        {
            if(userDAO.getUserByIdAndPassword(Integer.parseInt(strId),strPwd))
            {
                try {
                    ChatClient chatClient = new ChatClient(Integer.parseInt(strId));
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
                    controller.Init(userDAO.getUserById(Integer.parseInt(strId)),chatClient);
                    mainStage.show();
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    chatClient.sendMessage(strId);
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("警告");
                    alert.setContentText("未能连接到服务器！");
                    alert.show();
                    e.printStackTrace();
                }

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("警告");
                alert.setContentText("用户名或密码错误！");
                alert.show();
                password.clear();
            }
          }
    }

    public void register(ActionEvent actionEvent) {
    }
}
