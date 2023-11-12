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
    private static String shadowPath;
    private static String dictPath;


    public Crack(String shadowFile, String dictionary) throws FileNotFoundException {
        this.dictionary = dictionary;
        this.users = Crack.parseShadow(shadowFile);
    }

    public void crack() throws FileNotFoundException {
        Scanner s = new Scanner(new FileInputStream(this.dictionary));
        while (s.hasNextLine()) {
            String word = s.nextLine();
            int i = 0;
            System.out.println(i);
            for (User user : users) {
                if(user.getPassHash().startsWith("$")) {
                    String hash = Crypt.crypt(word, user.getPassHash());
//                    System.out.println(hash);           //scaffold
                    System.out.printf("User %d%nname: %s%npass: %s%n%n", i, user.getUsername(), word);
                    i++;
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
        User[] user = new User[getLineCount(shadowFile)];
//        System.out.printf("File as %d lines%n", user.length);            //scaffold

        try {
            String line = "";           //scaffold
            String[] arrNameAndPass = new String[2];
            System.out.println(arrNameAndPass.length);
            Scanner s = new Scanner(new FileInputStream(shadowPath));
            int i = 0;
            do {
                arrNameAndPass = s.nextLine().split(":", 2);
                user[i] = new User(arrNameAndPass[0], arrNameAndPass[1]);
//                System.out.printf("user: %s%npass: %s%n%n", user[i].getUsername(), user[i].getPassHash());    //scaffold
                i++;
            } while (s.hasNextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static void getPathAndDict(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Type the path to your shadow file: ");
        shadowPath = /*sc.nextLine()*/ "resources/shadow";              //splint
        System.out.println(shadowPath);                                 //scaffold
        System.out.println("Type the path to your dictionary file: ");
        dictPath = /*sc.nextLine()*/ "resources/englishSmall.dic";      //splint
        System.out.println(dictPath);                                   //scaffold
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            getPathAndDict();
            Crack c = new Crack(shadowPath, dictPath);
            c.crack();
        } catch (IOException e) {
        }
    }
}
