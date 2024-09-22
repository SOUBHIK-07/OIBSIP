import java.util.HashMap;
import java.util.Scanner;

// Main class
public class OnlineReservationSystem {

    // Database simulation using HashMap for simplicity
    static HashMap<String, Reservation> reservations = new HashMap<>();
    static HashMap<String, User> users = new HashMap<>();

    // Scanner for input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeUsers(); // Predefined users for login
        System.out.println("Welcome to Online Reservation System!");

        if (login()) {
            while (true) {
                System.out.println("\nSelect an option:");
                System.out.println("1. Make a Reservation");
                System.out.println("2. Cancel a Reservation");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                switch (choice) {
                    case 1:
                        makeReservation();
                        break;
                    case 2:
                        cancelReservation();
                        break;
                    case 3:
                        System.out.println("Thank you for using the system!");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    // User login system
    public static boolean login() {
        System.out.print("Enter Login ID: ");
        String loginId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.containsKey(loginId) && users.get(loginId).getPassword().equals(password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid login credentials. Try again.");
            return false;
        }
    }

    // Reservation form
    public static void makeReservation() {
        System.out.println("Enter Reservation Details");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();

        System.out.print("Enter Train Name: ");
        String trainName = scanner.nextLine();

        System.out.print("Enter Class Type: ");
        String classType = scanner.nextLine();

        System.out.print("Enter Date of Journey (DD/MM/YYYY): ");
        String dateOfJourney = scanner.nextLine();

        System.out.print("Enter Departure (From): ");
        String from = scanner.nextLine();

        System.out.print("Enter Destination (To): ");
        String to = scanner.nextLine();

        // Generate PNR number (for simplicity, using a random number)
        String pnr = "PNR" + (int) (Math.random() * 10000);

        // Create a new reservation and store it in the database
        Reservation reservation = new Reservation(pnr, name, trainNumber, trainName, classType, dateOfJourney, from, to);
        reservations.put(pnr, reservation);

        System.out.println("Reservation successful! Your PNR number is: " + pnr);
    }

    // Cancellation form
    public static void cancelReservation() {
        System.out.print("Enter PNR Number for Cancellation: ");
        String pnr = scanner.nextLine();

        if (reservations.containsKey(pnr)) {
            System.out.println("Reservation Details: " + reservations.get(pnr));
            System.out.print("Do you want to confirm cancellation? (Y/N): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                reservations.remove(pnr);
                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Cancellation aborted.");
            }
        } else {
            System.out.println("Invalid PNR number.");
        }
    }

    // Initialize predefined users for login
    public static void initializeUsers() {
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));
    }
}

// Class to store reservation details
class Reservation {
    String pnr, name, trainNumber, trainName, classType, dateOfJourney, from, to;

    public Reservation(String pnr, String name, String trainNumber, String trainName, String classType, String dateOfJourney, String from, String to) {
        this.pnr = pnr;
        this.name = name;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr + ", Name: " + name + ", Train: " + trainNumber + " - " + trainName +
            ", Class: " + classType + ", Date: " + dateOfJourney + ", From: " + from + ", To: " + to;
    }
}

// Class to store user details
class User {
    String loginId;
    String password;

    public User(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
