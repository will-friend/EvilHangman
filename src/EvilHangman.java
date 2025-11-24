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
        int randomIndex = new Random().nextInt(wordList.size());
        String target = wordList.get(randomIndex);
        int solutionLength = target.length();
        ArrayList<String> solutionList = new ArrayList<>();
        for (String word : wordList) {
            if (word.length() == solutionLength) {
                solutionList.add(word);
            }
        }

        solution = new EvilSolution(solutionList);

    }

}
