package interfaces;

public interface IBudgetManagement {
    void setBudget(String category, double limit);
    void checkBudget(String category);
    void updateBudget(String category, double newLimit);
    void manageBudget(String budgetCategory, double limit);
}
