package test.bank;

/**
 * @author myeongju.jung
 */
public class Account {
    private int balance;

    public Account(int money) {
        this.balance = money;
    }

    public int getBalance() {
        return this.balance;
    }

    public void deposit(int money) {
        this.balance += money;
    }

    public void withDraw(int money) {
        this.balance -= money;
    }
}
