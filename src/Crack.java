import org.apache.commons.codec.digest.Crypt;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
/*import java.util.ArrayList;
import java.util.Arrays;*/
import java.util.Scanner;
import java.util.stream.Stream;

public class Crack {
    private final User[] users;
    private final String dictionary;

    public Crack(String shadowFile, String dictionary) throws FileNotFoundException {
        this.dictionary = dictionary;
        this.users = Crack.parseShadow(shadowFile);

        File path = Path.of(this.dictionary).toFile();
        Scanner sc = new Scanner(path);
        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            for (int i = 0; (i < Array.getLength(users)); i++) {
                if (users[i].getPassHash().contains("$")) {
                    String hash = Crypt.crypt(word, users[i].getPassHash());

                    if (hash.equals(users[i].getPassHash())) {
                        System.out.printf("Found password %s for user %s%n.", word, users[i].getUsername());
                    }
                }
            }
        }





    }

    public void crack() throws FileNotFoundException {
    }

    public static int getLineCount(String path) {
        int lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            lineCount = (int)stream.count();
        } catch(IOException ignored) {}
        return lineCount;
    }
    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {
        File path = Path.of(shadowFile).toFile();
        int lineCount = getLineCount(String.valueOf(path));
        User[] users = new User[lineCount];
        Scanner sc = new Scanner(path);
        int i = 0;
        while (lineCount > 0) {
            String line = sc.nextLine();
            String[] lineParts = line.split(":");
            users[i] = new User(lineParts[0],lineParts[1]);
            lineCount--;
            i++;
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
