package buffer;

import java.util.Date;

public class Goal {
    private String name;
    private double targetAmount;
    private double savedSoFar;
    private Date startDate;
    private Date targetDate;
    private String priority;
    private String description;

    // Constructor with additional fields
    public Goal(String name, double targetAmount, Date startDate, Date targetDate, String priority, String description) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.savedSoFar = 0;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.priority = priority;
        this.description = description;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getSavedSoFar() {
        return savedSoFar;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    // Add to savings
    public void addToSavings(double amount) {
        this.savedSoFar += amount;
    }

    // Update goal's priority
    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Update goal's description
    public void setDescription(String description) {
        this.description = description;
    }
}
