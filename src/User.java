/**
 *
 * @author Cassandra Portlock
 *
 * @since Version 1
 *
 */

public class User {
    private String username;
    private String passHash;

    public User(String username, String passHash) {
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
