public class Transaction {
    public String type;
    public double amount;
    public String category;
    public String date;

    public Transaction() {} // Required for Firebase

    public Transaction(String type, double amount, String category, String date) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
}
