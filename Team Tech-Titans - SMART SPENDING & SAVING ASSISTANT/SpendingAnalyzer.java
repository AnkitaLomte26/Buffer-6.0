package buffer;
import java.util.*;

import buffer.Expense;

public class SpendingAnalyzer {

    public static void detectOverspending(User user) {
        Map<String, Double> categoryMap = new HashMap<>();
        double totalIncome = user.getTotalIncome();
        double totalSpending = 0;

        // Sum up spending by category and total
        for (Expense e : user.getExpenses()) {
            double updated = categoryMap.getOrDefault(e.getCategory(), 0.0) + e.getAmount();
            categoryMap.put(e.getCategory(), updated);
            totalSpending += e.getAmount();
        }

        // Calculate average spending across categories
        double averageSpendingPerCategory = categoryMap.size() > 0 ? totalSpending / categoryMap.size() : 0;

        System.out.println("\nSmart Overspending Detection Report:");

        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            String category = entry.getKey();
            double amount = entry.getValue();

            boolean isHighComparedToAvg = amount > 1.5 * averageSpendingPerCategory;
            boolean isHighComparedToIncome = totalIncome > 0 && (amount > 0.3 * totalIncome);

            if (isHighComparedToAvg || isHighComparedToIncome) {
                System.out.printf("Overspending in '%s': ₹%.2f\n", category, amount);
                if (isHighComparedToAvg)
                    System.out.printf("   • This is above 1.5 your average category spend: ₹%.2f\n", averageSpendingPerCategory);
                if (isHighComparedToIncome)
                    System.out.printf("   • This is more than 30%% of your total income: ₹%.2f\n", totalIncome);
            }
        }
    }
}
