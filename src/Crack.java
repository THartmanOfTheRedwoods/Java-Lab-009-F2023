// Naqibullah Haiwadpal
//11/11/2023

import org.apache.commons.codec.digest.Crypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        try (Scanner scanner = new Scanner(new FileInputStream(dictionary))) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                for (User user : users) {
                    if (user.getPassHash().contains("$")) {
                        String hash = Crypt.crypt(word, user.getPassHash());
                        if (user.getPassHash().equals(hash)) {
                            System.out.println("Found password " + word + " for user " + user.getUsername() + ".");
                        }
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
                    users[index++] = new User(parts[0], parts[1]);
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

class User {
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
