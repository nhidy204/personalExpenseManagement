package entities;
import interfaces.IBudgetManagement;
import java.util.HashMap;
import java.util.Map;

// Budget Class
public class Budget implements IBudgetManagement {
    private final Map<String, Double> categoryBudgets = new HashMap<>();
    private final Map<String, Double> currentSpendings = new HashMap<>();
    private Double limit;

    @Override
    public boolean setBudget(String category) {
        categoryBudgets.put(category, limit);
        currentSpendings.put(category, 0.0);  // Start with 0 spending
        System.out.println("Budget set for " + category + ": $" + limit);
        return false;
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
        if (categoryBudgets.containsKey(budgetCategory)) {
            updateBudget(budgetCategory, limit);
        } else {
            setBudget(budgetCategory);
        }
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
