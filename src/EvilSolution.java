import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class EvilSolution extends Solution {

    private ArrayList<String> wordList;
    private final int targetLength;

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

    private HashSet<Integer> updateWordList(char guess) {

        HashMap<HashSet<Integer>, HashSet<String>> families = new HashMap<>();
        HashSet<String> excludedFamily = new HashSet<>(wordList);
        HashSet<String> familySet;
        ArrayList<Integer> emptyIdx = new ArrayList<>();
        HashSet<Integer> guessIdx = new HashSet<>();
        int frequency = getFrequency(guess);

        int maxFamilySize = -1;

        for (int i = 0; i < targetLength; i++) {
            if (partialSolution.get(i) == '_') {
                emptyIdx.add(i);
            }
        }

        HashSet<HashSet<Integer>> emptySubSets = getIndexSubsets(emptyIdx, frequency);

        for (HashSet<Integer> idx: emptySubSets) {
            familySet = getFamily(idx, guess);
            families.put(idx, familySet);
            excludedFamily.removeAll(familySet);
        }

        families.put(new HashSet<>(targetLength), excludedFamily);

        for (HashSet<Integer> i: families.keySet()) {
            if (families.get(i).size() > maxFamilySize) {
                maxFamilySize = families.get(i).size();
                guessIdx = i;
            }
        }

        this.wordList = new ArrayList<>(families.get(guessIdx));

        return guessIdx;
    }

    private int getFrequency(char guess) {
        int frequency;
        int maxFreq = -1;
        for (String word: wordList) {
            frequency = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    frequency++;
                }
            }
            maxFreq = Math.max(frequency, maxFreq);
        }
        return maxFreq;
    }

    private void dfs(int i, ArrayList<Integer> cur, ArrayList<Integer> indices, HashSet<HashSet<Integer>> res, int frequency) {
        if (cur.size() == frequency || i == indices.size()) {
            if (!cur.isEmpty()) {
                res.add(new HashSet<>(cur));
            }
            return;
        }
        cur.add(indices.get(i));
        dfs(i + 1, cur, indices, res, frequency);
        cur.removeLast();
        dfs(i + 1, cur, indices, res, frequency);

    }

    private HashSet<HashSet<Integer>> getIndexSubsets(ArrayList<Integer> indices, int frequency) {
        HashSet<HashSet<Integer>> subsets = new HashSet<>();
        dfs(0, new ArrayList<>(), indices, subsets, frequency);
        return subsets;
    }

    private HashSet<String> getFamily(HashSet<Integer> index, char guess) {
        HashSet<String> family = new HashSet<>();
        int count;
        for (String word: wordList) {
            count = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    if (index.contains(i)) {
                        count++;
                    } else {
                        count--;
                    }
                }
            }
            if (count == index.size()) {
                family.add(word);
            }
        }
        return family;
    }

    @Override
    public boolean addGuess(char guess) {
        boolean guessCorrect = false;

        ArrayList<Integer> index = new ArrayList<>(updateWordList(guess));

        if (index.getFirst() != targetLength) {
            guessCorrect = true;
            for (int idx : index) {
                partialSolution.set(idx, guess);
                missingChars--;
            }
        }

        return guessCorrect;
    }

    @Override
    public String getTarget() {
        return wordList.getFirst();
    }

}
