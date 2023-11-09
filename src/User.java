public class User { // class with two string instance variables
    private final String username;
    private final String passHash;

    public User(String username, String passHash) { // User constructor that has 2 parameters
        this.username = username; // assigns parameters to instance variables
        this.passHash = passHash; // assigns parameters to instance variables
    }

    public static void crack(String dictionary, User user) {
    }

    public String getUsername() { // method getUsername that returns instance variable
        return username; // returns
    }

    public String getPassHash() { // method getPassHash that returns instance variable
        return passHash;
    }
}
