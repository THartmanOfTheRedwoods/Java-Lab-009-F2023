import org.apache.commons.codec.digest.Crypt;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class Crack {
    private final User[] users;
    private final String dictionary;

    public Crack(String shadowFile, String dictionary) throws FileNotFoundException {
        this.users = parseShadow(shadowFile);
        this.dictionary = dictionary;
    }

    public void crack() throws FileNotFoundException {
        try (Scanner s = new Scanner(new FileInputStream(this.dictionary), StandardCharsets.UTF_8)) {
            while (s.hasNextLine()) {
                String word = s.nextLine();
                for (User user : users) {
                    if (user.getPassHash().contains("$")) {
                        String hash = Crypt.crypt(word, user.getPassHash());
                        if (hash.equals(user.getPassHash())) {
                            System.out.printf("Found password %s for user %s.%n", word, user.getUsername());
                        }
                    }
                }
            }
        }
    }

    public static int getLineCount(String path) throws IOException {
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            return (int) stream.count();
        }
    }

    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {
        User[] users = new User[getLineCount(shadowFile)];
        try (Scanner s1 = new Scanner(new FileInputStream(shadowFile), StandardCharsets.UTF_8)) {
            int index = 0;
            while (s1.hasNextLine()) {
                String[] userLine = s1.nextLine().split(":");
                users[index] = new User(userLine[0], userLine[1]);
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Example usage:
        String shadowFile = "resources/shadow";
        String dictionaryFile = "resources/englishSmall.dic";

        Crack cracker = new Crack(shadowFile, dictionaryFile);
        cracker.crack();
    }
}



    public void crack(String dictionaryFile) throws FileNotFoundException {
        try (Scanner s = new Scanner(new FileInputStream(dictionaryFile), StandardCharsets.UTF_8)) {
            while (s.hasNextLine()) {
                String word = s.nextLine();
                // Iterate through the user array
                for (User user : users) {
                    // Check for specific conditions in the password hash
                    if (user.getPassHash().contains("$")) {
                        // Use Crypt library to build a password hash
                        String hash = Crypt.crypt(word, user.getPassHash());
                        // Compare the generated hash with user's password hash
                        if (hash.equals(user.getPassHash())) {
                            // If the hashes match, print the password and username
                            System.out.printf("Found password %s for user %s.%n", word, user.getUsername());
                        }
                    }
                }
            }
        }
    }}