import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * @author Olivia McKittrick
 * @Date 11/7/2023
 */
public class Crack {
    private final User[] users;
    private final String dictionary;

    public Crack(String shadowFile, String dictionary) throws FileNotFoundException {
        this.dictionary = dictionary;
        this.users = Crack.parseShadow(shadowFile);
    }

    public void crack(String dictionaryFile, User[] users) throws FileNotFoundException {
        for (User user : users) {
            if (user.getPassHash().contains("$")) {
                User.crack(dictionary, user);
            }
        }
    }

    public static int getLineCount(String path) throws FileNotFoundException {
        int lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            lineCount = (int)stream.count();
        } catch(IOException ignored) {}
        return lineCount;
    }

    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {
        int lineCount = getLineCount(shadowFile);
        User[] users = new User[lineCount];

        try (FileInputStream fileInputStream = new FileInputStream(shadowFile);
             Scanner scanner = new Scanner(fileInputStream)) {
            int index = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":"); // colon delimiter splits each line from the shadow file
                if (parts.length >= 2) { //checking if the parts array has two elements to ensure valid username/password hash per line
                    String username = parts[0];
                    String passHash = parts[1];
                    users[index] = new User(username, passHash); // create and store user object
                    index++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users; // return the array of user objects
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

    private void crack() {
    }
}