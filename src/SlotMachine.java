import java.util.Scanner;

/**
 * Programmer: Ben Goldstone
 * Date: 3/2/2021
 * Professor: Dr. Joseph Helsing
 * Description: A program that Emulates a Slot Machine.
 */
public class SlotMachine {
    //public variables
    public static Scanner scan = new Scanner(System.in);
    public static double playerBalance = 100;
    public static double machineBalance = 0;
    public static final String[] WORDS = {"Computer", "Science", "Java", "Hello", "World", "Professor", "Helsing"};
    public static String[][] reels = new String[3][3];
    public static double betAmount = 1;
    public static double winnings = 0;

    public static void main(String[] args) {
        welcome();
        menu();
        endmsg();
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
     */
    public static void menu() {
        //initializes variables
        boolean flag = true;

        //slot machine loop
        while (flag) {
            //tells user balances
            System.out.printf("Your balance is now $%.2f and there is $%.2f in the machine)\n", playerBalance, machineBalance);

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
                case 1 -> addMoney();
                case 2 -> changeBetAmount();
                case 3 -> play();
                case 4 -> flag = false;
                default -> System.out.print("\nInvalid Choice, ");
            }
        }
    }

    /**
     * Allows user to add money to the machine
     */
    public static void addMoney() {
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
            } else {
                machineBalance += balance;
                playerBalance -= balance;
                flag = false;
            }
        }while(flag);
    }

    /**
     * Allows user to change bet amount
     */
    public static void changeBetAmount() {
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

    }

    /**
     * Allows user to play the game
     */
    public static void play() {

    }

    /**
     * Displays ending message and Cash Out
     */
    public static void endmsg(){
        System.out.println("Thanks for playing the Slot Machine!!");
        if(winnings > 0){
            System.out.printf("Congratulations, you have won $%.2f, a total of $%.2f will be returned to you!\n", winnings,machineBalance);
        } else{
            System.out.printf("Sorry, you have lost $%.2f, a total of $%.2f will be returned to you!\n", winnings,machineBalance);
        }
    }
}
