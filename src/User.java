public class User {
    private final String username;
    private final String passHash;

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

    @Override
    public String toString() {
        return String.format("""
               User {
                 username: %s,
                 passHash: %s,
                """, this.username, this.passHash);
    }
}
