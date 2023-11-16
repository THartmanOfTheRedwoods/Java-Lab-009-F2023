/**
 * @author Trevor Hartman
 * @author MJ Fracess
 *
 * @since Version 1.0
 **/
public class User {

    private String username;
    private String passHash;

    public User(String username, String passHash){
        this.username = username;
        this.passHash = passHash;

    }

    public String getUsername() {
        return username;
    }

    public String getPassHash() {
        return passHash;
    }
}

