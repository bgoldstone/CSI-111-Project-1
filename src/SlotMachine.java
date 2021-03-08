/*
 * Name: Ben Goldstone
 * Date: 3/2/2021
 * Professor: Dr. Joseph Helsing
 * Description: A program that emulates a slot machine.
 */

//import used libraries

import java.util.Random;
import java.util.Scanner;

/**
 * Main SlotMachine class.
 */
public class SlotMachine {
    /**
     * Main method
     */
    public static void main(String[] args) {
        welcome();
        menu();
    }

    /**
     * Prints Welcome message.
     */
    public static void welcome() {
        System.out.println("#".repeat(32));
        System.out.println("##   Welcome to the Casino!   ##");
        System.out.println("##                            ##");
        System.out.println("## Default Starting bet is $1 ##");
        System.out.println("##                            ##");
        System.out.println("##  Player starts with $100   ##");
        System.out.println("##                            ##");
        System.out.println("#".repeat(32));
    }

    /**
     * Prompts user for choice of what action to do.
     */
    public static void menu() {
        //initializes local variables/objects
        boolean flag = true;
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        double playerBalance = 100;
        double machineBalance = 0;
        double betAmount = 1;
        double winnings = 0;
        double totalOfBets = 0;
        do {
            //tells user balances
            printStats(playerBalance, machineBalance);

            //prompts user for menu option
            System.out.print("""
                    Please select an option:
                    1. Add Money to the Machine\s""");
            System.out.printf("\n2. Change bet amount (current bet is $%.2f and the default is $1.00)\n", betAmount);
            System.out.print("""
                    3. Play the game
                    4. Leave the Machine and pay out all of your winnings
                    Select(1,2,3,4):\s""");
            int choice = scan.nextInt();
            System.out.println();

            //selects method relevant to selection
            switch (choice) {
                case 1 -> {
                    double[] changeMoney = addMoney(scan, playerBalance, machineBalance);
                    playerBalance = changeMoney[0];
                    machineBalance = changeMoney[1];
                }
                case 2 -> betAmount = changeBetAmount(scan, playerBalance, machineBalance);
                case 3 -> {
                    double[] returnValues = play(playerBalance, machineBalance, betAmount, totalOfBets, rand);
                    playerBalance = returnValues[0];
                    machineBalance = returnValues[1];
                    betAmount = returnValues[2];
                    winnings += returnValues[3];
                    totalOfBets += returnValues[4];
                }
                case 4 -> {
                    cashOut(winnings, totalOfBets, machineBalance);
                    flag = false;
                }
                default -> System.out.print("\nInvalid Choice, ");
            }
        } while (flag);
    }

    /**
     * Allows user to add money to the machine.
     *
     * @param scan           Takes a scanner for user input
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @return a double[] array consisting of {playerBalance, machineBalance}
     */
    public static double[] addMoney(Scanner scan, double playerBalance, double machineBalance) {
        double balance;
        do {
            //prompts user for how much money to add
            System.out.printf("\nHow Much money would you like to add? " +
                    "(you have a balance of $%.2f and there is $%.2f in the machine): $", playerBalance, machineBalance);
            balance = scan.nextDouble();

            //checks if user has that much money to bet
            if (balance > playerBalance || balance < 0) {
                System.out.println("Invalid amount, please change how much you are depositing. " +
                        "(This cannot be over how much money you have or negative)");
                printStats(playerBalance, machineBalance);
            } else {
                machineBalance += balance;
                playerBalance -= balance;
                break;
            }
            sleep(1);
            System.out.println();
        } while (true);
        return new double[]{playerBalance, machineBalance};
    }

    /**
     * Allows user to change bet amount.
     *
     * @param scan           Takes a scanner for user input
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @return double of new betAmount value;
     */
    public static double changeBetAmount(Scanner scan, double playerBalance, double machineBalance) {
        //variable declaration
        double amount;
        double betAmount;
        //loops till bet is above $1.00 and in range of what player has
        do {
            //gets input from user
            System.out.print("How much money would you like to add to the machine? " +
                    "(Must be $1.00 or above) $");
            amount = scan.nextDouble();
            System.out.println();
            //checks if user has that much money to bet
            if (amount > playerBalance) {
                System.out.println("Invalid bet, please change how much you are betting. " +
                        "(This cannot be over how much money you have)");
                printStats(playerBalance, machineBalance);
            }

            //makes sure bet is not below $1.00
            if (amount < 1) {
                System.out.println("Sorry the default bet is 1.00, Please try again.");
            } else {
                betAmount = amount;
                break;
            }
        } while (true);

        //Tells user the bet
        System.out.printf("Your bet has been placed at $ %.2f\n\n", betAmount);
        System.out.println();
        return betAmount;
    }

    /**
     * Allows user to play the game.
     *
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @param betAmount      takes the bet amount for the user
     * @param totalOfBets    keeps track of how many total bets the player has placed in total
     * @param rand           takes in a Random object
     * @return double[] array consisting of {playerBalance, machineBalance, betAmount, winnings, totalOfBets}
     */
    public static double[] play(double playerBalance, double machineBalance, double betAmount, double totalOfBets, Random rand) {
        //keeps track of winnings for this round
        double winnings = 0;

        //checks betAmount is valid
        if (machineBalance - betAmount < 0) {
            System.out.println("Not enough money in the Machine! Try adding more money to the machine");
            return new double[]{playerBalance, machineBalance, betAmount, winnings, totalOfBets};
        } else {
            System.out.println("The Game is about to start!");
            sleep(1);
        }
        //takes bet
        machineBalance -= betAmount;

        //adds to total of bets
        totalOfBets += betAmount;

        //initializes variables
        String[] WORDS = {"Computer", "Science", "Java", "Hello", "World", "Professor", "Helsing"};

        //initializes random object
        String[][] reels = new String[3][3];

        //Assigns reels
        for (int i = 0; i < reels.length; i++) {
            for (int j = 0; j < reels[i].length; j++) {
                reels[i][j] = WORDS[rand.nextInt(WORDS.length)];
            }
        }
        System.out.println("\n");
        for (String[] reel : reels) {
            System.out.printf("%s\t\t%s\t\t%s\n", reel[0], reel[1], reel[2]);
            sleep(1);
        }

        //checks winning conditions
        //if three or two across
        if (reels[0][0].equals(reels[0][1]) && reels[0][1].equals(reels[0][2])) {
            winnings += betAmount * 3;
            declareWinnings("top row", "across");
        } else if (reels[0][0].equals(reels[0][1]) || reels[0][1].equals(reels[0][2])) {
            winnings += betAmount * 2;
            declareWinnings("top row", "across");
        }

        if (reels[1][0].equals(reels[1][1]) && reels[1][1].equals(reels[1][2])) {
            winnings += betAmount * 3;
            declareWinnings("middle row", "across");
        } else if (reels[1][0].equals(reels[1][1]) || reels[1][1].equals(reels[1][2])) {
            winnings += betAmount * 2;
            declareWinnings("middle row", "across");
        }

        if (reels[2][0].equals(reels[2][1]) && reels[2][1].equals(reels[2][2])) {
            winnings += betAmount * 3;
            declareWinnings("bottom row", "across");
        } else if (reels[2][0].equals(reels[2][1]) || reels[2][1].equals(reels[2][2])) {
            winnings += betAmount * 2;
            declareWinnings("bottom row", "across");
        }

        //if three or two down
        if (reels[0][0].equals(reels[1][0]) && reels[1][0].equals(reels[2][0])) {
            winnings += betAmount * 3;
            declareWinnings("left column", "down");
        } else if (reels[0][0].equals(reels[1][0]) || reels[1][0].equals(reels[2][0])) {
            winnings += betAmount * 2;
            declareWinnings("left column", "down");
        }

        if (reels[0][1].equals(reels[1][1]) && reels[1][1].equals(reels[2][1])) {
            winnings += betAmount * 3;
            declareWinnings("middle column", "down");
        } else if (reels[0][1].equals(reels[1][1]) || reels[1][1].equals(reels[2][1])) {
            winnings += betAmount * 2;
            declareWinnings("middle column", "down");
        }

        if (reels[0][2].equals(reels[1][2]) && reels[1][2].equals(reels[2][2])) {
            winnings += betAmount * 3;
            declareWinnings("right column", "down");
        } else if (reels[0][2].equals(reels[1][2]) || reels[1][2].equals(reels[2][2])) {
            winnings += betAmount * 2;
            declareWinnings("right column", "down");
        }

        //if two or three diagonal
        if (reels[0][0].equals(reels[1][1]) && reels[1][1].equals(reels[2][2])) {
            winnings += betAmount * 3;
            declareWinnings("top left to bottom right", "diagonal");
        } else if (reels[0][0].equals(reels[1][1]) || reels[1][1].equals(reels[2][2])) {
            winnings += betAmount * 2;
            declareWinnings("top left to bottom right", "diagonal");
        }

        if (reels[0][2].equals(reels[1][1]) && reels[1][1].equals(reels[2][0])) {
            winnings += betAmount * 3;
            declareWinnings("bottom left to top right", "diagonal");
        } else if (reels[0][2].equals(reels[1][1]) || reels[1][1].equals(reels[2][0])) {
            winnings += betAmount * 2;
            declareWinnings("bottom left to top right", "diagonal");
        }
        //adds winnings to total balance
        machineBalance += winnings;

        System.out.printf("\nYour total winnings are $%.2f\n", (winnings - betAmount));
        sleep(1);
        return new double[]{playerBalance, machineBalance, betAmount, winnings, totalOfBets};
    }

    /**
     * Displays ending message and Cash Out
     *
     * @param winnings       takes the amount of winnings for the player
     * @param totalOfBets    adds together all of the bets placed
     * @param machineBalance takes the machine's current balance
     */
    public static void cashOut(double winnings, double totalOfBets, double machineBalance) {
        System.out.println("Thanks for playing the Slot Machine!!");
        if (winnings - totalOfBets > 0) {
            System.out.printf("Congratulations, you have won $%.2f, a total of $%.2f will be returned to you!\n", (winnings - totalOfBets), machineBalance);
        } else {
            double returnValue;
            if ((machineBalance + winnings) > 0) {
                returnValue = (machineBalance + winnings);
            } else {
                returnValue = 0;
            }

            System.out.printf("Sorry, you have lost -$%.2f, a total of $%.2f will be returned to you!\n", Math.abs(winnings - totalOfBets), returnValue);
        }
    }

    /**
     * Tells user their current balances
     *
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     */
    public static void printStats(double playerBalance, double machineBalance) {
        System.out.printf("Your balance is now $%.2f and there is $%.2f in the machine)\n", playerBalance, machineBalance);
        sleep(1);
    }

    /**
     * Tells user how much they have won for each condition met
     *
     * @param rowColumnOrDiagonally How they won(row, column, from where to where diagonally)
     * @param direction             Which direction they won (across, down, diagonally)
     */
    public static void declareWinnings(String rowColumnOrDiagonally, String direction) {
        System.out.printf("\nYou won on %s %s!", rowColumnOrDiagonally, direction);
    }

    /**
     * Puts process to sleep for s seconds to give thinking/reels rotating effect
     *
     * @param seconds number of seconds for program to sleep for
     */
    public static void sleep(int seconds) {
        seconds *= 1000;
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}