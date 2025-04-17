//Income class
package buffer;
public class Income {
    private String source;
    private double amount;
    private String date;

    public Income(String source, double amount, String date) {
        this.source = source;
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String toString() {
        return source + ": ₹" + amount + " on " + date;
    }
}