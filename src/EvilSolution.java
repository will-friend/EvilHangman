import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class EvilSolution extends Solution {

    private ArrayList<String> wordList;
    private final int targetLength;

    /**
     * Constructor for derived class EvilSolution from base class Solution.
     * Modifies Solution fields as well as introduces wordList and
     * targetLength fields for evil portion of gameplay
     * @param wordList ArrayList of words from input dictionary that are all
     *                 length targetLength
     */
    public EvilSolution(ArrayList<String> wordList) {
        super();
        this.wordList = wordList;
        this.missingChars = this.wordList.getFirst().length();
        this.targetLength = this.missingChars;
        this.partialSolution = new ArrayList<>(missingChars);
        for (int i = 0; i < this.missingChars; i++) {
            partialSolution.add('_') ;
        }
    }

    /**
     * Method to return the character array of the partial solution state
     * of the evil hangman game
     * @return ArrayList of Characters representing the current partial
     * solution state of the current game
     */
    public ArrayList<Character> getPartialSolution() {
        return this.partialSolution;
    }

    /**
     * Method to return the target word length
     * @return integer representing number of char in solution word
     */
    public int getTargetLength() {
        return this.targetLength;
    }

    /**
     * Method to return the wordList field (for testing)
     * @return ArrayList of Strings for the words in the provided
     * dictionary
     */
    public ArrayList<String> getWordList() {
        return wordList;
    }

    /**
     * Method to update wordList field of EvilSolution class, serving as
     * current list of possible solution words for the evil game
     *
     * @param guess char provided by user input as guess of letter in solution
     *              word
     * @return HashSet of indices where guess will be placed to maximize the
     * number of possible solutions left (entry of targetLength means guess was
     * excluded
     */
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

        families.put(new HashSet<>(Arrays.asList(targetLength)), excludedFamily);

        for (HashSet<Integer> i: families.keySet()) {
            if (families.get(i).size() > maxFamilySize) {
                maxFamilySize = families.get(i).size();
                guessIdx = i;
            }
        }

        this.wordList = new ArrayList<>(families.get(guessIdx));

        return guessIdx;
    }

    /**
     * Method to get the maximum number of times a user inputted guess char
     * would appear in a word from available solutions in field wordList
     *
     * @param guess char provided by user input as guess of letter in solution
     *              word
     * @return int value of the maximum frequency of the character possible in
     * the available solutions in wordList
     */
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

    /**
     * Recursive method (backtracking) to generate all possible index positions/
     * index combinations for the user provided guess to be placed, based on
     * number of empty spaces available and the max frequency found for the char
     *
     * @param i int index of current position in indices being considered
     * @param cur ArrayList of current subset of index positions being considered
     * @param indices ArrayList of possible index values the guess could be placed
     *                in word
     * @param res HashSet of index combinations to find families for
     * @param frequency Maximum number of times a character can exist in available
     *                  wordList words, setting upper limit to size of entry in res
     */
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

    /**
     * Method for invoking recursive search for all possible index combinations for
     * word family finding given guess character and current game state
     *
     * @param indices ArrayList of indices that have not been filled with a correct
     *                guess yet
     * @param frequency int representing the maximum number of times the guess char
     *                  could appear in a word in available solution set
     * @return HashSet of HashSets containing all possible integer combinations within
     * allowed guesses given max frequency and number of empty spaces remaining in game
     */
    private HashSet<HashSet<Integer>> getIndexSubsets(ArrayList<Integer> indices, int frequency) {
        HashSet<HashSet<Integer>> subsets = new HashSet<>();
        dfs(0, new ArrayList<>(), indices, subsets, frequency);
        return subsets;
    }

    /**
     * Method for building up word families from current solution given a guess char
     * set and a HashSet of indices to evaluate as guess placement locations
     *
     * @param index HashSet of integers representing the indices in the target word
     *              to look for the guess char
     * @param guess char representing user inputted guess for character in target
     *              word
     * @return HashSet of Strings (words) that contain words from wordList field that
     * contain the char guess at indices specified in the index input param
     */
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

    /**
     * Overridden method from Solution class to correctly implement changing
     * solution list as game play progresses
     *
     * @param guess char provided by user input representing guess char in target
     *              word
     * @return boolean indicating if the evil game determined the guess would be
     * correct or not
     */
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

    /**
     * Overridden method from Solution class to provide solution when no
     * more words are left in solution set
     *
     * @return String representing the last word in the solution set after
     * evil gameplay
     */
    @Override
    public String getTarget() {
        return wordList.getFirst();
    }

}
