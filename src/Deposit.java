public class Deposit {
    private double amountOnAccount;

    public Deposit() {
        this.amountOnAccount = 1.0;
    }

    public double getAmountOnAccount() {
        return amountOnAccount;
    }

    public void setAmountOnAccount(double amountOnAccount) {
        this.amountOnAccount = amountOnAccount;
    }

    public double calculate(double a, double b, String o) {
        if (o.equals("add")) {
            return a + b;
        } else if (o.equals("withdraw")) {
            return a - b;
        } else {
            System.out.println("Something went wrong!");
            return 0;
        }
    }
}
