//SmartAdvisor class
package buffer;
public class SmartAdvisor {
    public static void giveAdvice(User user) {
        double income = user.getTotalIncome();
        double expense = user.getTotalExpense();

        if (expense > income * 0.8) {
            System.out.println("You are spending over 80% of your income. Consider reducing entertainment or food expenses.");
        } else if (user.getRemainingBudget() > 5000) {
            System.out.println("Great job! You have healthy savings this month. Consider investing.");
        } else {
            System.out.println("Try to save at least 30% of your income. Set clear goals and track categories closely.");
        }
    }
}
