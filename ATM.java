import java.util.ArrayList;
import java.util.Scanner;

// Main ATM class to handle operations
public class ATM {
    public static void main(String[] args) {
        // Sample user for testing
        User user = new User("12345", "54321", 1000.00);
        ATMInterface atm = new ATMInterface(user);
        atm.start();
    }
}

// Class to represent a User
class User {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public void withdraw(double amount) {
        balance -= amount;
        transactionHistory.add("Withdrew: $" + amount);
    }

    public void transfer(double amount, User recipient) {
        balance -= amount;
        recipient.deposit(amount);
        transactionHistory.add("Transferred: $" + amount + " to User ID: " + recipient.getUserId());
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }
}

// ATM Interface to handle user interaction
class ATMInterface {
    private User user;
    private Scanner scanner;

    public ATMInterface(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

    // Start the ATM operations
    public void start() {
        System.out.println("Welcome to the ATM System");
        if (login()) {
            boolean quit = false;
            while (!quit) {
                showMenu();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        showTransactionHistory();
                        break;
                    case 2:
                        performWithdraw();
                        break;
                    case 3:
                        performDeposit();
                        break;
                    case 4:
                        performTransfer();
                        break;
                    case 5:
                        quit = true;
                        System.out.println("Thank you for using the ATM!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN. Access Denied.");
        }
    }

    // User login
    private boolean login() {
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        return userId.equals(user.getUserId()) && pin.equals(user.getUserPin());
    }

    // Show the ATM menu
    private void showMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Choose an option: ");
    }

    // Show the user's transaction history
    private void showTransactionHistory() {
        ArrayList<String> history = user.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : history) {
                System.out.println(transaction);
            }
        }
    }

    // Perform a withdrawal
    private void performWithdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (amount > user.getBalance()) {
            System.out.println("Insufficient balance.");
        } else {
            user.withdraw(amount);
            System.out.println("Withdrawal successful. Current balance: $" + user.getBalance());
        }
    }

    // Perform a deposit
    private void performDeposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        user.deposit(amount);
        System.out.println("Deposit successful. Current balance: $" + user.getBalance());
    }

    // Perform a transfer to another user
    private void performTransfer() {
        System.out.print("Enter the recipient's User ID: ");
        String recipientId = scanner.next();
        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        // Assuming a recipient user is available with this ID for simplicity
        User recipient = new User(recipientId, "xxxx", 0); // Fake recipient for demonstration
        if (amount > user.getBalance()) {
            System.out.println("Insufficient balance.");
        } else {
            user.transfer(amount, recipient);
            System.out.println("Transfer successful. Current balance: $" + user.getBalance());
        }
    }
}
