import java.util.Date;

public class Expense {
    // Fields
    private final int id;
    private final Date date;
    private final double value;
    private final String merchant;

    // Constructor
    public Expense(int id, Date date, double value, String merchant) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.merchant = merchant;
    }

    // Methods
    public int getId() {
        return this.id;
    }

    public double getValue() {
        return this.value;
    }

    public Date getDate() {
        return this.date;
    }

    public String getMerchant() {
        return this.merchant;
    }

    @Override
    public String toString() {
        return "Expense [id=" + this.id + ", date=" + this.date + ", value=" + this.value + ", merchant=" + this.merchant + "]";
    }

    public String toCsv() {
        return this.id + "," + this.date + "," + this.value + "," + this.merchant;
    }

    public String toJSON() {
        return "{\"id\":" + this.id + ",\"date\":\"" + this.date + "\",\"value\":" + this.value + ",\"merchant\":\"" + this.merchant + "\"}";
    }
}
