package entities;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// Transaction Class
public class Transaction {
    private static int counter = 0;
    private final int id;
    private String type;  // Income or Expense
    private double amount;
    private String category;
    private final Date date;

    public Transaction(String type, double amount, String category) {
        this.id = ++counter;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = new Date();  // Current date
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return type.equals("Income") ? amount : -amount;
    }

    public boolean isInCurrentMonth() {
        LocalDate now = LocalDate.now();
        LocalDate transactionDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return now.getMonth() == transactionDate.getMonth() && now.getYear() == transactionDate.getYear();
    }


    public boolean isInCurrentYear() {
        LocalDate now = LocalDate.now(); //lấy date hiện tại under dạng đối tượng LocalDate
        LocalDate localDate = date.toInstant()/*dùng để đại diện cho 1 time cụ thể rõ từng hour, minute, second*/.atZone(ZoneId.systemDefault()).toLocalDate();
        //move ổi đối tượng Date sang LocalDate
        //1. move đối tượng Date thành Instant bằng phương thức toInstant()
        //2. sử dụng atZone(ZoneId.systemDefault()) để gán bùng time hiện tại
        //3. finally, move thành LocalDate
        return now.getYear() == localDate.getYear();
        //compare năm hiện tại với năm của đối tượng localDate
        //trả về true nếu năm hiện tại trùng với năm của localDate, ngc lại trả về false
    }

    @Override
    public String toString() {
        return "Transaction ID: " + id + ", Type: " + type + ", Amount: $" + amount + ", Category: " + category + ", Date: " + date;
    }


    public String getCategory() {
        return category;
    }
}
