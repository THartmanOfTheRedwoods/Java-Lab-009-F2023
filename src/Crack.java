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
    private static String fileContent = "";



    public Crack(String shadowFile, String dictionary) throws FileNotFoundException {
        this.dictionary = dictionary;
        this.users = Crack.parseShadow(shadowFile);
    }

    public void crack() throws FileNotFoundException {
        Scanner s = new Scanner(new FileInputStream(this.dictionary));
        while (s.hasNextInt()) {
            String word = s.nextLine();
            for (User user : users) {
                if(user.getPassHash().startsWith("$")) {
                    String hash = Crypt.crypt(word, user.getPassHash());
                    System.out.println(hash);           //scaffold
                    System.out.printf("Found password %s for user %s.", word, user.getUsername());
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
        User[] arrayUser = new User[getLineCount(shadowFile)];
        System.out.printf("File as %d lines%n", arrayUser.length);            //scaffold

/*        String filePath = "resources/shadow";
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);*/

        try {
            String line = "";           //scaffold
            String[] arraytest = new String[2];
            System.out.println(arraytest.length);
            Scanner s = new Scanner(new FileInputStream(shadowPath));
            int i = 0;              //scaffold
            int j = 0;              //scaffold
            while (s.hasNextLine()) {
                i++;                //scaffold


//                System.out.printf("%d. %s%n", i, s.nextLine());     //scaffold
                line = s.nextLine();            //scaffold
                arraytest = line.split(":", 2);
                System.out.printf("user: %s%npass: %s%n%n", arraytest[0], arraytest[1]);


/*                do {                                       //scaffold
                    System.out.println(arraytest[j]);       //scaffold
                    j++;
                } while (j < 1);                                //scaffold*/

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

//          imported class FileInputStream
//          imported class Scanner
//          a while loop that uses the above 2 imported classes to read the resources/shadow file line by line.
//          the split method and the delimiter : to split each line into a string array (i.e. String[])


//          use the first 2 elements of the split string array to create a new User(element1, element2)
//          and finally store each new User into a User array (i.e. User[] users), and return the array.
        return arrayUser;
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

    private static String[] splitLines(String text) {
        if (text == null || text.
                isEmpty()) {
            return new String[0]; // Return an empty array if the input is null or empty.
        }

        try {
            Scanner s = new Scanner(new FileInputStream(shadowPath));
            int i = 0;              //scaffold
            while (s.hasNextLine()) {
                i++;                //scaffold
                System.out.printf("%d. %s%n", i, s.nextLine());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }




        return text.split("\\n"); // Split the text into an array of lines using newline as the delimiter.
    }


    public static void readShadowFile() throws IOException {
        getPathAndDict();
//        parseShadow(shadowPath);
//        String[] fileLines = new String[getLineCount(shadowPath)];

        try (FileInputStream fileInputStream = new FileInputStream(shadowPath)) {
            parseShadow(shadowPath);
//            fileLines = splitLines(new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8));

        } catch (IOException e) {
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
/*        getFilePath();
        Crack c = new Crack(shadowPath, dictPath);
        c.crack();*/


        try {
            readShadowFile();
        } catch (IOException e) {
        }
    }
}
