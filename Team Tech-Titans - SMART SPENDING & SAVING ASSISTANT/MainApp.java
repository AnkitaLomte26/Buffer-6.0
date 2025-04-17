package buffer;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) throws ParseException {
        System.out.println("\n\n\t\t\t\t\t\t\t -----------------------------------------------------");
        System.out.println("\t\t\t\t\t\t\t         SMART SPENDING & SAVING ASSISTANT");
        System.out.println("\t\t\t\t\t\t\t -----------------------------------------------------");

        Scanner sc = new Scanner(System.in);
        List<User> users = new ArrayList<>();
        User currentUser = null;

        while (true) {
            System.out.println("\n\n\t\t\t\t\t************ Welcome to BudgetBuddy AI ************\n");
            System.out.println("1. Signup\n2. Login\n3. Exit");
            System.out.print("> ");
            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) {
                System.out.print("Enter username: ");
                String u = sc.nextLine();

                System.out.println("Password must be 8-15 chars, include UPPER/lowercase, number, special char.");
                System.out.print("Set Password: ");
                String p = sc.nextLine();
                while (!isPasswordValid(p)) {
                    System.out.print("Invalid password. Re-enter: ");
                    p = sc.nextLine();
                }

                users.add(new User(u, p));
                System.out.println("Account created!");
            } else if (ch == 2) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();

                for (User user : users) {
                    if (user.getUsername().equals(u) && user.checkPassword(p)) {
                        currentUser = user;
                        break;
                    }
                }

                if (currentUser == null) {
                    System.out.println("Invalid login!");
                    continue;
                }

                currentUser.processRecurringExpenses();

                while (true) {
                    System.out.println("\n===== Menu =====");
                    System.out.println("1. Add Income\n2. Add Expense\n3. Add Goal\n4. View Summary\n5. Smart Advice");
                    System.out.println("6. Forecast Next Month\n7. Detect Overspending\n8. View Goals\n9. Add Recurring Expense\n10. Generate Report\n11. Logout");
                    System.out.print("> ");
                    int option = Integer.parseInt(sc.nextLine());

                    switch (option) {
                        case 1:
                            System.out.print("Source: ");
                            String source = sc.nextLine();
                            System.out.print("Amount: ");
                            double incomeAmt = Double.parseDouble(sc.nextLine());
                            System.out.print("Date (dd/mm/yyyy): ");
                            String idate = sc.nextLine();
                            currentUser.addIncome(new Income(source, incomeAmt, idate));
                            break;

                        case 2:
                            System.out.print("Category: ");
                            String cat = sc.nextLine();
                            System.out.print("Amount: ");
                            double expAmt = Double.parseDouble(sc.nextLine());
                            System.out.print("Payment Mode: ");
                            String paym = sc.nextLine();
                            System.out.print("Note: ");
                            String note = sc.nextLine();
                            System.out.print("Date (dd/mm/yyyy): ");
                            String edate = sc.nextLine();
                            currentUser.addExpense(new Expense(cat, expAmt, paym, note));
                            break;

                        case 3:
                            // Goal creation process with new attributes
                            System.out.print("Goal Name: ");
                            String gname = sc.nextLine();
                            System.out.print("Target Amount: ");
                            double target = Double.parseDouble(sc.nextLine());

                            // Input for additional attributes
                            System.out.print("Start Date (dd/MM/yyyy): ");
                            String startDateStr = sc.nextLine();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date startDate = (Date) dateFormat.parse(startDateStr);

                            System.out.print("Target Date (dd/MM/yyyy): ");
                            String targetDateStr = sc.nextLine();
                            Date targetDate = (Date) dateFormat.parse(targetDateStr);

                            System.out.print("Priority (High/Medium/Low): ");
                            String priority = sc.nextLine();

                            System.out.print("Description: ");
                            String description = sc.nextLine();

                            currentUser.addGoal(new Goal(gname, target, startDate, targetDate, priority, description));
                            break;

                        case 4:
                            System.out.printf("Income: %.2f Rs\n", currentUser.getTotalIncome());
                            System.out.printf("Expenses: %.2f Rs\n", currentUser.getTotalExpense());
                            System.out.printf("Remaining: %.2f Rs\n", currentUser.getRemainingBudget());
                            break;

                        case 5:
                            // Smart advice directly here
                            double totalIncome = currentUser.getTotalIncome();
                            double totalExpense = currentUser.getTotalExpense();
                            double remainingBudget = currentUser.getRemainingBudget();
                            double totalSavings = currentUser.getTotalSavings(); // Assuming there's a method to get total savings
                            double emergencyFund = currentUser.getEmergencyFund(); // Assuming there's a method for emergency fund
                            double goalSavings = currentUser.getTotalGoalSavings(); // Assuming the user tracks goal-related savings

                            System.out.println("\n===== Smart Financial Advice =====");

                            // Overspending check
                            if (totalExpense > totalIncome) {
                                System.out.println("Warning: You have overspent your budget! Consider reducing unnecessary expenses.");
                            } else {
                                System.out.println("Good job! You're staying within your budget.");
                            }

                            // Low savings check
                            if (totalSavings < 0.2 * totalIncome) {
                                System.out.println("Tip: Try to save more. Aiming for at least 20% of your income as savings is a good target.");
                            }

                            // Expense trends
                            for (Expense exp : currentUser.getExpenses()) {
                                if (exp.getAmount() > 0.1 * totalIncome) {
                                    System.out.println("Tip: Your " + exp.getCategory() + " expenses are higher than 10% of your income. Consider cutting back.");
                                }
                            }

                            // Emergency fund check
                            if (emergencyFund < 3 * totalExpense) {
                                System.out.println("Tip: It's advisable to build an emergency fund that can cover at least 3-6 months of expenses.");
                            }

                            // Goal progress
                            if (goalSavings < currentUser.getTotalGoalAmount() * 0.5) {
                                System.out.println("Reminder: You're behind on your savings goals. Try to save more to stay on track.");
                            } else if (goalSavings >= currentUser.getTotalGoalAmount()) {
                                System.out.println("Great job! You've met your savings goals.");
                            } else {
                                System.out.println("You're on track to meet your savings goals. Keep it up!");
                            }

                            // Remaining budget advice
                            if (remainingBudget < 0) {
                                System.out.println("Warning: You have a negative balance! Reevaluate your expenses.");
                            } else {
                                System.out.println("You still have a balance of Rs " + remainingBudget + ". Consider saving or investing it.");
                            }

                            break;

                        case 6:
                            ForecastEngine.forecastNextMonth(currentUser);
                            break;

                        case 7:
                            SpendingAnalyzer.detectOverspending(currentUser);
                            break;

                        case 8:
                            for (Goal g : currentUser.getGoals()) {
                                System.out.println("Goal: " + g.getName() + "Rs" + g.getSavedSoFar() + "Rs" + g.getTargetAmount());
                                System.out.println("Start Date: " + g.getStartDate());
                                System.out.println("Target Date: " + g.getTargetDate());
                                System.out.println("Priority: " + g.getPriority());
                                System.out.println("Description: " + g.getDescription());
                            }
                            break;

                        case 9:
                            System.out.println("Enter recurring expense details:");
                            System.out.print("Category: ");
                            String rcat = sc.nextLine();
                            System.out.print("Amount: ");
                            double ramt = Double.parseDouble(sc.nextLine());
                            System.out.print("Frequency (e.g., daily, weekly, monthly): ");
                            String frequency = sc.nextLine();
                            System.out.print("Due Date (dd/mm/yyyy): ");
                            String dueDate = sc.nextLine();

                            RecurringExpense recurringExpense = new RecurringExpense(rcat, ramt, frequency, dueDate);
                            currentUser.addRecurringExpense(recurringExpense);
                            System.out.println("Recurring expense added successfully!");
                            break;

                        case 10:
                            System.out.print("Enter Month & Year (MM/YYYY): ");
                            String my = sc.nextLine();
                            ReportGenerator.generateMonthlyReport(currentUser, my);
                            break;

                        case 11:
                            currentUser = null;
                            System.out.println("Logged out!");
                            break;
                    }

                    if (currentUser == null) break;
                }
            } else {
                System.out.println("Exiting...");
                break;
            }
        }
        sc.close();
    }

    public static boolean isPasswordValid(String password) {
        int minLength = 6;  // You said 8 in prompt, here it's 6
        int maxLength = 15;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
    
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
    
        return password.length() >= minLength && password.length() <= maxLength &&
               hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    } 
}

