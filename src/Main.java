import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        System.out.println("Welcome to our hotel! Please type an option from the menu:");
        System.out.println("1. View rooms");
        System.out.println("2. Book a Room");
        System.out.println("3. Cancel Booking");
        System.out.println("4. Sign in/Register");

        while (!validInput) {

            int command = Integer.parseInt(scanner.nextLine());

            switch (command) {
                case 1 -> {
                    validInput = true;
                }
                case 2 -> validInput = true;
                case 3 -> {
                    UserAuthentication.cancelBooking(UserAuthentication.signIn());
                    validInput = true;
                }
                case 4 -> {
                    System.out.println("Do you have an account?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int doYouHaveAnAccount = Integer.parseInt(scanner.nextLine());
                    switch (doYouHaveAnAccount) {
                        case 1 -> UserAuthentication.signIn();
                        case 2 -> UserAuthentication.register();
                    }
                    validInput = true;
                }
                default -> System.out.println("Please enter a valid number.");
            }
        }
    }
}