import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //Makes a scanner for the files
        Scanner scan = new Scanner(System.in);


        //Training for spam
        System.out.println("Enter spam training file: ");
        String s1 = scan.nextLine();
        InputStream is1 = Main.class.getResourceAsStream(s1);
        InputStream is1v2 = Main.class.getResourceAsStream(s1);
        if (is1 == null) {
            System.err.println("Bad filename " + s1);
            System.exit(1);
        }
        Scanner spamtrain = new Scanner(is1);
        Scanner spamtrain2 = new Scanner(is1v2);


        //Training for ham
        System.out.println("Enter ham training file: ");
        String h1 = scan.nextLine();
        InputStream is2 = Main.class.getResourceAsStream(h1);
        InputStream is2v2 = Main.class.getResourceAsStream(h1);
        if (is2 == null) {
            System.err.println("Bad filename " + h1);
            System.exit(1);
        }
        Scanner hamtrain = new Scanner(is2);
        Scanner hamtrain2 = new Scanner(is2v2);


        //Testing for spam
        System.out.println("Enter spam testing file: ");
        String s2 = scan.nextLine();
        InputStream is3 = Main.class.getResourceAsStream(s2);
        InputStream is3v2 = Main.class.getResourceAsStream(s2);
        if (is3 == null) {
            System.err.println("Bad filename " + s2);
            System.exit(1);
        }
        Scanner spamtest = new Scanner(is3);
        Scanner spamtest2 = new Scanner(is3v2);


        //Testing for ham
        System.out.println("Enter ham testing file: ");
        String h2 = scan.nextLine();
        InputStream is4 = Main.class.getResourceAsStream(h2);
        InputStream is4v2 = Main.class.getResourceAsStream(h2);
        if (is4 == null) {
            System.err.println("Bad filename " + h2);
            System.exit(1);
        }
        Scanner hamtest = new Scanner(is4);
        Scanner hamtest2 = new Scanner(is4v2);


        //Debug (yes/no)
        System.out.print("Do you want debugging info? (y/n): ");
        String debug = scan.nextLine();


        //All calculations
        HashMap<String, Integer> spamtrainingvocab = Training.Vocabulary(spamtrain, false);   //creates hashmap of spam training words to usages in emails
        HashMap<String, Integer> hamtrainingvocab = Training.Vocabulary(hamtrain, false);     //creates hashmap of ham training words to usages in emails
        Set<String> vocabulary = new HashSet<>();                                                   //full vocabulary
        vocabulary.addAll(spamtrainingvocab.keySet());
        vocabulary.addAll(hamtrainingvocab.keySet());
        int numspamemails = Training.getEmailcount(spamtrain2);                                     //num of spam emails in training
        int numhamemails = Training.getEmailcount(hamtrain2);                                       //num of ham emails in training
        int numspamtestmails = Training.getEmailcount(spamtest2);                                  //num of spam test emails in training
        int numhamtestmails = Training.getEmailcount(hamtest2);                                    //num of ham test emails in training
        double probSpam = (double) numspamemails / (numspamemails + numhamemails);                  //prob of spam emails
        double probHam = (double) numhamemails / (numspamemails + numhamemails);                    //prob of ham emails
        ArrayList<HashMap<String, Integer>> STEV = new ArrayList<>();                               //creates arraylist of hashmaps for individual spam emails
        for (int i = 0; i < numspamtestmails; i++)
        {
            STEV.add(Training.Vocabulary(spamtest, true));
        }
        ArrayList<HashMap<String, Integer>> HTEV = new ArrayList<>();                               //creates arraylist of hashmaps for individual ham emails
        for (int i = 0; i < numhamtestmails; i++)
        {
            HTEV.add(Training.Vocabulary(hamtest, true));
        }
//        HashMap<String, Double> spamtestProbs = new HashMap<>();                                     //map of words to their probs given spam
//        HashMap<String, Double> hamtestProbs = new HashMap<>();                                      //map of words to their probs given ham
//
//        for (int i = 0; i < numhamtestmails; i++)
//        {
//            //spamtestProbs.put()
//        }

        for(String words: vocabulary)
        {
            if (!spamtrainingvocab.containsKey(words))
            {
                spamtrainingvocab.put(words, 0);
            }

            if (!hamtrainingvocab.containsKey(words))
            {
                hamtrainingvocab.put(words, 0);
            }
        }

        //All printing
        if (debug.equals("y")) {
            System.out.println("Training from " + s1 + " and " + h1);
            System.out.println("Testing from " + s2 + " and " + h2);
            System.out.println("number of emails " + numspamemails + " vs " + numhamemails);
            ArrayList<String> vocab = new ArrayList<>();
            for (Map.Entry<String, Integer> set : spamtrainingvocab.entrySet()) {
                vocab.add(set.getKey());
            }
            for (Map.Entry<String, Integer> set : hamtrainingvocab.entrySet()) {
                if (!vocab.contains(set.getKey())) {
                    vocab.add(set.getKey());
                }
            }
            System.out.println("entire vocab " + vocab);
            System.out.println("entire vocab size is " + vocab.size());
            System.out.println("spam words " + spamtrainingvocab);
            System.out.println("ham words " + hamtrainingvocab);
            System.out.println("priors: " + probSpam + " " + probHam);
        }


        int correctTests = Testing.ProbabilityCalculation(spamtrainingvocab, hamtrainingvocab, STEV, vocabulary, numspamemails, numhamemails, true);
        correctTests += Testing.ProbabilityCalculation(spamtrainingvocab, hamtrainingvocab, HTEV, vocabulary, numspamemails, numhamemails, false);
        int totalTestEmails = numhamtestmails + numspamtestmails;
        System.out.println("Total: " + correctTests + "/" + totalTestEmails + " emails classified correctly.");
    }















}