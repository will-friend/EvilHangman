import java.util.ArrayList;
import java.util.Random;

public class EvilHangman extends Hangman {

    private int solutionLength;
    private ArrayList<String> solutionList;

    public EvilHangman() {
        super("engDictionary.txt");
        int maxLength = 0;
        for (String word : wordList) {
            maxLength = Math.max(maxLength, word.length());
        }
        solutionLength = new Random().nextInt(maxLength-1) + 1;
        solutionList = new ArrayList<>();
        for (String word : wordList) {
            if (word.length() == solutionLength) {
                solutionList.add(word);
            }
        }

        solution = new EvilSolution(solutionList);

    }

}
