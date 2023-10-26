public class User {
    private static String username;
    private static String passHash;

    public void user(username, passHash){
        this.username = username;
        this.passHash = passHash;
    }
    public String getUsername(){
        return username;
    }
    public String getPassHash(){
        return passHash;
    }
}

