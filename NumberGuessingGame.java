import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    
    public static void main(String[] args) {
        System.out.println("Welcome to the Number Guessing Game!");
        boolean playAgain = true;

        while (playAgain) {
            System.out.println("\nStarting a new game!");
            playGame();
            System.out.print("\nDo you want to play again? (Y/N): ");
            char choice = scanner.next().charAt(0);
            playAgain = (choice == 'Y' || choice == 'y');
        }
        System.out.println("Thanks for playing!");
    }

    // Main game logic
    public static void playGame() {
        int numberToGuess = random.nextInt(100) + 1;  // Generates a number between 1 and 100
        int maxAttempts = 10;  // Limit attempts to 10
        int attempts = 0;
        int score = 100;  // Start with a score of 100

        System.out.println("I have chosen a number between 1 and 100. Can you guess it?");
        System.out.println("You have " + maxAttempts + " attempts to guess the correct number.");

        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int userGuess = scanner.nextInt();
            attempts++;
            
            if (userGuess == numberToGuess) {
                System.out.println("Congratulations! You've guessed the correct number in " + attempts + " attempts.");
                score -= (attempts - 1) * 10;  // Decrease score based on attempts taken
                System.out.println("Your score is: " + score);
                break;
            } else if (userGuess > numberToGuess) {
                System.out.println("Your guess is too high.");
            } else {
                System.out.println("Your guess is too low.");
            }

            if (attempts == maxAttempts) {
                System.out.println("Sorry! You've used all " + maxAttempts + " attempts.");
                System.out.println("The correct number was: " + numberToGuess);
                score = 0;  // Set score to 0 if they fail to guess
                System.out.println("Your score is: " + score);
            }
        }
    }
}
