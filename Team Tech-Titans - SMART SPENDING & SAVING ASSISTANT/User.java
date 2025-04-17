package buffer;
import java.text.SimpleDateFormat;
import java.util.*;

import buffer.Expense;

public class User {
    private String username;
    private String password;
    private List<Income> incomes;
    private Map<String, List<Expense>> categorizedExpenses;
    private Map<String, Goal> goals;
    private List<RecurringExpense> recurringExpenses;
    private double totalSavings; // Track total savings
    private double emergencyFund; // Track emergency fund

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.incomes = new ArrayList<>();
        this.categorizedExpenses = new HashMap<>();
        this.goals = new HashMap<>();
        this.recurringExpenses = new ArrayList<>();
        this.totalSavings = 0.0;
        this.emergencyFund = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String pwd) {
        return this.password.equals(pwd);
    }

    public void addIncome(Income income) {
        incomes.add(income);
    }

    public void addRecurringExpense(RecurringExpense recurringExpense) {
        recurringExpenses.add(recurringExpense);
    }

    public void processRecurringExpenses() {
        for (RecurringExpense recurringExpense : recurringExpenses) {
            if (recurringExpense.isDue()) {
                String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(recurringExpense.getDueDate());
                Expense expense = new Expense(
                        recurringExpense.getCategory(),
                        recurringExpense.getAmount(),
                        "Recurring Expense", // Note
                        dateStr
                );
                addExpense(expense);
                System.out.println("Added recurring expense: " + recurringExpense.getCategory() + " ₹" + recurringExpense.getAmount());
            }
        }
    }

    public void addExpense(Expense expense) {
        categorizedExpenses
                .computeIfAbsent(expense.getCategory(), k -> new ArrayList<>())
                .add(expense);
    }

    public void addGoal(Goal goal) {
        if (!goals.containsKey(goal.getName())) {
            goals.put(goal.getName(), goal);
        } else {
            System.out.println("Goal already exists! Use a different name or update the existing one.");
        }
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public List<Expense> getExpenses() {
        List<Expense> all = new ArrayList<>();
        for (List<Expense> list : categorizedExpenses.values()) {
            all.addAll(list);
        }
        return all;
    }

    public Collection<Goal> getGoals() {
        return goals.values();
    }

    public double getTotalIncome() {
        return incomes.stream().mapToDouble(Income::getAmount).sum();
    }

    public double getTotalExpense() {
        return getExpenses().stream().mapToDouble(Expense::getAmount).sum();
    }

    public double getRemainingBudget() {
        return getTotalIncome() - getTotalExpense();
    }

    public Map<String, List<Expense>> getCategorizedExpenses() {
        return categorizedExpenses;
    }

    public void displayTopSpendingCategories() {
        Map<String, Double> totals = new HashMap<>();
        for (Map.Entry<String, List<Expense>> entry : categorizedExpenses.entrySet()) {
            double sum = 0;
            for (Expense e : entry.getValue()) {
                sum += e.getAmount();
            }
            totals.put(entry.getKey(), sum);
        }

        PriorityQueue<Map.Entry<String, Double>> maxHeap =
                new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));

        maxHeap.addAll(totals.entrySet());

        System.out.println("Top Spending Categories:");
        int count = 0;
        while (!maxHeap.isEmpty() && count++ < 3) {
            Map.Entry<String, Double> entry = maxHeap.poll();
            System.out.println("  • " + entry.getKey() + ": ₹" + entry.getValue());
        }
    }

    // Get total savings (example method)
    public double getTotalSavings() {
        return totalSavings;
    }

    // Set total savings
    public void setTotalSavings(double totalSavings) {
        this.totalSavings = totalSavings;
    }

    // Get emergency fund (example method)
    public double getEmergencyFund() {
        return emergencyFund;
    }

    // Set emergency fund
    public void setEmergencyFund(double emergencyFund) {
        this.emergencyFund = emergencyFund;
    }

    // Get total goal savings (savings towards all goals)
    public double getTotalGoalSavings() {
        double goalSavings = 0.0;
        for (Goal goal : goals.values()) {
            goalSavings += goal.getSavedSoFar(); // Assuming Goal has getSavedSoFar()
        }
        return goalSavings;
    }

    // Get total goal amount (target amounts for all goals)
    public double getTotalGoalAmount() {
        double goalAmount = 0.0;
        for (Goal goal : goals.values()) {
            goalAmount += goal.getTargetAmount(); // Assuming Goal has getTargetAmount()
        }
        return goalAmount;
    }
    public List<RecurringExpense> getRecurringExpenses() {
        return recurringExpenses;
    }
    
}
