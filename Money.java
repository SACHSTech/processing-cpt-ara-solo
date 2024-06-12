import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Money extends PApplet {
    String[] SYMBOLS = { "Cherry", "Lemon", "Orange", "Bell", "Star", "Seven" };
    double[] PAYOUTS = { 2.0, 1.5, 1.25, 1.75, 3.0, 5.0 };
    int SYMBOLS_COUNT = SYMBOLS.length;

    double balance = 10000.0;
    double betAmount = 0.0;
    ArrayList<String> spinHistory = new ArrayList<>();

    PImage[] reelImages;
    PImage[] currentReels = new PImage[3];
    int spinDuration = 0;
    int spinCount = 0;
    int spinSpeed = 5;
    int maxSpinCount = 30;
    boolean spinning = false;
    Random random = new Random();

    PFont font;
    String resultText = "Welcome to the Slot Machine Game!";
    String balanceText = "Current balance: $" + balance;

    public void settings() {
        size(600, 400);
    }

    public void setup() {
        font = createFont("Arial", 16, true);
        textFont(font);
        reelImages = new PImage[SYMBOLS_COUNT];
        for (int i = 0; i < SYMBOLS_COUNT; i++) {
            reelImages[i] = loadImage("images/" + SYMBOLS[i] + ".png");
        }

        for (int i = 0; i < 3; i++) {
            currentReels[i] = reelImages[random.nextInt(SYMBOLS_COUNT)];
        }
    }

    public void draw() {
        background(255);
        fill(0);

        // Display balance and result
        text(balanceText, 20, 20);
        text(resultText, 20, 50);

        // Display bet amount input
        text("Enter bet amount: ", 20, 80);

        // Display reel images
        for (int i = 0; i < 3; i++) {
            image(currentReels[i], 150 + i * 120, 150, 100, 100);
        }

        // Spin button
        fill(200);
        rect(20, 350, 100, 30);
        fill(0);
        text("Spin", 45, 370);

        // Handle spinning
        if (spinning) {
            spinReels();
        }
    }

    public void mousePressed() {
        if (mouseX > 20 && mouseX < 120 && mouseY > 350 && mouseY < 380) {
            String betInput = prompt("Enter your bet amount: ");
            if (betInput != null) {
                try {
                    betAmount = Double.parseDouble(betInput);
                    if (betAmount <= 0 || betAmount > balance) {
                        resultText = "Invalid bet amount. Please try again.";
                        return;
                    }
                    startSpinning();
                } catch (NumberFormatException e) {
                    resultText = "Invalid bet amount. Please enter a valid number.";
                }
            }
        }
    }

    void startSpinning() {
        spinDuration = 0;
        spinCount = 0;
        spinning = true;
    }

    void spinReels() {
        String[] result = new String[3];

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(SYMBOLS_COUNT);
            result[i] = SYMBOLS[index];
            currentReels[i] = reelImages[index];
        }

        spinDuration++;
        if (spinDuration >= maxSpinCount) {
            spinning = false;
            double payout = calculatePayout(result);
            updateBalance(payout);
            logSpin(result, payout);

            resultText = "Spin result: " + result[0] + " | " + result[1] + " | " + result[2] + " - Payout: $" + payout;
            balanceText = "New balance: $" + balance;

            if (balance <= 0) {
                resultText = "Your balance is zero. Game over!";
            }
        }
    }

    double calculatePayout(String[] result) {
        if (result[0].equals(result[1]) && result[1].equals(result[2])) {
            for (int i = 0; i < SYMBOLS_COUNT; i++) {
                if (SYMBOLS[i].equals(result[0])) {
                    return betAmount * PAYOUTS[i];
                }
            }
        }
        return 0.0;
    }

    void updateBalance(double payout) {
        balance = balance - betAmount + payout;
    }

    void logSpin(String[] result, double payout) {
        String spinResult = join(result, " | ");
        spinHistory.add("Spin: " + spinResult + " - Payout: $" + payout);
    }

    String prompt(String message) {
        return javax.swing.JOptionPane.showInputDialog(frame, message);
    }
}
