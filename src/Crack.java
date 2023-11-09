import org.apache.commons.codec.digest.Crypt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
        File dicStream= new File(dictionary);
        Scanner sc = new Scanner(dicStream);
        int j = 0;
        String[] y;
        while (sc.hasNextLine()) {
            String pw = sc.nextLine();
            for (User u : users) {
                if(u.getPassHash().startsWith("$")){

                    String hash = Crypt.crypt(pw, u.getPassHash());
                    if(hash.equals(u.getPassHash()));{
                        System.out.printf("Found password %s for user %s.%n", pw, u.getUsername());
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

        User[] users = new User[getLineCount(shadowFile)];
        File file = new File(shadowFile);
        String[] x;
        int i = 0;
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
          x  = sc.nextLine().split(":");
         users[i] = new User(x[0], x[1]);
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
