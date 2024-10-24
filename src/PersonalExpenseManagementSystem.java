import java.util.List;
import java.util.Scanner;

public class PersonalExpenseManagementSystem {
    public static void main(String[] args) {
        User user = new User("username", "password", 1000) {
            @Override
            public List<Transaction> getTransactions() {
                return List.of();
            }
        };
        user.registerUser("username", "password");
        user.loginUser("username", "password");
        user.listTransactions();
        user.saveUserData(); // Lưu dữ liệu user

        Scanner scanner = new Scanner(System.in);

        Report report = new Report() {
            @Override
            public void generateMonthlyReport() {

            }

            @Override
            public void generateAnnualReport() {

            }

            @Override
            public void generateCategoryReport(String category) {

            }

            @Override
            public void generateCharts() {

            }

            @Override
            public List<Transaction> getTransactions() {
                return List.of();
            }
        };  // Create an instance of Report
        Notification notification = new Notification();  // Create an instance of Notification

        // User registration and login
        System.out.println("Welcome to the Personal Expense Management System!");

        // Check if the user is already registered
        System.out.print("Do you already have an account? (yes/no): ");
        String hasAccount = scanner.nextLine();

        if (hasAccount.equalsIgnoreCase("no")) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            System.out.print("Enter your initial balance: ");
            double balance = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline

            // Register new user
            user = new User(username, password, balance) {
                @Override
                public List<Transaction> getTransactions() {
                    return List.of();
                }
            };
            user.registerUser(username, password);
        }

        // User login
        System.out.print("Login with your username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        // Simulate user logging in (this would validate credentials)
        if (user == null) {
            user = new User(loginUsername, loginPassword, 0) {
                @Override
                public List<Transaction> getTransactions() {
                    return List.of();
                }
            }; // You could load balance here if needed
        }
        user.loginUser(loginUsername, loginPassword);

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add Transaction");
            System.out.println("2. List Transactions");
            System.out.println("3. Edit Transaction");
            System.out.println("4. Delete Transaction");
            System.out.println("5. Set Budget");
            System.out.println("6. Check Budget");
            System.out.println("7. Update Budget");
            System.out.println("8. Manage Financial Goals");
            System.out.println("9. Generate Reports");
            System.out.println("10. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Add Transaction
                    System.out.print("Enter transaction type (Income/Expense): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    user.addTransaction(type, amount, category);
                    System.out.println("Transaction added!");

                    // Send alerts based on the transaction type
                    if (type.equalsIgnoreCase("Expense") && amount > 100) {  // Example threshold for alerts
                        notification.sendBudgetAlert(category);
                    }
                    break;

                case 2: // List Transactions
                    System.out.println("Listing all transactions:");
                    user.listTransactions();
                    break;

                case 3: // Edit Transaction
                    System.out.print("Enter transaction ID to edit: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter new transaction type (Income/Expense): ");
                    String newType = scanner.nextLine();
                    System.out.print("Enter new amount: ");
                    double newAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter new category: ");
                    String newCategory = scanner.nextLine();
                    user.editTransaction(editId, newType, newAmount, newCategory);
                    System.out.println("Transaction updated!");
                    break;

                case 4: // Delete Transaction
                    System.out.print("Enter transaction ID to delete: ");
                    int deleteId = scanner.nextInt();
                    user.deleteTransaction(deleteId);
                    System.out.println("Transaction deleted!");
                    break;

                case 5: // Set Budget
                    System.out.print("Enter budget category: ");
                    String budgetCategory = scanner.nextLine();
                    System.out.print("Enter budget limit: ");
                    double limit = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline
                    user.setBudget(budgetCategory, limit);
                    System.out.println("Budget set for category: " + budgetCategory);
                    break;

                case 6: // Check Budget
                    System.out.print("Enter budget category to check: ");
                    String checkCategory = scanner.nextLine();
                    user.budgetManager.checkBudget(checkCategory);
                    break;

                case 7: // Update Budget
                    System.out.print("Enter budget category to update: ");
                    String updateCategory = scanner.nextLine();
                    System.out.print("Enter new budget limit: ");
                    double newLimit = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline
                    user.budgetManager.updateBudget(updateCategory, newLimit);
                    System.out.println("Budget updated for category: " + updateCategory);
                    break;

                case 8: // Manage Financial Goals
                    System.out.print("Enter goal name: ");
                    String goalName = scanner.nextLine();
                    System.out.print("Enter target amount: ");
                    double targetAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline
                    user.setGoal(goalName, targetAmount);
                    System.out.print("Enter contribution amount: ");
                    double contribution = scanner.nextDouble();
                    user.contributeToGoal(goalName, contribution);

                    // Notify if the goal is nearing completion
                    if (contribution >= targetAmount) {
                        notification.sendGoalAlert(goalName);
                    }
                    System.out.println("Contribution added to goal: " + goalName);
                    break;

                case 9: // Generate Reports
                    System.out.print("Generate monthly report? (yes/no): ");
                    String monthlyReportChoice = scanner.nextLine();
                    if (monthlyReportChoice.equalsIgnoreCase("yes")) {
                        user.generateMonthlyReport();
                    }

                    System.out.print("Generate annual report? (yes/no): ");
                    String annualReportChoice = scanner.nextLine();
                    if (annualReportChoice.equalsIgnoreCase("yes")) {
                        user.generateAnnualReport();
                    }

                    report.generateMonthlyReport(user);
                    report.generateAnnualReport(user);
                    System.out.print("Enter category for report: ");
                    String reportCategory = scanner.nextLine();
                    report.generateCategoryReport(user, reportCategory);
                    report.generateCharts(user);

                    System.out.print("Generate charts? (yes/no): ");
                    String chartChoice = scanner.nextLine();
                    if (chartChoice.equalsIgnoreCase("yes")) {
                        user.generateCharts();
                    }
                    break;

                case 10: // Exit
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
