package interfaces;

public interface IBudgetManagement {
    boolean setBudget(String category);
    void checkBudget(String category);
    void updateBudget(String category, double newLimit);
    void manageBudget(String budgetCategory, double limit);
}
