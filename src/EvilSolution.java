import java.util.ArrayList;
import java.util.HashSet;

public class EvilSolution extends Solution {

    private ArrayList<String> wordList;
    private int targetLength;

    public EvilSolution(ArrayList<String> wordList) {
        super();
        this.wordList = wordList;
        this.missingChars = this.wordList.getFirst().length();
        this.targetLength = this.missingChars;
        partialSolution = new ArrayList<>(missingChars);
        for (int i = 0; i < this.missingChars; i++) {
            partialSolution.add('_') ;
        }
    }

    private int updateWordList(char guess) {
        ArrayList<HashSet<String>> families = new ArrayList<HashSet<String>>();
        HashSet<String> excludedFamily = new HashSet<>();
        ArrayList<Integer> emptyIdx = new ArrayList<>();
        for (int i = 0; i < targetLength; i++) {
            if (partialSolution.get(i) == '_') {
                emptyIdx.add(i);
            }
        }

        for (int idx: emptyIdx) {
            families.add(getFamily(idx, guess)) ;
        }



        return -1 ;
    }

    private HashSet<String> getFamily(int index, char guess) {
        HashSet<String> family = new HashSet<>();
        for (String word: wordList) {
            if (word.charAt(index) == guess) {
                family.add(word);
            }
        }
        return family;
    }

    @Override
    public boolean addGuess(char guess) {
        boolean guessCorrect = false;
        return guessCorrect;
    }

}
