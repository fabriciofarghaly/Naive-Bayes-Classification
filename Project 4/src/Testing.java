import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Contains useful methods for testing phase
public class Testing {
    //Multiplies all probabilities taking into account smoothing
    public static int ProbabilityCalculation(HashMap<String, Integer> spamTrainingVocab, HashMap<String,
            Integer> hamTrainingVocab, ArrayList<HashMap<String, Integer>> testEmails, Set<String> vocabulary, int sEmailNum, int hEmailNum, boolean spam) {
        int correctTests = 0;
        int i = 0;
        for (HashMap<String, Integer> e : testEmails)
        {
            //System.out.println(e.keySet());
            double probEmailSpam = Math.log((double) sEmailNum/ (sEmailNum + hEmailNum));
            double probEmailHam = Math.log((double) hEmailNum/ (sEmailNum + hEmailNum));
            double probSpam;
            double probHam;
            int features = 0;
            i++;
            for (String w : vocabulary) {
                if (e.keySet().contains(w)) {
                    probSpam = Math.log((spamTrainingVocab.get(w) + 1.0)/ (sEmailNum + 2.0)) ;
                    probHam = Math.log((hamTrainingVocab.get(w) + 1.0)/ (hEmailNum + 2.0)) ;
                    features++;
                } else {
                    probSpam = Math.log((sEmailNum - spamTrainingVocab.get(w) + 1.0)/ (sEmailNum + 2.0)) ;
                    probHam = Math.log((hEmailNum - hamTrainingVocab.get(w) + 1.0)/ (hEmailNum + 2.0) );
                }
//                System.out.println(probSpam);
//                System.out.println(probHam);
                probEmailSpam += probSpam;
                probEmailHam += probHam;
            }

            System.out.printf("Test %s %d/%d features true %.3f %.3f ", i, features, vocabulary.size(), probEmailSpam, probEmailHam);

            if (spam) {
                if (probEmailSpam > probEmailHam) {
                    System.out.println("spam right");
                    correctTests++;
                } else {
                    System.out.println("ham wrong");
                }
            } else {
                if (probEmailSpam > probEmailHam) {
                    System.out.println("spam wrong");
                } else {
                    System.out.println("ham right");
                    correctTests++;
                }
            }
        }

        return correctTests;
    }
}


