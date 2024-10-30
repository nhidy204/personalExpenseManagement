package entities;
import interfaces.INotificationService;

// Notification Class
public class Notification implements INotificationService {
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
