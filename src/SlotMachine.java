/*
 * Name: Ben Goldstone
 * Date: 3/2/2021
 * Professor: Dr. Joseph Helsing
 * Description: A program that emulates a slot machine.
 */

//import used libraries

import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Main SlotMachine class.
 */
public class SlotMachine {
    /**
     * The main method.
     *
     * @param args any arguments
     */
    public static void main(String[] args) {
        //prints welcome message
        welcome();

        //initializes local variables/objects
        boolean flag = true;
        Random rand = new Random();
        double playerBalance = 100;
        double machineBalance = 0;
        double betAmount = 1;
        double winnings = 0;
        String[] options = {"Please select an option....", "Add Money", "Change Bet Amount", "Play", "Cash Out and Leave"};

        //prints current player's stats
        printStats(playerBalance, machineBalance);

        //Menu Loop
        do {
            //tells user balances

            //prompts user for menu option
            String msg = "Please select an option:" +
                    "\n1. Add Money to the Machine\s" + String.format(
                    "%n2. Change bet amount (current bet is $%.2f and the default is $1.00)", betAmount) +
                    "\n3. Play the game" +
                    "\n4. Leave the Machine and pay out all of your winnings" +
                            "Select Option:\s";
            String input = (String) JOptionPane.showInputDialog(null, msg,
                    "Enter a menu choice",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            int choice = 0;

            //makes sure user selected an option
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Invalid Selection!!", "Invalid Selection!", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            //Assigns text response to numeric value
            for (int i = 0; i < options.length; i++) {
                if (input.equals(options[i])) {
                    choice = i;
                    break;
                }
            }
            //selects method relevant to selection
            switch (choice) {
                case 1 -> {
                    double[] changeMoney = addMoney(playerBalance, machineBalance);
                    playerBalance = changeMoney[0];
                    machineBalance = changeMoney[1];
                    printStats(playerBalance, machineBalance);
                }
                case 2 -> betAmount = changeBetAmount(playerBalance, machineBalance);
                case 3 -> {
                    double[] returnValues = play(playerBalance, machineBalance, betAmount, rand);
                    playerBalance = returnValues[0];
                    machineBalance = returnValues[1];
                    winnings += returnValues[2];
                    printStats(playerBalance, machineBalance);
                }
                case 4 -> {
                    cashOut(winnings, machineBalance);
                    flag = false;
                }
                //if no option selected
                default -> JOptionPane.showMessageDialog(null, "Please select one of the four actions.", "", JOptionPane.WARNING_MESSAGE);
            }
        } while (flag);
    }

    /**
     * Prints Welcome message.
     */
    public static void welcome() {
        final String welcome = "#".repeat(32) +
                "\n##    Welcome to the Casino!    ##" +
                "\n\n##  Default Starting bet is $1  ##" +
                "\n\n##   Player starts with $100    ##\n\n" +
                "#".repeat(32);
        JOptionPane.showMessageDialog(null, welcome, "", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Allows user to add money to the machine.
     *
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @return a double[] array consisting of {playerBalance, machineBalance}
     */
    public static double[] addMoney(double playerBalance, double machineBalance) {
        double balance;
        do {
            //prompts user for how much money to add
            String msg = String.format("%nHow Much money would you like to add? " +
                    "(you have a balance of $%.2f and there is $%.2f in the machine): $", playerBalance, machineBalance);
            String input = JOptionPane.showInputDialog(msg, "1.00");
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Invalid Input!",
                        "Invalid Input!", JOptionPane.ERROR_MESSAGE);
                continue;
            } else {
                balance = Double.parseDouble(input);
            }
            //checks if user has that much money to add
            if (balance > playerBalance || balance < 0) {
                JOptionPane.showMessageDialog(null, "Invalid amount, please change how much you are depositing. " +
                                "(This cannot be over how much money you have or negative)",
                        "Invalid Amount!", JOptionPane.ERROR_MESSAGE);
                printStats(playerBalance, machineBalance);
            } else {
                machineBalance += balance;
                playerBalance -= balance;
                break;
            }
        } while (true);
        return new double[]{playerBalance, machineBalance};
    }

    /**
     * Allows user to change bet amount.
     *
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @return double of new betAmount value;
     */
    public static double changeBetAmount(final double playerBalance, final double machineBalance) {
        //variable declaration
        double amount;
        double betAmount;
        //loops till bet is above $1.00 and in range of what player has
        do {
            //gets input from user
            String msg = "How much money would you like to add to the machine? (Must be $1.00 or above) $";
            String input = JOptionPane.showInputDialog(msg, "1.00");
            //checks if user has that much money to bet
            if (input == null) {
                JOptionPane.showMessageDialog(null,
                        "Invalid Input!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                continue;
            } else {
                amount = Double.parseDouble(input);
            }
            if (amount > machineBalance || amount < 1.00) {
                JOptionPane.showMessageDialog(null, "Invalid bet, please change how much you are betting. " +
                        "(This cannot be over how much money you have)", "Invalid Bet!", JOptionPane.ERROR_MESSAGE);
                printStats(playerBalance, machineBalance);
            }

            //makes sure bet is not below $1.00
            if (amount < 1) {
                JOptionPane.showMessageDialog(null,
                        "Sorry the default bet is 1.00, Please try again.", "", JOptionPane.WARNING_MESSAGE);
            } else {
                //Tells user the bet
                betAmount = amount;
                String betPlaced = String.format("Your bet has been placed at $ %.2f%n%n%n", betAmount);
                JOptionPane.showMessageDialog(null, betPlaced);
                break;
            }
        } while (true);
        return betAmount;
    }

    /**
     * Allows user to play the game.
     *
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @param betAmount      takes the bet amount for the user
     * @param rand           takes in a Random object
     * @return double[] array consisting of {playerBalance, machineBalance, winnings-betAmount}
     */
    public static double[] play(double playerBalance, double machineBalance, double betAmount, Random rand) {
        //keeps track of winnings for this round
        double winnings = 0;
        double totalOfBets = 0;
        //checks betAmount is valid
        if (machineBalance - betAmount < 0) {
            JOptionPane.showMessageDialog(null, "Not enough money in the Machine! Try adding more money to the machine",
                    "Not Enough Money!", JOptionPane.ERROR_MESSAGE);
            return new double[]{playerBalance, machineBalance, betAmount, winnings, totalOfBets};
        } else {
            JOptionPane.showMessageDialog(null, "The Game is about to start!", "", JOptionPane.WARNING_MESSAGE);
        }
        //takes bet
        machineBalance -= betAmount;

        //adds to total of bets
        totalOfBets += betAmount;

        //initializes variables
        /*
        More efficient to have in main method because it only assigns the array once.
         */
        final String[] WORDS = {"Computer", "Science", "Java", "Hello", "World", "Professor", "Helsing"};

        //initializes random object
        String[][] reels = new String[3][3];

        //Assigns reels
        for (int i = 0; i < reels.length; i++) {
            for (int j = 0; j < reels[i].length; j++) {
                reels[i][j] = WORDS[rand.nextInt(WORDS.length)];
            }
        }
        StringBuilder reelPrint = new StringBuilder();
        for (String[] reel : reels) {
            reelPrint.append(String.format("%15s%15s%15s%n", reel[0], reel[1], reel[2]));
        }
        //Prints Reels
        JOptionPane.showMessageDialog(null, reelPrint.toString());
        System.out.println(reelPrint.toString());

        //checks winning conditions
        //if three or two across
        //first row
        if (reels[0][0].equals(reels[0][1]) && reels[0][1].equals(reels[0][2])) {
            winnings += betAmount * 3;
            declareWinnings("top row", "across");
        } else if (reels[0][0].equals(reels[0][1]) || reels[0][1].equals(reels[0][2])) {
            winnings += betAmount * 2;
            declareWinnings("top row", "across");
        }

        //second row
        if (reels[1][0].equals(reels[1][1]) && reels[1][1].equals(reels[1][2])) {
            winnings += betAmount * 3;
            declareWinnings("middle row", "across");
        } else if (reels[1][0].equals(reels[1][1]) || reels[1][1].equals(reels[1][2])) {
            winnings += betAmount * 2;
            declareWinnings("middle row", "across");
        }

        //third row
        if (reels[2][0].equals(reels[2][1]) && reels[2][1].equals(reels[2][2])) {
            winnings += betAmount * 3;
            declareWinnings("bottom row", "across");
        } else if (reels[2][0].equals(reels[2][1]) || reels[2][1].equals(reels[2][2])) {
            winnings += betAmount * 2;
            declareWinnings("bottom row", "across");
        }

        //if three or two down
        //first column
        if (reels[0][0].equals(reels[1][0]) && reels[1][0].equals(reels[2][0])) {
            winnings += betAmount * 3;
            declareWinnings("left column", "down");
        } else if (reels[0][0].equals(reels[1][0]) || reels[1][0].equals(reels[2][0])) {
            winnings += betAmount * 2;
            declareWinnings("left column", "down");
        }

        //second column
        if (reels[0][1].equals(reels[1][1]) && reels[1][1].equals(reels[2][1])) {
            winnings += betAmount * 3;
            declareWinnings("middle column", "down");
        } else if (reels[0][1].equals(reels[1][1]) || reels[1][1].equals(reels[2][1])) {
            winnings += betAmount * 2;
            declareWinnings("middle column", "down");
        }

        //third column
        if (reels[0][2].equals(reels[1][2]) && reels[1][2].equals(reels[2][2])) {
            winnings += betAmount * 3;
            declareWinnings("right column", "down");
        } else if (reels[0][2].equals(reels[1][2]) || reels[1][2].equals(reels[2][2])) {
            winnings += betAmount * 2;
            declareWinnings("right column", "down");
        }

        //if two or three diagonal
        //top left to bottom right
        if (reels[0][0].equals(reels[1][1]) && reels[1][1].equals(reels[2][2])) {
            winnings += betAmount * 3;
            declareWinnings("top left to bottom right", "diagonal");
        } else if (reels[0][0].equals(reels[1][1]) || reels[1][1].equals(reels[2][2])) {
            winnings += betAmount * 2;
            declareWinnings("top left to bottom right", "diagonal");
        }

        //bottom left to top right
        if (reels[0][2].equals(reels[1][1]) && reels[1][1].equals(reels[2][0])) {
            winnings += betAmount * 3;
            declareWinnings("bottom left to top right", "diagonal");
        } else if (reels[0][2].equals(reels[1][1]) || reels[1][1].equals(reels[2][0])) {
            winnings += betAmount * 2;
            declareWinnings("bottom left to top right", "diagonal");
        }

        //adds winnings to total balance
        machineBalance += winnings;
        //tells user winnings
        if (winnings - betAmount >= 0) {
            JOptionPane.showMessageDialog(null,
                    String.format("%nYour total winnings are $%.2f%n", (winnings - betAmount)));
        } else {
            JOptionPane.showMessageDialog(null,
                    String.format("%nYour total winnings are -$%.2f%n", (Math.abs(winnings - betAmount))));
        }
        return new double[]{playerBalance, machineBalance, winnings - betAmount, totalOfBets};
    }

    /**
     * Displays ending message and Cash Out.
     *
     * @param winnings       takes the amount of winnings for the player
     * @param machineBalance takes the machine's current balance
     */
    public static void cashOut(final double winnings, final double machineBalance) {
        JOptionPane.showMessageDialog(null, "Thanks for playing the Slot Machine!!");
        if (winnings >= 0) {
            String printWinnings = String.format("Congratulations, you have won $%.2f, a total of $%.2f will be returned to you!%n",
                    (winnings), machineBalance);
            JOptionPane.showMessageDialog(null, printWinnings);
        } else {
            double returnValue;
            if ((machineBalance + winnings) > 0) {
                returnValue = (machineBalance + winnings);
            } else {
                returnValue = 0;
            }

            String printLosses = String.format("Sorry, you have lost -$%.2f, a total of $%.2f will be returned to you!%n",
                    Math.abs(winnings), returnValue);
            JOptionPane.showMessageDialog(null, printLosses);
        }
    }

    /**
     * Tells user their current balances.
     *
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     */
    public static void printStats(final double playerBalance, final double machineBalance) {
        String stats = String.format("Your balance in pocket is $%.2f and there is now $%.2f in the machine)%n",
                playerBalance, machineBalance);
        JOptionPane.showMessageDialog(null, stats);
    }

    /**
     * Tells user how much they have won for each condition met.
     *
     * @param rowColumnOrDiagonally How they won(row, column, from where to where diagonally)
     * @param direction             Which direction they won (across, down, diagonally)
     */
    public static void declareWinnings(final String rowColumnOrDiagonally, final String direction) {
        String declareWin = String.format("%nYou won on %s %s!", rowColumnOrDiagonally, direction);
        JOptionPane.showMessageDialog(null, declareWin);
    }
}
