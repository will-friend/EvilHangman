import java.util.ArrayList;
import java.util.HashMap;
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

        HashMap<Integer, HashSet<String>> families = new HashMap<>();
        HashSet<String> excludedFamily = new HashSet<>(wordList);
        HashSet<String> familySet;
        ArrayList<Integer> emptyIdx = new ArrayList<>();
        int guessIdx = 0;

        int maxFamilySize = -1;

        for (int i = 0; i < targetLength; i++) {
            if (partialSolution.get(i) == '_') {
                emptyIdx.add(i);
            }
        }

        for (int idx: emptyIdx) {
            familySet = getFamily(idx, guess);
            families.put(idx, familySet);
            excludedFamily.removeAll(familySet);
        }

        families.put(targetLength, excludedFamily);

        for (int i: families.keySet()) {
            if (families.get(i).size() > maxFamilySize) {
                maxFamilySize = families.get(i).size();
                guessIdx = i;
            }
        }

        this.wordList = new ArrayList<>(families.get(guessIdx));

        return guessIdx;
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

        int index = updateWordList(guess);

        if (index != targetLength) {
            guessCorrect = true;
            partialSolution.set(index, guess);
            missingChars--;
        }

        return guessCorrect;
    }

    @Override
    public String getTarget() {
        return wordList.getFirst();
    }

}
