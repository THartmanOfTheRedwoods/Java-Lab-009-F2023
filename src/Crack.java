import org.apache.commons.codec.digest.Crypt;

import java.io.*;
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
        Scanner sc = new Scanner(new FileInputStream(this.dictionary), StandardCharsets.UTF_8);
        while(sc.hasNextLine()){
            String word = sc.nextLine();
            for(User user : users){
                if(user.getPassHash().contains("$")){
                    String hash = Crypt.crypt(word, user.getPassHash());
                    if(hash.equals(user.getPassHash())){
                        System.out.printf("Ya did it buster. Found password %s for user %s%n", word, user.getUsername());
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
        User[] users = new User[Crack.getLineCount(shadowFile)];

        Scanner sc = new Scanner(new FileInputStream(shadowFile), StandardCharsets.UTF_8);
        int index = 0;
        while(sc.hasNextLine()){
            String[] userLine = sc.nextLine().split(":");
            users[index] = new User(userLine[0], userLine[1]);
            index++;
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
