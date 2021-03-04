import java.util.Arrays;
import java.util.Scanner;

/**
 * Programmer: Ben Goldstone
 * Date: 3/2/2021
 * Professor: Dr. Joseph Helsing
 * Description: A program that Emulates a Slot Machine.
 */
public class SlotMachine {
    //Global Variables

    public static void main(String[] args) {
        //initializes variables
        boolean flag = true;
        welcome();
        while (flag) {
            flag = menu();
        }
    }

    /**
     * Prints Welcome message
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
     * Prompts user for choice of what action to do
     *
     * @return boolean value to see if machine is still running
     */
    public static boolean menu() {
        //initializes local variables
        boolean keepGoing = true;
        Scanner scan = new Scanner(System.in);
        double playerBalance = 100.00;
        double machineBalance = 0.00;
        double betAmount = 1.00;
        double winnings = 0.00;

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
            case 2 -> betAmount = changeBetAmount(scan, playerBalance, machineBalance, betAmount);
            case 3 -> {
                double[] returnValues = play(scan, playerBalance, machineBalance, betAmount, winnings);
                playerBalance = returnValues[0];
                machineBalance = returnValues[1];
                betAmount = returnValues[2];
                winnings = returnValues[3];
            }
            case 4 -> keepGoing = cashOut(winnings, machineBalance);
            default -> System.out.print("\nInvalid Choice, ");
        }
        return keepGoing;

    }

    /**
     * Allows user to add money to the machine
     *
     * @param scan           Takes a scanner for user input
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @return a double[] array consisting of {playerBalance, machineBalance}
     */
    public static double[] addMoney(Scanner scan, double playerBalance, double machineBalance) {
        boolean flag = true;
        double balance;
        do {
            //prompts user for how much money to add
            System.out.printf("\nHow Much money would you like to add? (you have a balance of $%.2f and there is $%.2f in the machine): $", playerBalance, machineBalance);
            balance = scan.nextDouble();

            //checks if user has that much money to bet
            if (balance > playerBalance || balance < 0) {
                System.out.println("Invalid amount, please change how much you are depositing. " +
                        "(This cannot be over how much money you have or negative)");
                printStats(playerBalance, machineBalance);
            } else {
                machineBalance += balance;
                playerBalance -= balance;
                flag = false;
            }
        } while (flag);
        return new double[]{playerBalance, machineBalance};
    }

    /**
     * Allows user to change bet amount
     *
     * @param scan           Takes a scanner for user input
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @param betAmount      takes the bet amount for the user
     * @return double of new betAmount value;
     */
    public static double changeBetAmount(Scanner scan, double playerBalance, double machineBalance, double betAmount) {
        //variable declaration
        boolean flag = true;
        double amount;

        //loops till bet is above $1.00 and in range of what player has
        do {
            //gets input from user
            System.out.println("How much money would you like to add to the machine? (Must be $1.00 or above) $");
            amount = scan.nextDouble();

            //checks if user has that much money to bet
            if (amount > playerBalance) {
                System.out.println("Invalid bet, please change how much you are betting. " +
                        "(This cannot be over how much money you have)");
                printStats(playerBalance, machineBalance);

            }

            //makes sure bet is not below $1.00
            if (amount < 1.00) {
                System.out.println("Sorry the default bet is 1.00, Please try again.");
            } else {
                betAmount = amount;
                flag = false;
            }
        } while (flag);

        //Tells user the bet
        System.out.printf("Your bet has been placed at $ %.2f\n\n", betAmount);
        System.out.println();
        return betAmount;
    }

    /**
     * Allows user to play the game
     *
     * @param scan           Takes a scanner for user input
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @param betAmount      takes the bet amount for the user
     * @param winnings       takes the amount of winnings for the player
     * @return double[] array consisting of {playerBalance, machineBalance, betAmount, winnings}
     */
    public static double[] play(Scanner scan, double playerBalance, double machineBalance, double betAmount, double winnings) {
        //initializes variables
        final String[] WORDS = {"Computer", "Science", "Java", "Hello", "World", "Professor", "Helsing"};
        String[][] reels = new String[3][3];
        double[] returnValues = {playerBalance, machineBalance, betAmount, winnings};


        return Arrays.copyOf(returnValues, returnValues.length);
    }

    /**
     * Displays ending message and Cash Out
     *
     * @param winnings       takes the amount of winnings for the player
     * @param machineBalance takes the machine's current balance
     * @return boolean value to tell the main slot machine to stop going
     */
    public static boolean cashOut(double winnings, double machineBalance) {
        System.out.println("Thanks for playing the Slot Machine!!");
        if (winnings > 0) {
            System.out.printf("Congratulations, you have won $%.2f, a total of $%.2f will be returned to you!\n", winnings, machineBalance);
        } else {
            System.out.printf("Sorry, you have lost $%.2f, a total of $%.2f will be returned to you!\n", winnings, machineBalance);
        }
        return false;
    }

    /**
     * Tells user their current balances
     *
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     */
    public static void printStats(double playerBalance, double machineBalance) {
        System.out.printf("Your balance is now $%.2f and there is $%.2f in the machine)\n", playerBalance, machineBalance);
    }
}
