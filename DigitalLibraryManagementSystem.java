import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Main class to handle Library Management System
public class DigitalLibraryManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, User> users = new HashMap<>();
    static ArrayList<Book> books = new ArrayList<>();
    static Admin admin = new Admin("admin", "admin123");

    public static void main(String[] args) {
        System.out.println("Welcome to the Digital Library Management System");

        // Pre-populate some books and users for testing
        books.add(new Book("B001", "Effective Java", "Joshua Bloch"));
        books.add(new Book("B002", "Clean Code", "Robert C. Martin"));
        books.add(new Book("B003", "Java Concurrency in Practice", "Brian Goetz"));
        users.put("user1", new User("user1", "pass123", "User One"));

        boolean quit = false;
        while (!quit) {
            System.out.println("\nLogin as:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    userLogin();
                    break;
                case 3:
                    quit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Admin login function
    public static void adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            System.out.println("Admin login successful!");
            adminModule();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    // Admin module: Admin has full control
    public static void adminModule() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. View All Books");
            System.out.println("4. Add User");
            System.out.println("5. View All Users");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    viewAllBooks();
                    break;
                case 4:
                    addUser();
                    break;
                case 5:
                    viewAllUsers();
                    break;
                case 6:
                    quit = true;
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Add a new book to the library (Admin function)
    public static void addBook() {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        books.add(new Book(bookId, title, author));
        System.out.println("Book added successfully.");
    }

    // Remove a book from the library (Admin function)
    public static void removeBook() {
        System.out.print("Enter Book ID to remove: ");
        String bookId = scanner.nextLine();
        boolean removed = books.removeIf(book -> book.getBookId().equals(bookId));
        if (removed) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    // View all books in the library
    public static void viewAllBooks() {
        System.out.println("\nBooks in the Library:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Add a new user to the system (Admin function)
    public static void addUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();
        users.put(userId, new User(userId, password, name));
        System.out.println("User added successfully.");
    }

    // View all users in the system
    public static void viewAllUsers() {
        System.out.println("\nRegistered Users:");
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    // User login function
    public static void userLogin() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.containsKey(userId) && users.get(userId).getPassword().equals(password)) {
            System.out.println("User login successful!");
            userModule(users.get(userId));
        } else {
            System.out.println("Invalid User ID or Password.");
        }
    }

    // User module: Limited access to library functions
    public static void userModule(User user) {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View All Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewAllBooks();
                    break;
                case 2:
                    issueBook(user);
                    break;
                case 3:
                    returnBook(user);
                    break;
                case 4:
                    quit = true;
                    System.out.println("User logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Issue a book to a user
    public static void issueBook(User user) {
        System.out.print("Enter Book ID to issue: ");
        String bookId = scanner.nextLine();
        for (Book book : books) {
            if (book.getBookId().equals(bookId) && !book.isIssued()) {
                book.setIssued(true);
                user.issueBook(book);
                System.out.println("Book issued successfully.");
                return;
            }
        }
        System.out.println("Book not available or already issued.");
    }

    // Return a book
    public static void returnBook(User user) {
        System.out.print("Enter Book ID to return: ");
        String bookId = scanner.nextLine();
        Book returnedBook = user.returnBook(bookId);
        if (returnedBook != null) {
            returnedBook.setIssued(false);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("You have not issued this book.");
        }
    }
}

// Admin class
class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

// Book class
class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean issued;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.issued = false;
    }

    public String getBookId() {
        return bookId;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Issued: " + (issued ? "Yes" : "No");
    }
}

// User class
class User {
    private String userId;
    private String password;
    private String name;
    private ArrayList<Book> issuedBooks = new ArrayList<>();

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    public Book returnBook(String bookId) {
        for (Book book : issuedBooks) {
            if (book.getBookId().equals(bookId)) {
                issuedBooks.remove(book);
                return book;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Name: " + name;
    }
}
