package test.game.projects;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class HangMan {

    private static final int MAX_ERRORS = 11;
    private String wordToGuess;
    private Scanner input;
    private char letter;
    private int attempts = MAX_ERRORS;
    private Set<Character> lettersToGuess;
    private Set<Character> lettersGuessed;
    private Set<Character> lettersWrong;
    private boolean gameIsRunning;


    public static void main(String[] args) {
        HangMan game = new HangMan();
        game.run();
    }

    private void run() {

        input = new Scanner(System.in);
        wordToGuess = getRandomWordToGuess();
        gameIsRunning = true;
        lettersToGuess = new HashSet<Character>();
        lettersGuessed = new HashSet<Character>();
        lettersWrong = new HashSet<Character>();

        fillLettersToGuess();
        // Put first and last letter into lettersGuessed set.
        lettersGuessed.add(wordToGuess.charAt(0));
        lettersGuessed.add(wordToGuess.charAt(wordToGuess.length() - 1));

        System.out.println("I've chosen a word for you to guess.");
        while (gameIsRunning) {
            System.out.println("Word to guess:");
            showWordToGuess(wordToGuess, lettersGuessed);
            System.out.println("guess a letter!");
            showWrongLetters();
            try {
                pickLetter();
            } catch (TriesOutOfBoundException e) {
                e.printStackTrace();
            }
            if (lettersToGuess.contains(letter)) {
                lettersGuessed.add(letter);
            } else {
                lettersWrong.add(letter);
                lifeLost();
                System.out.println("Your letter is not in the word.");
                System.out.println("Lives remaining: " + attempts);
            }
            showWordToGuess(wordToGuess, lettersGuessed);
            if (isGameOver()) {
                gameIsRunning = false;
                System.out.println("You " + ((attempts == 0) ? "lost!" : "WIN!"));
            }
        }
        input.close();
    }

        /*
         * Fills lettersToGuess from wordToGuess
         */
        private void fillLettersToGuess() {
            for (Character c : wordToGuess.toCharArray()) {
                lettersToGuess.add(c);
            }
        }


    private boolean isGameOver() {

        return ((attempts == 0) || (lettersGuessed.size() == lettersToGuess.size()));

    }

    private void lifeLost() {

        attempts--;

        if (attempts == 5){

            System.out.println("You're getting out of tries!");

        }

    }



    private void showWrongLetters() {

        if (lettersWrong.size() > 0) {
            System.out.print("Wrong letters: ");
            for (Character c : lettersWrong) {
                System.out.print(String.valueOf(c).toUpperCase() + ", ");
            }
            System.out.println();
        }


    }


    // Load a word from dictionary. To do: Implement a dictionary
    private String getRandomWordToGuess() {

        String[] wordList = { "hello", "test", "elephant", "car", "table",
                "stack", "help", "someone", "yellow", "purple" };
        int min = 0;
        int max = wordList.length - 1;
        int wordToGuessPosition = getRandomNumber(min, max);
        return wordList[wordToGuessPosition].toUpperCase();
    }

    /*public void Solution(String wordToFind) {



    }*/

    /*
     * Shows the word to guess.
     */
    private void showWordToGuess(String wordToGuess,
                                 Set<Character> lettersGuessed) {

        for (int i = 0; i < wordToGuess.length(); i++) {

            // Checks lettersGuessed to know what to reveal to the user
            if (lettersGuessed.contains(wordToGuess.charAt(i))) {
                System.out.print(wordToGuess.toUpperCase().charAt(i));
            } else {
                System.out.print("_");
            }
        }
        // Hardcoded newline. Is there any fancier way to do this?
        System.out.println();
    }

    /*
     * Returns a random number. To do: Move it to a helper class.
     */
    private int getRandomNumber(int lbIncl, int ubExcl) {
        return lbIncl + (int)(Math.random() * (ubExcl - lbIncl));
    }



    /*
     * Ask the user to type a letter and verify if it isn't in the game already
     */
    private void pickLetter() throws TriesOutOfBoundException{

        int sameLettersCounter = 0;

        do {
            letter = input.nextLine().trim().toLowerCase().charAt(0);
            if (wordToGuess.contains(String.valueOf(letter))){

                sameLettersCounter++;

            }
            if (lettersWrong.contains(letter)
                    || lettersGuessed.contains(letter)) {
                System.out.println("The letter " + String.valueOf(letter).toUpperCase()
                        + " is already in the game!");
                System.out.println("guess a new letter!");
            }
        } while (lettersWrong.contains(letter)
                || lettersGuessed.contains(letter));
    }

}
