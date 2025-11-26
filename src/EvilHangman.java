import java.util.ArrayList;
import java.util.Random;

public class EvilHangman extends Hangman {

    /**
     * No argument constructor for running game
     */
    public EvilHangman() {
        this("engDictionary.txt");
    }

    /**
     * Constructor for building derived class EvilHangman
     * from base class Hangman
     */
    public EvilHangman(String dictionaryPath) {
        super(dictionaryPath);
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

        solution = new EvilSolution(wordList);

    }

}
