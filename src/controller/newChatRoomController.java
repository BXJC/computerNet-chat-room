package controller;

import dao.ChatRoomDAO;
import dao.UserDAO;
import entity.ChatRoom;
import entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import other.ChatClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class newChatRoomController {
    @FXML ListView idTextList;
    @FXML TextField idToInvite;
    @FXML TextField chatRoomName;
    List<User> userList = new ArrayList<>();
    private List<String> infoList = new ArrayList<>();
    ChatRoomDAO chatRoomDAO = new ChatRoomDAO();
    private UserDAO userDAO = new UserDAO();
    private User user;
    private User currentUser;
    private ChatClient chatClient;
    public void Init(User user, ChatClient chatClient){
        this.currentUser = user;
        this.chatClient = chatClient;
    }
    public void invite(ActionEvent actionEvent) {
        int id = Integer.parseInt(idToInvite.getText());
        if((user = userDAO.getUserById(id)) == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Please check the id you entered！");
            alert.show();
        }
        else if (id == currentUser.getUserId())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("you can't invite your self！");
            alert.show();
            idToInvite.clear();
        }
        else if(containThisUser(id))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("this user already in list！");
            alert.show();
            idToInvite.clear();
        }
        else {
            userList.add(user);
            infoList.add( "id: " +  user.getUserId() + " " + user.getNickname());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    idTextList.setItems(FXCollections.observableList(infoList));
                }
            });
        }
    }

    public void remove(ActionEvent actionEvent) {
        String []list = idTextList.getSelectionModel().getSelectedItem().toString().split(" ");
        User user = userDAO.getUserById(Integer.parseInt(list[1]));
        infoList.remove(idTextList.getSelectionModel().getSelectedItem().toString());
        userList.remove(user);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                idTextList.setItems(FXCollections.observableList(infoList));
            }
        });
    }

    public void OK(ActionEvent actionEvent) throws Exception{
        if(chatRoomName.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Chat room name can't be empty！");
            alert.show();
        }
        else if(infoList.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("you can invite at least one user！");
            alert.show();
        }
        else {
            userList.add(currentUser);
            ChatRoom chatRoom = new ChatRoom(chatRoomName.getText(),userList,null);
            int roomId = chatRoomDAO.newChatRoom(chatRoom);
            chatRoom.setRoomId(roomId);
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
            controller.Init(userDAO.getUserById(currentUser.getUserId()),chatClient);
//            mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
            mainStage.setResizable(false); /* 设置窗口不可改变 */
//            mainStage.setAlwaysOnTop(true);
            mainStage.show();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Congratulations!");
            alert.setContentText("chat room created！ room id: " + roomId);
            alert.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }

    }

    public void createRet(ActionEvent actionEvent) throws Exception{
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
//        mainStage.initStyle(StageStyle.TRANSPARENT); /* 透明标题栏 */
        mainStage.setResizable(false); /* 设置窗口不可改变 */
//        mainStage.setAlwaysOnTop(true);
        mainStage.show();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public boolean containThisUser(int id){
        for(User user : userList)
            if (user.getUserId() == id)
                return true;
            return false;
    }
}
