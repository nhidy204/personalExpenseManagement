package entities;

import interfaces.IFinancialGoalManagement;

import java.util.ArrayList;
import java.util.List;

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
