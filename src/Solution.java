import java.util.ArrayList;

public class Solution {

	protected String target;
	protected ArrayList<Character> partialSolution;
	protected int missingChars;

	// Not used in EvilSolution
	public Solution(String target) {
		this.target = target;
		missingChars = target.length();
		partialSolution = new ArrayList<>(missingChars);
		for (int i = 0; i < target.length(); i++) {
			partialSolution.add('_');
		}
	}

	// Used in EvilSolution
	public boolean isSolved() {
		return missingChars == 0;
	}

	// Used in EvilSolution
	public void printProgress() {
		for (char c : partialSolution) {
			System.out.print(c + " ");
		}
		System.out.println();
	}

	// Overridden in EvilSolution
	public boolean addGuess(char guess) {
		boolean guessCorrect = false;
		for (int i = 0; i < target.length(); i++) {
			if (target.charAt(i) == guess) {
				partialSolution.set(i, guess);
				missingChars--;
				guessCorrect = true;
			}
		}
		return guessCorrect;
	}

	// overridden in EvilSolution
	public String getTarget() {
		return target;
	}
}
