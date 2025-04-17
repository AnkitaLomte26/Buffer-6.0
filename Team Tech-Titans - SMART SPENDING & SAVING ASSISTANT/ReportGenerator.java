//ReportGenerator class
package buffer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import buffer.Expense;
import buffer.ForecastEngine;

public class ReportGenerator {
    public static void generateMonthlyReport(User user, String monthYear) {
        String fileName = user.getUsername() + "_" + monthYear.replace("/", "") + "_Report.txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("===== MONTHLY FINANCE REPORT =====\n");
            writer.write("USER: " + user.getUsername() + "\n");
            writer.write("MONTH: " + monthYear + "\n\n");

            writer.write("TOTAL INCOME: ₹" + user.getTotalIncome() + "\n");
            writer.write("TOTAL EXPENSES: ₹" + user.getTotalExpense() + "\n");
            writer.write("REMAINING BUDGET: ₹" + user.getRemainingBudget() + "\n\n");

            writer.write("SAVINGS GOALS:\n");
            for (Goal goal : user.getGoals()) {
                writer.write("  • " + goal.getName() + "  ₹" + goal.getSavedSoFar() + " / ₹" + goal.getTargetAmount() + "\n");
            }

            writer.write("\nTop EXPENSE CATEGORIES:\n");
            HashMap<String, Double> categoryTotal = new HashMap<>();
            for (Expense e : user.getExpenses()) {
                categoryTotal.put(e.getCategory(), categoryTotal.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
            }

            for (Map.Entry<String, Double> entry : categoryTotal.entrySet()) {
                writer.write("  • " + entry.getKey() + "  ₹" + entry.getValue() + "\n");
            }

            writer.write("\n**SUGGESTED ADVICE**:\n");
            SmartAdvisor.giveAdvice(user);

            double forecast = ForecastEngine.forecastExpenseOnly(user);
            writer.write("  • Estimated expense for next month: ₹" + Math.round(forecast) + "\n");

            writer.write("\nTHANK YOU FOR USING BUGETBUDDY \n");

            System.out.println("Report generated: " + fileName);
        } catch (IOException e) {
            System.out.println(" Error generating report: " + e.getMessage());
        }
    }
}
