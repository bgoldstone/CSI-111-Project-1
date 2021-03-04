import java.util.Arrays;
import java.util.Random;
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
        welcome();
        menu();
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
     */
    public static void menu() {
        //initializes local variables
        boolean keepGoing = true;
        Scanner scan = new Scanner(System.in);
        double playerBalance = 100.00;
        double machineBalance = 0.00;
        double betAmount = 1.00;
        double winnings = 0.00;
        double startingBets = 0.00;
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
                case 2 -> betAmount = changeBetAmount(scan, playerBalance, machineBalance, betAmount);
                case 3 -> {
                    double[] returnValues = play(playerBalance, machineBalance, betAmount, startingBets);
                    playerBalance = returnValues[0];
                    machineBalance = returnValues[1];
                    betAmount = returnValues[2];
                    winnings += returnValues[3];
                    startingBets += returnValues[4];
                }
                case 4 -> keepGoing = cashOut(winnings, startingBets, machineBalance);
                default -> System.out.print("\nInvalid Choice, ");
            }
        }while (keepGoing);
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
        return new double[] {playerBalance,machineBalance};
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
     * @param playerBalance  takes the player's current balance
     * @param machineBalance takes the machine's current balance
     * @param betAmount      takes the bet amount for the user
     * @return double[] array consisting of {playerBalance, machineBalance, betAmount, winnings, startingBets}
     */
    public static double[] play(double playerBalance, double machineBalance, double betAmount, double startingBets) {
        //keeps track of winnings for this round
        double winnings = 0;
        if (machineBalance - betAmount < 0) {
            System.out.println("Not enough money in the Machine! Try adding more money to the machine");
            return new double[]{playerBalance, machineBalance, betAmount, winnings};
        } else {
            System.out.println("The Game is about to start!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        machineBalance -= betAmount;
        //initializes variables
        String[] WORDS = {"Computer", "Science", "Java", "Hello", "World", "Professor", "Helsing"};
        //initializes random object
        Random rand = new Random();
        String[][] reels = new String[3][3];
        //Assigns reels
        for (int i = 0; i < reels.length; i++) {
            for (int j = 0; j < reels[i].length; j++) {
                reels[i][j] = WORDS[rand.nextInt(WORDS.length)];
            }
        }
        System.out.println("\n");
        for (String[] reel : reels) {
            System.out.println(Arrays.toString(reel));
        }
        System.out.println();

        //checks Winning conditions

        //if three or two across
        if (reels[0][0].equals(reels[0][1]) && reels[0][1].equals(reels[0][2])) {
            winnings += (betAmount * 3);
            startingBets += betAmount;
            declareWinnings("top row","across",betAmount*2);
        } else if (reels[0][0].equals(reels[0][1]) || reels[0][1].equals(reels[0][2])) {
            winnings += (betAmount * 2);
            startingBets += betAmount;
            declareWinnings("top row","across",betAmount);
        }

        if (reels[1][0].equals(reels[1][1]) && reels[1][1].equals(reels[1][2])) {
            winnings += (betAmount * 3);
            startingBets += betAmount;
            declareWinnings("middle row","across",betAmount*2);
        } else if (reels[1][0].equals(reels[1][1]) || reels[1][1].equals(reels[1][2])) {
            winnings += (betAmount * 2);
            startingBets += betAmount;
            declareWinnings("middle row","across",betAmount);
        }

        if (reels[2][0].equals(reels[2][1]) && reels[2][1].equals(reels[2][2])) {
            winnings += (betAmount * 3);
            startingBets += betAmount;
            declareWinnings("bottom row","across",betAmount*2);
        } else if (reels[2][0].equals(reels[2][1]) || reels[2][1].equals(reels[2][2])) {
            winnings += (betAmount * 2);
            startingBets += betAmount;
            declareWinnings("bottom row","across",betAmount);
        }

        //if three or two down
        if (reels[0][0].equals(reels[1][0]) && reels[1][0].equals(reels[2][0])) {
            winnings += (betAmount * 3);
            startingBets += betAmount;
            declareWinnings("left column","down",betAmount*2);
        } else if (reels[0][0].equals(reels[1][0]) || reels[1][0].equals(reels[2][0])) {
            winnings += (betAmount * 2);
            startingBets += betAmount;
            declareWinnings("left column","down",betAmount);
        }

        if (reels[0][1].equals(reels[1][1]) && reels[1][1].equals(reels[2][1])) {
            winnings += (betAmount * 3);
            startingBets += betAmount;
            declareWinnings("middle column","down",betAmount*2);
        } else if (reels[0][1].equals(reels[1][1]) || reels[1][1].equals(reels[2][1])) {
            winnings += (betAmount * 2);
            startingBets += betAmount;
            declareWinnings("middle column","down",betAmount);
        }

        if (reels[0][2].equals(reels[1][2]) && reels[1][2].equals(reels[2][2])) {
            winnings += (betAmount * 3);
            startingBets += betAmount;
            declareWinnings("right column","down",betAmount*2);
        } else if (reels[0][2].equals(reels[1][2]) || reels[1][2].equals(reels[2][2])) {
            winnings += (betAmount * 2);
            startingBets += betAmount;
            declareWinnings("right column","down",betAmount);
        }

        //if two or three diagonal
        if(reels[0][0].equals(reels[1][1]) && reels[1][1].equals(reels[2][2])){
            winnings += (betAmount * 3);
            startingBets += betAmount;
            declareWinnings("top left to bottom right","diagonal",betAmount*2);
        }else if(reels[0][0].equals(reels[1][1]) || reels[1][1].equals(reels[2][2])){
            winnings += (betAmount * 2);
            declareWinnings("top left to bottom right","diagonal",betAmount);
            startingBets += betAmount;
        }

        if(reels[0][2].equals(reels[1][1]) && reels[1][1].equals(reels[2][0])){
            winnings += (betAmount * 3);
            startingBets += betAmount;
            declareWinnings("bottom left to top right","diagonal",betAmount*2);
        }else if(reels[0][2].equals(reels[1][1]) || reels[1][1].equals(reels[2][0])){
            winnings += (betAmount * 2);
            startingBets += betAmount;
            declareWinnings("bottom left to top right","diagonal",betAmount);
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new double[]{playerBalance, machineBalance, betAmount, winnings, startingBets};
    }

    /**
     * Displays ending message and Cash Out
     *
     * @param winnings       takes the amount of winnings for the player
     * @param machineBalance takes the machine's current balance
     * @return boolean value to tell the main slot machine to stop going
     */
    public static boolean cashOut(double winnings, double startingBets, double machineBalance) {
        System.out.println("Thanks for playing the Slot Machine!!");
        if (winnings-startingBets > 0) {
            System.out.printf("Congratulations, you have won $%.2f, a total of $%.2f will be returned to you!\n", (winnings-startingBets), (machineBalance + winnings));
        } else {
            double returnValue;
            if((machineBalance + winnings) > 0) {
                returnValue = (machineBalance + winnings);
            } else{
                returnValue = 0;
            }

            System.out.printf("Sorry, you have lost -$%.2f, a total of $%.2f will be returned to you!\n", Math.abs(winnings-startingBets), returnValue);
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

    /**
     * Tells user how much they have won
     * @param rowColumnOrDiagonally How they won(row, column, from where to where diagonally)
     * @param direction Which direction they won (across, down, diagonally)
     * @param winnings tells user how much they won
     */
    public static void declareWinnings(String rowColumnOrDiagonally, String direction, double winnings){
        System.out.printf("You won $%.2f on %s %s!", winnings, rowColumnOrDiagonally, direction);
    }
}
