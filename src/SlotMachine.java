/**
 * Programmer: Ben Goldstone
 * Date: 3/2/2021
 * Professor: Dr. Joseph Helsing
 * Description: A program that Emulates a Slot Machine.
 */
public class SlotMachine {
    //public variables
    public double playerBalance = 100;
    public double machineBalance = 0;
    public final String[] WORDS = {"Computer", "Science", "Java", "Hello", "World", "Professor", "Helsing"};
    public String[][] reels = new String[3][3];
    public int defaultBet = 1;

    public static void main(String[] args) {
        welcome();
    }
    public static void welcome(){
        System.out.println("#".repeat(32));
        System.out.println("##   Welcome to the Casino!   ##");
        System.out.println("##                            ##");
        System.out.println("## Default Starting bet is $1 ##");
        System.out.println("##                            ##");
        System.out.println("##  Player starts with $100   ##");
        System.out.println("##                            ##");
        System.out.println("#".repeat(32));

    }
}
