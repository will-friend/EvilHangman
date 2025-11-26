import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EvilSolutionTest {

    private EvilSolution solution;
    private ArrayList<String> wordList;
    private PrintStream originalOutputStream;

    @BeforeEach
    void setUp() {
        this.wordList = new ArrayList<>(Arrays.asList("help", "boss", "seat", "boat", "cool", "melt", "bolt", "rope", "quit", "with", "cope", "mope", "joke"));
        this.solution = new EvilSolution(wordList);
        this.originalOutputStream = System.out;
    }

    @Test void testGetWordList() {
        assertEquals(this.wordList, this.solution.getWordList());
    }

    @Test
    public void testGetTarget() {
        assertNotNull(this.solution.getTarget());
    }

    @Test void testAddGuessExclude() {
        char guess = 'a';
        this.solution.addGuess(guess);
        ArrayList<Character> expectedPartial = new ArrayList<>(Arrays.asList('_', '_', '_', '_'));
        assertEquals(expectedPartial, this.solution.getPartialSolution());

    }

    @Test void testAddGuessInclude() {
        char guess = 'o';
        this.solution.addGuess(guess);
        ArrayList<Character> expectedPartial = new ArrayList<>(Arrays.asList('_', 'o', '_', '_'));
        assertEquals(expectedPartial, this.solution.getPartialSolution());

    }

    @Test void testGetTargetLength() {
        assertTrue(this.solution.getTargetLength() > 0);
    }

    @Test void testTargetLengthInUpdatedWordList() {
        ArrayList<String> retrievedWordList = this.solution.getWordList();
        for (String word : retrievedWordList) {
            assertEquals(this.solution.getTargetLength(), word.length());
        }
    }

    @Test void testIsSolvedFalse() {
        assertFalse(this.solution.isSolved());
    }

    @Test void testIsSolvedTrue() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        for (char c : alphabet.toCharArray()) {
            this.solution.addGuess(c);
        }
        assertTrue(solution.isSolved());
    }

    @Test void TestPrintProgressNoGuess() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testPrintStream = new PrintStream(outputStream);
        System.setOut(testPrintStream);
        this.solution.printProgress();
        System.setOut(originalOutputStream);
        String expectedPartial = "_ _ _ _ \n";
        String actualPartial = outputStream.toString();
        assertEquals(expectedPartial, actualPartial);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOutputStream);
    }



}