import interfaces.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.time.ZoneId;
import java.io.File;
import java.util.Date;

// User Class
abstract class User implements IUserManagement, ITransactionManagement, IBudgetManagement, IFinancialGoalManagement, IReportGeneration {
    private final String username;
    private final String password;
    private double balance;
    private final List<Transaction> transactions;
    final Budget budgetManager = new Budget();  // To handle budgets
    private final FinancialGoalManager goalManager = new FinancialGoalManager();  // To handle financial goals

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public boolean askToSaveData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to save your data? (yes/no)");
        String choice = scanner.nextLine();
        return choice.equalsIgnoreCase("yes");
    }

    public void saveUserData() {
        if (askToSaveData()) {
            try {
                // Path for the text file
                String filePath = System.getProperty("user.home") + File.separator + "user_data.txt";
                File userFile = new File(filePath);

                // Create file if it doesn't exist
                if (!userFile.exists()) {
                    if (userFile.createNewFile()) {
                        System.out.println("File has been created: " + userFile.getAbsolutePath());
                    } else {
                        System.out.println("Cannot create file: " + userFile.getAbsolutePath());
                        return;  // Exit if file cannot be created
                    }
                } else {
                    System.out.println("File already exists: " + userFile.getAbsolutePath());
                }

                // Save user info
                saveUserInfo(userFile);

                // Save transaction data
                saveTransactions(userFile);

                System.out.println("The data has been saved successfully.");
            } catch (IOException e) {
                System.err.println("Error while saving data: " + e.getMessage());
            }
        }
    }

    private void saveTransactions(File userFile) {
    }

    private void saveUserInfo(File userFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            // Save user info to file
            writer.write("username: " + username + ", balance: " + balance);
            writer.newLine();
        }
    }


    @Override
    public void registerUser(String username, String password) {
        System.out.println("User registered: " + username);
    }

    @Override
    public void loginUser(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    @Override
    public double getUserBalance() {
        return balance;
    }

    @Override
    public void viewReports() {
        // You might want to implement the Report generation in a more detailed manner.
        generateMonthlyReport();
        generateAnnualReport();
        System.out.println("Reports generated.");
    }

    @Override
    public void addTransaction(String type, double amount, String category) {
        Transaction transaction = new Transaction(type, amount, category);
        transactions.add(transaction);
        balance += transaction.getAmount();  // Update balance
        budgetManager.recordSpending(category, transaction.getAmount());

        // Check if spending exceeds budget and send alert
        if (budgetManager.isOverBudget(category)) {
            Notification notification = new Notification();
            notification.sendBudgetAlert(category);  // Trigger budget alert
        }

        System.out.println("Transaction added.");
    }

    @Override
    public void editTransaction(int transactionId, String type, double amount, String category) {
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                double oldAmount = t.getAmount();
                t.setType(type);
                t.setAmount(amount);
                t.setCategory(category);
                // Adjust balance based on old and new amount
                balance += (amount - oldAmount);
                System.out.println("Transaction updated.");
                return;
            }
        }
        System.out.println("Transaction not found.");
    }

    @Override
    public void deleteTransaction(int transactionId) {
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                transactions.remove(t);
                balance -= t.getAmount();  // Adjust balance
                System.out.println("Transaction deleted.");
                return;
            }
        }
        System.out.println("Transaction not found.");
    }

    @Override
    public void listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    @Override
    public void setBudget(String category, double limit) {
        budgetManager.setBudget(category, limit);
        System.out.println("Budget set for " + category + ": " + limit);
    }

    @Override
    public void checkBudget(String category) {

    }

    @Override
    public void updateBudget(String category, double newLimit) {

    }

    @Override
    public void manageBudget(String budgetCategory, double limit) {

    }

    @Override
    public void setGoal(String goalName, double targetAmount) {
        goalManager.setGoal(goalName, targetAmount);
        System.out.println("Goal set: " + goalName + " with target amount " + targetAmount);
    }

    @Override
    public void trackProgress(String goalName) {
        goalManager.trackProgress(goalName);
    }

    @Override
    public void adjustGoal(String goalName, double newTargetAmount) {
        goalManager.adjustGoal(goalName, newTargetAmount);
        System.out.println("Goal adjusted: " + goalName + " to " + newTargetAmount);
    }

    @Override
    public void contributeToGoal(String goalName, double amount) {
        goalManager.contributeToGoal(goalName, amount);
        System.out.println("Contributed " + amount + " to goal: " + goalName);
    }

    @Override
    public void manageGoal(String goalName, double targetAmount) {

    }

    @Override
    public void generateMonthlyReport() {
        // Implement your report generation logic here
        System.out.println("Generating monthly report...");
    }

    @Override
    public void generateAnnualReport() {
        // Implement your report generation logic here
        System.out.println("Generating annual report...");
    }

    @Override
    public void generateCategoryReport(String category) {
        // Implement your report generation logic here
        System.out.println("Generating report for category: " + category);
    }

    @Override
    public void generateCharts() {
        // Implement your chart generation logic here
        System.out.println("Generating charts...");
    }
}

// Transaction Class
class Transaction {
    private static int counter = 0;
    private final int id;
    private String type;  // Income or Expense
    private double amount;
    private String category;
    private final Date date;

    public Transaction(String type, double amount, String category) {
        this.id = ++counter;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = new Date();  // Current date
    }

    public int getId() {
        return id;
    }

    public String getType() {  // Add this method
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return type.equals("Income") ? amount : -amount;
    }

    public boolean isInCurrentMonth() {
        LocalDate now = LocalDate.now();
        LocalDate transactionDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return now.getMonth() == transactionDate.getMonth() && now.getYear() == transactionDate.getYear();
    }


    public boolean isInCurrentYear() {
        LocalDate now = LocalDate.now(); //lấy date hiện tại under dạng đối tượng LocalDate
        LocalDate localDate = date.toInstant()/*dùng để đại diện cho 1 time cụ thể rõ từng hour, minute, second*/.atZone(ZoneId.systemDefault()).toLocalDate();
        //move ổi đối tượng Date sang LocalDate
        //1. move đối tượng Date thành Instant bằng phương thức toInstant()
        //2. sử dụng atZone(ZoneId.systemDefault()) để gán bùng time hiện tại
        //3. finally, move thành LocalDate
        return now.getYear() == localDate.getYear();
        //compare năm hiện tại với năm của đối tượng localDate
        //trả về true nếu năm hiện tại trùng với năm của localDate, ngc lại trả về false
    }

    @Override
    public String toString() {
        return "Transaction ID: " + id + ", Type: " + type + ", Amount: $" + amount + ", Category: " + category + ", Date: " + date;
    }


    public String getCategory() {
        return category;  // Fix this method
    }
}

// Budget Class
class Budget implements IBudgetManagement {
    private final Map<String, Double> categoryBudgets = new HashMap<>();
    private final Map<String, Double> currentSpendings = new HashMap<>();

    @Override
    public void setBudget(String category, double limit) {
        categoryBudgets.put(category, limit);
        currentSpendings.put(category, 0.0);  // Start with 0 spending
        System.out.println("Budget set for " + category + ": $" + limit);
    }

    @Override
    public void checkBudget(String category) {
        double budget = categoryBudgets.getOrDefault(category, 0.0);
        double spending = currentSpendings.getOrDefault(category, 0.0);
        System.out.println("Spending on " + category + ": $" + spending + " / $" + budget);
    }

    @Override
    public void updateBudget(String category, double newLimit) {
        categoryBudgets.put(category, newLimit);
        System.out.println("Updated budget for " + category + " to $" + newLimit);
    }

    @Override
    public void manageBudget(String budgetCategory, double limit) {
        // Not implemented yet
    }

    // Method to record spending for each category
    public void recordSpending(String category, double amount) {
        double currentSpending = currentSpendings.getOrDefault(category, 0.0);
        currentSpendings.put(category, currentSpending + amount);
    }

    // Method to get total spend for a specific category
    public double getCategorySpending(String category) {
        return currentSpendings.getOrDefault(category, 0.0);
    }

    // Method to get the budget limit for a specific category
    public double getCategoryLimit(String category) {
        return categoryBudgets.getOrDefault(category, 0.0);
    }

    // Method to check whether spending exceeds budget or not
    public boolean isOverBudget(String category) {
        double spending = getCategorySpending(category);
        double limit = getCategoryLimit(category);
        return spending > limit;
    }
}


// Financial Goal Management Classes
class FinancialGoalManager implements IFinancialGoalManagement {
    private final List<FinancialGoal> goals = new ArrayList<>();

    @Override
    public void setGoal(String goalName, double targetAmount) {
        goals.add(new FinancialGoal(goalName, targetAmount));
        System.out.println("Goal set: " + goalName + " - Target: $" + targetAmount);
    }

    @Override
    public void trackProgress(String goalName) {
        for (FinancialGoal goal : goals) {
            if (goal.getGoalName().equals(goalName)) {
                double progress = (goal.getCurrentAmount() / goal.getTargetAmount()) * 100;
                System.out.println("Progress for " + goalName + ": " + progress + "%");
                return;
            }
        }
        System.out.println("Goal not found.");
    }

    @Override
    public void adjustGoal(String goalName, double newTargetAmount) {
        for (FinancialGoal goal : goals) {
            if (goal.getGoalName().equals(goalName)) {
                goal.setTargetAmount(newTargetAmount);
                System.out.println("Adjusted goal " + goalName + " to target: $" + newTargetAmount);
                return;
            }
        }
        System.out.println("Goal not found.");
    }

    @Override
    public void contributeToGoal(String goalName, double amount) {
        for (FinancialGoal goal : goals) {
            if (goal.getGoalName().equals(goalName)) {
                goal.contribute(amount);
                return;
            }
        }
        System.out.println("Goal not found.");
    }

    @Override
    public void manageGoal(String goalName, double targetAmount) {

    }
}
// financialGoal classes
class FinancialGoal {
    private final String goalName;
    private double targetAmount;
    private double currentAmount;

    public FinancialGoal(String goalName, double targetAmount) {
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
    }

    public String getGoalName() {
        return goalName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void contribute(double amount) {
        currentAmount += amount;
        System.out.println("Contributed $" + amount + " to goal " + goalName);
    }
}

// Report Class
abstract class Report implements IReportGeneration {

    public void generateMonthlyReport(User user) {
        System.out.println("Generating Monthly Report...");
        List<Transaction> transactions = user.getTransactions();

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {
            if (transaction.isInCurrentMonth()) {
                if (transaction.getType().equalsIgnoreCase("Income")) {
                    totalIncome += transaction.getAmount();
                } else if (transaction.getType().equalsIgnoreCase("Expense")) {
                    totalExpense += Math.abs(transaction.getAmount());  // Use absolute value for expenses
                }
            }
        }

        System.out.printf("Total Income for this month: $%.2f%n", totalIncome);
        System.out.printf("Total Expense for this month: $%.2f%n", totalExpense);
    }


    public void generateAnnualReport(User user) {
        System.out.println("Generating Annual Report...");
        List<Transaction> transactions = user.getTransactions();

        // Filter this year's transactions
        double totalIncome = 0;
        double totalExpense = 0;
        for (Transaction transaction : transactions) {
            if (transaction.isInCurrentYear()) {
                if (transaction.getType().equalsIgnoreCase("Income")) {
                    totalIncome += transaction.getAmount();
                } else if (transaction.getType().equalsIgnoreCase("Expense")) {
                    totalExpense += transaction.getAmount();
                }
            }
        }

        System.out.println("Annual Income: " + totalIncome);
        System.out.println("Annual Expense: " + totalExpense);
    }

    public void generateCategoryReport(User user, String category) {
        System.out.println("Generating Category Report for: " + category);
        List<Transaction> transactions = user.getTransactions();

        // Calculate transactions by category
        double totalAmount = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equalsIgnoreCase(category)) {
                totalAmount += transaction.getAmount();
            }
        }

        System.out.println("Total amount spent in category " + category + ": " + totalAmount);
    }

    public void generateCharts(User user) {
        System.out.println("Generating Financial Charts...");
        // Simulator creates a chart by displaying the number of transactions
        List<Transaction> transactions = user.getTransactions();
        int incomeCount = 0;
        int expenseCount = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType().equalsIgnoreCase("Income")) {
                incomeCount++;
            } else if (transaction.getType().equalsIgnoreCase("Expense")) {
                expenseCount++;
            }
        }

        System.out.println("Income Transactions: " + incomeCount);
        System.out.println("Expense Transactions: " + expenseCount);
        System.out.println("[Mock chart: Incomes vs Expenses]");
    }
}

// Notification Class
class Notification implements INotificationService {
    @Override
    public void sendBudgetAlert(String category) {
        System.out.println("Alert: Approaching or exceeding budget limit for " + category);
    }

    @Override
    public void sendGoalAlert(String goalName) {
        System.out.println("Alert: Goal " + goalName + " nearing completion!");
    }

    @Override
    public void sendSpendingPatternAlert() {
        System.out.println("Alert: Irregular spending pattern detected.");
    }
}

