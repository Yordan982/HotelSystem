import java.io.*;
import java.util.*;

public class UserAuthentication {
    private static final String FILE_ACCOUNTS = "accounts.txt";
    private static final String FILE_BOOKINGS = "bookings.txt";

    public static String signIn() {
        String enteredUsername = readFromScanner("Username: ");
        String enteredPassword = readFromScanner("Password: ");

        if (checkCredentials(enteredUsername, enteredPassword)) {
            System.out.println("You are now signed in.");
        } else {
            System.out.println("Invalid username or password. Try again.");
            signIn();
        }
        return enteredUsername;
    }

    public static void register() {
        String enteredUsername = "";
        boolean usernameTaken = true;

        while (usernameTaken) {
            enteredUsername = readFromScanner("Enter a username: ");
            if (isUsernameTaken(enteredUsername)) {
                System.out.println("Username already taken. Please choose another username.");
            } else {
                usernameTaken = false;
            }
        }

        int userType = 0;
        String enteredPassword = readFromScanner("Enter a password: ");

        //If you enter *admin* in front of the password, you will become an admin. Example:
        //you enter password "*admin*myPassword" and you become an admin and your
        //password will be saved as "myPassword".
        if (enteredPassword.startsWith("*admin*")) {
            userType = 1;
            enteredPassword = enteredPassword.substring(7);
        }

        User user = new User(enteredUsername, enteredPassword, userType);
        writeUserToAccountsFile(user);

        System.out.println("Registration successful.");
    }

    private static String readFromScanner(String prompt) {
        String input = "";
        try {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(prompt);
            input = consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    private static boolean checkCredentials(String username, String password) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(FILE_ACCOUNTS));
            String line;

            while ((line = fileReader.readLine()) != null) {
                String[] userData = line.split(", ");
                if (userData.length == 3) {
                    String storedUsername = userData[1].trim();
                    String storedPassword = userData[2].trim();

                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        fileReader.close();
                        return true;
                    }
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean isUsernameTaken(String username) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(FILE_ACCOUNTS));
            String line;

            while ((line = fileReader.readLine()) != null) {
                String[] userData = line.split(", ");
                if (userData.length == 3) {
                    String storedUsername = userData[1].trim();

                    if (username.equals(storedUsername)) {
                        fileReader.close();
                        return true;
                    }
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void writeUserToAccountsFile(User user) {
        try {
            FileWriter writer = new FileWriter(FILE_ACCOUNTS, true);
            writer.write(user.getUserType() + ", " + user.getUsername() + ", " + user.getPassword() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cancelBooking(String username) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(FILE_BOOKINGS));
        List<String> updatedBookings = new ArrayList<>();

        String line;
        int recordCounter = 0;
        boolean hadAnyCancellations = false;
        while ((line = fileReader.readLine()) != null) {
            String[] bookingData = line.split(", ");
            String matchUsername = bookingData[0];

            if (username.equals(matchUsername)) {
                if (recordCounter == 0) {
                    System.out.println("Your bookings:");
                }
                System.out.println(++recordCounter + ". " + line);

                if (getUserChoice("Do you want to cancel this booking? (yes/no): ")) {
                    hadAnyCancellations = true;
                    continue;
                }
            }
            updatedBookings.add(line);
        }
        fileReader.close();

        if (recordCounter == 0) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        FileWriter fileWriter = new FileWriter(FILE_BOOKINGS);
        for (String booking : updatedBookings) {
            fileWriter.write(booking + "\n");
        }
        fileWriter.close();

        if (hadAnyCancellations) {
            System.out.println("Booking/s canceled successfully.");
        } else {
            System.out.println("You did not cancel any bookings.");
        }
    }

    private static boolean getUserChoice(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("yes");
    }
}