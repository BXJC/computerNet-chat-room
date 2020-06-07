package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/ui/loginFXML.fxml"));
        Scene scene = new Scene(root,462,256);
        primaryStage.setScene(scene);
        primaryStage.setTitle("登录");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
