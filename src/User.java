public class User {
    private String username;
    private String passHash;
public User(String username, String passHash){
    this.passHash = passHash;
    this.username = username;
}
    public String getUserName(){
        return username;
    }

    public String  getPassHash(){
        return passHash;
    }
}
