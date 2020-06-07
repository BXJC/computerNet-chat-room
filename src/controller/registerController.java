package controller;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class registerController {
    @FXML TextField nicknameText;
    @FXML PasswordField passwordText;
    @FXML PasswordField repeatPassword;
    private UserDAO userDAO = new UserDAO();

    public void register(ActionEvent actionEvent) throws Exception{
        if(nicknameText.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Please enter your nickname！");
            alert.show();
        }
        else if(passwordText.getText().isEmpty() || repeatPassword.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Please complete your password！");
            alert.show();
        }
        else if(!passwordText.getText().equals(repeatPassword.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("The two passwords are different！");
            alert.show();
        }
        else {
            String name = nicknameText.getText();
            String password = passwordText.getText();
            int id = userDAO.newUser(name,password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/ui/loginFXML.fxml"));
            Scene scene = new Scene(root,462,256);
            primaryStage.setScene(scene);
            primaryStage.setTitle("登录");
            primaryStage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            alert.setHeaderText("Congratulations");
            alert.setContentText("Registered！ your id is " + id);
            alert.show();
        }
    }

    public void registerRet(ActionEvent actionEvent) throws Exception{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/ui/loginFXML.fxml"));
        Scene scene = new Scene(root,462,256);
        primaryStage.setScene(scene);
        primaryStage.setTitle("登录");
        primaryStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}
