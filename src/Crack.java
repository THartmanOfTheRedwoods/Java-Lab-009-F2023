/**
 *
 * @author Trevor Hartman
 * @author Cameron Meng
 *
 * @since Version 1.0
 *
 */
import org.apache.commons.codec.digest.Crypt;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;
public class Crack {
    private final User[] users;
    private final String dictionary;
    public Crack(String shadowFile, String dictionary) throws FileNotFoundException {
        this.dictionary = dictionary;
        this.users = Crack.parseShadow(shadowFile);
    }
    public void crack() throws FileNotFoundException {
        Scanner s = new Scanner(new FileInputStream(this.dictionary));
        while(s.hasNextLine()) {
            String word = s.nextLine();
            for (User user : users) {
                if (user.getPassHash().startsWith("$")) {
                    String hash = Crypt.crypt(word, user.getPassHash());
                    if (hash.equals(user.getPassHash())) {
                        System.out.printf("Found password %s for user %s.%n", word, user.getUsername());
                    }
                }
            }
        }
    }
    public static int getLineCount(String path) {
        int lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            lineCount = (int)stream.count();
        } catch(IOException ignored) {}
        return lineCount;
    }
    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {
        int lineCount = getLineCount(shadowFile);
        User[] users = new User[lineCount];
        try (Scanner scanner = new Scanner(new FileInputStream(shadowFile))) {
            int index = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String username = parts[0];
                    String passwordHash = parts[1];
                    User user = new User(username, passwordHash);
                    users[index++] = user;
                }
            }
        }

        return users;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Type the path to your shadow file: ");
        String shadowPath = sc.nextLine();
        System.out.print("Type the path to your dictionary file: ");
        String dictPath = sc.nextLine();

        Crack c = new Crack(shadowPath, dictPath);
        c.crack();
    }
}
