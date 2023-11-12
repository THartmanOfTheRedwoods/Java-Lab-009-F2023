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
        Scanner s;
        for (User users : users) {
            s = new Scanner(new FileInputStream(this.dictionary));
            if(users.getPassHash().startsWith("$")) {
                String hash;
                String word;
                do {
                    word = s.nextLine();
                    hash = Crypt.crypt(word, users.getPassHash());
                } while (s.hasNextLine() && !(hash.equals(users.getPassHash().split(":",2)[0])));
        System.out.printf("%nname: %s%npass: %s%n", users.getUsername(), word);
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


// Complete the body of the **parseShadow** method utilizing:
//    * the pre-complete method **getLineCount** to create a user array called Users
//    * imported class FileInputStream
//    * imported class Scanner
//    * a while loop that uses the above 2 imported classes to read the **resources/shadow** file line by line.
//    * the **split** method and the delimiter **:** to split each line into a string array (i.e. String[])
//    * use the first 2 elements of the split string array to create a **new User(element1, element2)**
//    * and finally store each new User into a User array (i.e. User[] users), and return the array.
    public static User[] parseShadow(String shadowFile) throws FileNotFoundException {
        User[] user = new User[getLineCount(shadowFile)];
        try {
            String[] arrNameAndPass = new String[2];
            Scanner s = new Scanner(new FileInputStream(shadowPath));
            int i = 0;
            do {
                arrNameAndPass = s.nextLine().split(":", 2);
                user[i] = new User(arrNameAndPass[0], arrNameAndPass[1]);
                i++;
            } while (s.hasNextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static void getPathAndDict(){
//        Scanner sc = new Scanner(System.in);
        System.out.println("Type the path to your shadow file: ");
        shadowPath = /*sc.nextLine()*/ "resources/shadow";              //splint
        System.out.println(shadowPath);                                 //scaffold
        System.out.println("Type the path to your dictionary file: ");
        dictPath = /*sc.nextLine()*/ "resources/englishSmall.dic";      //splint
        System.out.println(dictPath);                                   //scaffold
    }

    public static void main(String[] args) throws FileNotFoundException {
            getPathAndDict();
            Crack c = new Crack(shadowPath, dictPath);
            c.crack();
    }
}
