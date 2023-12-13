/*
*
* @author Angelina Perez
* @author Trevor Hartman
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
        while (s.hasNextLine()) {
            String word = s.nextLine();
            for (User user : users) {
                if (user.getPassHash().contains("$")) {
                    String hash = Crypt.crypt(word, user.getPassHash());
                    if (hash.equals(user.getPassHash())) {
                        System.out.println("Found password" + word + " for user" + user.getUsername() + ".");
                    }
                }

            }
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
        Scanner s = new Scanner(new FileInputStream(shadowFile));
        int i = 0;
        while (s.hasNextLine()) {
            String[] line = s.nextLine().split(":");
            if (line.length >= 2) {
                String username = line[0];
                String passHash = line[1];
                i++;
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
