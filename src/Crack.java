/*Finn Clark
* 11/09/2023
* CIS - 12
* Hartman */

import org.apache.commons.codec.digest.Crypt;

import java.io.*;
import java.nio.CharBuffer;
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
        Scanner in = new Scanner(new FileInputStream(this.dictionary), StandardCharsets.UTF_8);
        while(in.hasNextLine()){
            String word = in.nextLine();
            for (User user : users){
                if (user.getPassHash().contains("$")){
                    String hash = Crypt.crypt(word, user.getPassHash());
                    if(hash.equals(user.getPassHash())){
                        System.out.printf("Found password %s for user %s%n", word, user.getUsername());
                    };
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
        String line;
        Scanner scan = new Scanner(new FileInputStream(shadowFile), StandardCharsets.UTF_8);
        int index = 0;
        while(scan.hasNextLine()){
            String[] userLine = scan.nextLine().split(":");
            User user = new User(userLine[0], userLine[1]);
            users[index] = user;
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
