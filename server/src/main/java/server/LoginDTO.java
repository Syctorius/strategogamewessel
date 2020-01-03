package server;

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

    public LoginDTO(int petId, String userName, String password) {
        this.userId = petId;
        this.userName = userName;
        this.passWord = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "id: " + userId + " name: " + userName + " owner: " + passWord;
    }
}
