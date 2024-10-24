package interfaces;

public interface IFinancialGoalManagement {
    void setGoal(String goalName, double targetAmount);
    void trackProgress(String goalName);
    void adjustGoal(String goalName, double newTargetAmount);
    void contributeToGoal(String goalName, double amount);
    void manageGoal(String goalName, double targetAmount);
}