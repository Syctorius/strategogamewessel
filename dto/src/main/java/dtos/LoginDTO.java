package dtos;

public class LoginDTO {

    private int userId;


    private String userName;


    private String passWord;

    /**
     * No-argument constructor.
     * Note that a no-argument constructor is
     * required for data transfer objects when a
     * constructor with arguments is also declared.
     */
    public LoginDTO() {
        // Nothing
    }

    public LoginDTO(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = password;
    }
    public LoginDTO(String userName, String password) {
        this.userName = userName;
        this.passWord = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return passWord;
    }

    public void setPassword(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "id: " + userId + " name: " + userName + " owner: " + passWord;
    }
}
