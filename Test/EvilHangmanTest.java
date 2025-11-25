import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class EvilHangmanTest {
    private InputStream originalReader;
    private PrintStream originalWriter;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        this.originalReader = System.in;
        this.originalWriter = System.out;
        this.outputStream = new ByteArrayOutputStream();
    }

    @Test
    void testHangmanStartNoErrorsInInput() {
        String input = "r\ny\nc\n";
        String output;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));
        EvilHangman evilHangman = new EvilHangman("short_engDictionary.txt");
        evilHangman.start();
        output = outputStream.toString();
        String outputExpected =
                "Guess a letter.\n" +
                "\n" +
                "_ _ _ \n" +
                "Incorrect guesses:\n" +
                "[]\n" +
                "Guess a letter.\n" +
                "\n" +
                "_ r _ \n" +
                "Incorrect guesses:\n" +
                "[]\n" +
                "Guess a letter.\n" +
                "\n" +
                "_ r y \n" +
                "Incorrect guesses:\n" +
                "[]\n" +
                "Congrats! The word was cry\n";
        assertEquals(outputExpected, output);
    }

    @Test
    void testHangmanStartDoubleLetterInInput() {
        String input = "r\nyy\ny\nc\n";
        String output;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));
        EvilHangman evilHangman = new EvilHangman("short_engDictionary.txt");
        evilHangman.start();
        output = outputStream.toString();
        String outputExpected =
                "Guess a letter.\n" +
                        "\n" +
                        "_ _ _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Please enter a single character.\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r y \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Congrats! The word was cry\n";
        assertEquals(outputExpected, output);
    }

    @Test
    void testHangmanStartRepeatedGuessInInput() {
        String input = "r\nr\ny\nc\n";
        String output;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));
        EvilHangman evilHangman = new EvilHangman("short_engDictionary.txt");
        evilHangman.start();
        output = outputStream.toString();
        String outputExpected =
                "Guess a letter.\n" +
                        "\n" +
                        "_ _ _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "You've already guessed that.\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r y \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Congrats! The word was cry\n";
        assertEquals(outputExpected, output);
    }

    @Test
    void testHangmanStartNonAlphabeticGuessInInput() {
        String input = "r\n!\n2\ny\nc\n";
        String output;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        System.setOut(new PrintStream(outputStream));
        EvilHangman evilHangman = new EvilHangman("short_engDictionary.txt");
        evilHangman.start();
        output = outputStream.toString();
        String outputExpected =
                "Guess a letter.\n" +
                        "\n" +
                        "_ _ _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Please enter an alphabetic character.\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Please enter an alphabetic character.\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r _ \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Guess a letter.\n" +
                        "\n" +
                        "_ r y \n" +
                        "Incorrect guesses:\n" +
                        "[]\n" +
                        "Congrats! The word was cry\n";
        assertEquals(outputExpected, output);
    }

    @AfterEach
    public void tearDown() {
        System.setIn(this.originalReader);
        System.setOut(this.originalWriter);
    }
}