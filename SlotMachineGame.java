import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * SlotMachineGame
 * A detailed slot machine game where the player can bet and spin to win.
 * Demonstrates the use of various programming concepts effectively.
 */
public class SlotMachineGame {

    // Constants for symbols and payouts
    private static final String[] SYMBOLS = {"Cherry", "Lemon", "Orange", "Bell", "Star", "Seven"};
    private static final double[] PAYOUTS = {2.0, 1.5, 1.25, 1.75, 3.0, 5.0};
    private static final int SYMBOLS_COUNT = SYMBOLS.length;

    // Game state variables
    private double balance;
    private double betAmount;
    private boolean continuePlaying;
    private ArrayList<String> spinHistory;

    /**
     * Constructor to initialize the slot machine game.
     */
    public SlotMachineGame() {
        this.balance = 100.0; // Initial balance
        this.betAmount = 0.0;
        this.continuePlaying = true;
        this.spinHistory = new ArrayList<>();
    }

    /**
     * Main method to start the game.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SlotMachineGame game = new SlotMachineGame();
        game.play();
    }

    /**
     * Main game loop.
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (continuePlaying) {
            displayBalance();
            betAmount = getBetAmount(scanner);

            if (betAmount <= 0 || betAmount > balance) {
                System.out.println("Invalid bet amount. Please try again.");
                continue;
            }

            String[] result = spinReels();
            displaySpinResult(result);

            double payout = calculatePayout(result);
            updateBalance(payout);
            logSpin(result, payout);

            displayPayout(payout);
            displayNewBalance();

            if (balance <= 0) {
                System.out.println("Your balance is zero. Game over!");
                break;
            }

            continuePlaying = promptContinuePlaying(scanner);
        }

        System.out.println("Thank you for playing! Final balance: $" + balance);
        scanner.close();
    }

    /**
     * Displays the current balance.
     */
    private void displayBalance() {
        System.out.println("Current balance: $" + balance);
    }

    /**
     * Gets the bet amount from the player.
     *
     * @param scanner Scanner object for user input
     * @return Bet amount entered by the player
     */
    private double getBetAmount(Scanner scanner) {
        System.out.print("Enter bet amount: $");
        return scanner.nextDouble();
    }

    /**
     * Simulates spinning the slot machine reels.
     *
     * @return An array of symbols representing the spin result
     */
    private String[] spinReels() {
        Random random = new Random();
        String[] result = new String[3];

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(SYMBOLS_COUNT);
            result[i] = SYMBOLS[index];
        }

        return result;
    }

    /**
     * Displays the result of the spin.
     *
     * @param result Array of symbols representing the spin result
     */
    private void displaySpinResult(String[] result) {
        System.out.println("Spin result: " + String.join(" | ", result));
    }

    /**
     * Calculates the payout based on the spin result.
     *
     * @param result Array of symbols representing the spin result
     * @return Payout amount
     */
    private double calculatePayout(String[] result) {
        if (result[0].equals(result[1]) && result[1].equals(result[2])) {
            for (int i = 0; i < SYMBOLS_COUNT; i++) {
                if (SYMBOLS[i].equals(result[0])) {
                    return betAmount * PAYOUTS[i];
                }
            }
        }
        return 0.0;
    }

    /**
     * Updates the player's balance based on the payout amount.
     *
     * @param payout Payout amount
     */
    private void updateBalance(double payout) {
        balance = balance - betAmount + payout;
    }

    /**
     * Logs the spin result and payout to the spin history.
     *
     * @param result Array of symbols representing the spin result
     * @param payout Payout amount
     */
    private void logSpin(String[] result, double payout) {
        String spinResult = String.join(" | ", result);
        spinHistory.add("Spin: " + spinResult + " - Payout: $" + payout);
    }

    /**
     * Displays the payout amount.
     *
     * @param payout Payout amount
     */
    private void displayPayout(double payout) {
        System.out.println("Payout: $" + payout);
    }

    /**
     * Displays the new balance after the spin.
     */
    private void displayNewBalance() {
        System.out.println("New balance: $" + balance);
    }

    /**
     * Prompts the player if they want to continue playing.
     *
     * @param scanner Scanner object for user input
     * @return True if the player wants to continue, false otherwise
     */
    private boolean promptContinuePlaying(Scanner scanner) {
        System.out.print("Do you want to play again? (yes/no): ");
        return scanner.next().equalsIgnoreCase("yes");
    }

    /**
     * Prints the spin history log.
     */
    private void printSpinHistory() {
        System.out.println("\nSpin History:");
        for (String log : spinHistory) {
            System.out.println(log);
        }
    }
}
