// Daniel Faulkner, CIS 12, 10/28/23
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
        this.users = parseShadow(shadowFile);
    }

    public void crack() throws FileNotFoundException {
        try (InputStream is = new FileInputStream(this.dictionary);
             Scanner sc = new Scanner(is)) {
            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                for (User user : this.users) {
                    String passHash = user.getPassHash();
                    if (passHash.contains("$")) {
                        String hash = Crypt.crypt(word, passHash);
                        if (hash.equals(passHash)) {
                            System.out.println("Found password " + word + " for user " + user.getUsername());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLineCount(String path) {
        int lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            lineCount = (int) stream.count();
        } catch (IOException ignored) {
        }
        return lineCount;
    }

    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {
        User[] users = new User[getLineCount(shadowFile)];
        try (InputStream is = new FileInputStream(shadowFile);
             Scanner sc = new Scanner(is)) {
            int index = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(":");
                User user = new User(parts[0], parts[1]);
                users[index] = user;
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
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

class User {
    private String username;
    private String passHash;

    public User(String username, String passHash) {
        this.username = username;
        this.passHash = passHash;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassHash() {
        return this.passHash;
    }
}
