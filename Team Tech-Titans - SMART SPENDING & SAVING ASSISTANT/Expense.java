package buffer;

public class Expense {
    private String category;
    private double amount;
    private String note;
    private String date;
    private String paymentMethod;  // Assuming you want to track the payment method

    // Constructor with 5 parameters (category, amount, note, date, paymentMethod)
    public Expense(String category, double amount, String note, String date) {
        this.category = category;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.paymentMethod = "N/A"; // or null
    }
    

    // Getters and setters
    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
