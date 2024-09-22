import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineExaminationSystem {

    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, User> users = new HashMap<>();  // Stores users (userId -> User)

    public static void main(String[] args) {
        // Create a sample user
        User user = new User("user1", "password123", "User One", "user1@example.com");
        users.put(user.getUserId(), user);
        
        System.out.println("Welcome to the Online Examination System!");
        boolean loggedIn = false;
        while (!loggedIn) {
            loggedIn = login();
        }
    }

    // Function to handle user login
    public static boolean login() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.containsKey(userId) && users.get(userId).getPassword().equals(password)) {
            System.out.println("Login successful!");
            showMenu(users.get(userId));
            return true;
        } else {
            System.out.println("Invalid User ID or Password. Please try again.");
            return false;
        }
    }

    // Function to show the main menu
    public static void showMenu(User user) {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nMenu:");
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Take Exam");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    updateProfile(user);
                    break;
                case 2:
                    updatePassword(user);
                    break;
                case 3:
                    takeExam(user);
                    break;
                case 4:
                    quit = true;
                    System.out.println("You have been logged out. Thank you!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Function to update the user profile
    public static void updateProfile(User user) {
        System.out.println("\nUpdate Profile:");
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();
        user.setName(newName);
        user.setEmail(newEmail);
        System.out.println("Profile updated successfully!");
    }

    // Function to update the user password
    public static void updatePassword(User user) {
        System.out.println("\nUpdate Password:");
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();
        if (user.getPassword().equals(currentPassword)) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            user.setPassword(newPassword);
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Current password is incorrect.");
        }
    }

    // Function to take the exam
    public static void takeExam(User user) {
        String[] questions = {
            "1. What is the capital of France?\n(A) Paris (B) Rome (C) Berlin (D) Madrid",
            "2. Which is the largest planet in our solar system?\n(A) Earth (B) Jupiter (C) Saturn (D) Mars",
            "3. What is the square root of 64?\n(A) 6 (B) 8 (C) 10 (D) 12"
        };

        char[] correctAnswers = {'A', 'B', 'B'};  // Correct answers
        char[] userAnswers = new char[questions.length];  // Store user's answers

        System.out.println("\nStarting the exam. You have 1 minute to complete.");

        // Timer for auto-submit after 1 minute
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime is up! Auto-submitting the exam...");
                submitExam(correctAnswers, userAnswers);
                timer.cancel();  // End timer after auto-submit
            }
        };

        // Schedule timer for 1 minute (60 seconds)
        timer.schedule(task, 60000);

        // Exam loop
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.print("Your answer (A/B/C/D): ");
            userAnswers[i] = scanner.next().charAt(0);
        }

        // If the user submits before the time runs out
        submitExam(correctAnswers, userAnswers);
        timer.cancel();  // Cancel timer if exam is submitted early
    }

    // Function to submit the exam and display the results
    public static void submitExam(char[] correctAnswers, char[] userAnswers) {
        int score = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }

        System.out.println("\nExam Submitted.");
        System.out.println("You scored: " + score + "/" + correctAnswers.length);
    }
}

// Class to represent a User
class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
