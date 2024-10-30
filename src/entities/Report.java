package entities;
import interfaces.IReportGeneration;
import java.util.List;

// Report Class
public abstract class Report implements IReportGeneration {

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
