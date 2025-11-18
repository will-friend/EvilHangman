import java.util.ArrayList;
import java.util.Random;

public class EvilHangman extends Hangman {

    /**
     * Constructor for building derived class EvilHangman
     * from base class Hangman
     */
    public EvilHangman() {
        super("engDictionary.txt");
        int maxLength = 0;
        for (String word : wordList) {
            maxLength = Math.max(maxLength, word.length());
        }
        int solutionLength = new Random().nextInt(maxLength-1) + 1;
        ArrayList<String> solutionList = new ArrayList<>();
        for (String word : wordList) {
            if (word.length() == solutionLength) {
                solutionList.add(word);
            }
        }

        solution = new EvilSolution(solutionList);

    }

}
