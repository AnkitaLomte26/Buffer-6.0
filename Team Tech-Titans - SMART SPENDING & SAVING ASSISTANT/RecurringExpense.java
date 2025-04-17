package buffer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecurringExpense {
    private String category;
    private double amount;
    private String frequency;
    private LocalDate dueDate;

    // Constructor
    public RecurringExpense(String category, double amount, String frequency, String dueDateStr) {
        this.category = category;
        this.amount = amount;
        this.frequency = frequency;
        // Convert string to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dueDate = LocalDate.parse(dueDateStr, formatter);
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getFrequency() {
        return frequency;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isDue() {
        LocalDate today = LocalDate.now();
        return today.getDayOfMonth() == dueDate.getDayOfMonth() &&
               today.getMonthValue() == dueDate.getMonthValue();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Category: " + category + ", Amount: ₹" + amount + ", Frequency: " + frequency + ", Due Date: " + dueDate.format(formatter);
    }
}
