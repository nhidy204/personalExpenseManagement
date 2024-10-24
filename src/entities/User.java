package entities;


import interfaces.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

// entities.User Class
public abstract class User implements IUserManagement, ITransactionManagement, IBudgetManagement, IFinancialGoalManagement, IReportGeneration {
    private final String username;
    private final String password;
    private double balance;
    private final List<Transaction> transactions;
    public final Budget budgetManager = new Budget();  // To handle budgets
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
        System.out.println("entities.User registered: " + username);
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
        // You might want to implement the entities.Report generation in a more detailed manner.
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

        System.out.println("entities.Transaction added.");
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
                System.out.println("entities.Transaction updated.");
                return;
            }
        }
        System.out.println("entities.Transaction not found.");
    }

    @Override
    public void deleteTransaction(int transactionId) {
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                transactions.remove(t);
                balance -= t.getAmount();  // Adjust balance
                System.out.println("entities.Transaction deleted.");
                return;
            }
        }
        System.out.println("entities.Transaction not found.");
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
        System.out.println("entities.Budget set for " + category + ": " + limit);
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



