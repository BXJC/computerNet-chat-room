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
import javafx.stage.StageStyle;
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
            alert.setHeaderText("Warning");
            alert.setContentText("User id and password both can't be empty！");
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
//                    mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
                    mainStage.setResizable(false); /* 设置窗口不可改变 */
//                    mainStage.setAlwaysOnTop(true);
                    mainStage.show();
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    chatClient.sendMessage(strId);
                }catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Warning");
                    alert.setContentText("Can't connect to server！");
                    alert.show();
                    e.printStackTrace();
                }

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Warning");
                alert.setContentText("Wrong user id or password！");
                alert.show();
                password.clear();
            }
          }
    }

    public void register(ActionEvent actionEvent) throws Exception{
        Stage mainStage = new Stage();
        URL location = getClass().getResource("/ui/registerFXML.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        mainStage.setTitle("I Chat");
        Scene scene = new Scene(root, 452, 254);
        mainStage.setScene(scene);
        registerController controller = fxmlLoader.getController();   //获取Controller的实例对象
//        mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
        mainStage.setResizable(false); /* 设置窗口不可改变 */
//        mainStage.setAlwaysOnTop(true);
        mainStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
