package buffer;

import java.text.DecimalFormat;

public class ForecastEngine {

    public static void forecastNextMonth(User user) {
        double totalIncome = user.getTotalIncome();
        double totalExpense = user.getTotalExpense();
        
        // Forecast for next month (assuming income stays the same and expenses stay constant)
        double forecastedIncome = totalIncome;
        double forecastedExpense = totalExpense;

        // Add forecast for recurring expenses if applicable
        for (RecurringExpense recurringExpense : user.getRecurringExpenses()) {
            if (recurringExpense.isDue()) {
                forecastedExpense += recurringExpense.getAmount(); // Adding recurring expenses
            }
        }

        // Project savings (forecasted savings = income - expenses)
        double forecastedSavings = forecastedIncome - forecastedExpense;

        // Calculate goal progress (if the user has goals)
        double forecastedGoalProgress = 0;
        for (Goal goal : user.getGoals()) {
            long durationInDays = (goal.getTargetDate().getTime() - goal.getStartDate().getTime()) / (1000 * 60 * 60 * 24);
            if (durationInDays > 0) {
                double monthlySavingsRequired = goal.getTargetAmount() / (durationInDays / 30.0);  // Monthly savings required to meet target
                forecastedGoalProgress += monthlySavingsRequired; // Add the projected monthly savings for the goal
            }
        }

        // Define a DecimalFormatter to ensure proper formatting of numbers
        DecimalFormat formatter = new DecimalFormat("#,###.00");

        // Display forecast for next month with proper currency format
        System.out.println("\nForecast for Next Month:");
        System.out.printf("  Projected Income: ₹%s\n", formatter.format(forecastedIncome));
        System.out.printf("  Projected Expenses: ₹%s\n", formatter.format(forecastedExpense));
        System.out.printf("  Projected Savings: ₹%s\n", formatter.format(forecastedSavings));
        System.out.printf("  Projected Goal Progress: ₹%s\n", formatter.format(forecastedGoalProgress));

        if (forecastedSavings < 0) {
            System.out.println("Warning: You may be overspending next month! Consider reviewing your expenses.");
        }
    }

    public static double forecastExpenseOnly(User user) {
        double totalExpenses = user.getTotalExpense();
        int months = user.getExpenses().size() > 0 ? user.getExpenses().size() : 1; // Adjust if there are multiple months

        // Forecast based on average monthly expenses
        return totalExpenses / months; // Simple average monthly expense forecast
    }

    // You can also add methods for other types of forecasting if needed, such as for recurring income
}
