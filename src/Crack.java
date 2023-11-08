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
    }

    public static int getLineCount(String path) {
        int lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(path), StandardCharsets.UTF_8)) {
            lineCount = (int)stream.count();
        } catch(IOException ignored) {}
        return lineCount;
    }


// Complete the body of the **parseShadow** method utilizing:
//    * the pre-complete method **getLineCount** to create a user array called Users
//    * imported class FileInputStream
//    * imported class Scanner
//    * a while loop that uses the above 2 imported classes to read the **resources/shadow** file line by line.
//    * the **split** method and the delimiter **:** to split each line into a string array (i.e. String[])
//    * use the first 2 elements of the split string array to create a **new User(element1, element2)**
//    * and finally store each new User into a User array (i.e. User[] users), and return the array.
    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {
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
