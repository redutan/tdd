package test.bank;

/**
 * @author myeongju.jung
 */
public class StandardTax {

    private int year;

    public StandardTax(int year) {
        if (year <= 1900) {
            throw new RuntimeException("Not supported year : " + this.year);
        }
        this.year = year;
    }

    public int getTaxAmount(int income) {
        int taxRate = getTaxRate(income);
        return income * taxRate / 100;
    }

    private int getTaxRate(int income) {
        if (income < 0) {
            throw new RuntimeException("income must not be negative number : " + income);
        }
        if (income <= 1200)
            return 8;
        else if (income > 1200 && income <= 4600)
            return 17;
        else if (income > 4600 && income <= 88000)
            return 26;
        else
            return 35;
    }
}
