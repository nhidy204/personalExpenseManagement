package entities;

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
