import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

//Contains useful methods for the training phase
public class Training {

    //Creates Hashmap of String words in the email to the number of occurrences for each word (can also be partial adapted for number of emails)
    public static HashMap<String, Integer> Vocabulary(Scanner scan, Boolean partial) {
        HashMap<String, Integer> vocab = new HashMap<>();
        HashMap<String, Boolean> dupCheck = new HashMap<>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] splitspace = line.toLowerCase(Locale.ROOT).split(" ");
            String[] splitchar = line.split("");
            if (partial) {
                if (line.equals("</BODY>")) {
                    return vocab;
                }
            } else {
                if (line.equals("</BODY>")) {
                    dupCheck = new HashMap<>();
                }
            }
            if (splitchar[0].equals("<") || line.isEmpty()) {
                continue;
            }

            for (String s : splitspace) {
                if (vocab.containsKey(s)) {
                    if (!dupCheck.containsKey(s)) {
                        dupCheck.put(s, true);
                        vocab.replace(s, vocab.get(s) + 1);
                    }
                    continue;
                }
                vocab.put(s, 1);
            }
        }
        return vocab;
    }

    //Creates integer of the number of emails for needed math
    public static int getEmailcount(Scanner scan) {
        int count = 0;
        while (scan.hasNext()) {
            String line = scan.nextLine();
            if (line.equals("</BODY>")) {
                count++;
            }
        }
        return count;
    }
}
