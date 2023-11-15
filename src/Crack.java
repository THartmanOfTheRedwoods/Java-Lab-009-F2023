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

Scanner scanner = new Scanner(new File(this.dictionary));
while(scanner.hasNextLine()){
    String word = scanner.nextLine();
    for (User user : users){

        if(user.getPassHash().contains("$")){

            String hash = Crypt.crypt(word, user.getPassHash());
            if(hash.equals(user.getPassHash())){

                System.out.printf("Found password %s for user %s %n", word, user.getUsername());
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
        int i = 0;

        Scanner s = new Scanner(new File(shadowFile));

       int limit = getLineCount(shadowFile);
       User[] users = new User[limit];
        while (s.hasNextLine()){
            String pw = s.nextLine();
           String[] arr =  pw.split(":");
            String element1 = arr[0];
            String element2 = arr[1];
            User nEw = new User(element1, element2);

            users[i] = nEw;
            i++;
        } return users;

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
