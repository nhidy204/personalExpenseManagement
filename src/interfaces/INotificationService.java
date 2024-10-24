package interfaces;

public interface INotificationService {
    void sendBudgetAlert(String category);
    void sendGoalAlert(String goalName);
    void sendSpendingPatternAlert();
}
