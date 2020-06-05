package entity;

public class User {
    private int userId;
    private String nickname;
    private String password;
    private String status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User() {
    }

    public User(int userId, String nickname, String status) {
        this.userId = userId;
        this.nickname = nickname;
        this.status = status;
    }

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
