package example.vendingmachine;

/**
 * @author myeongju.jung
 */
public class VendingMachine {
    private int changeAmount;

    public void putCoin(int coin) {
        this.changeAmount += coin;
    }

    public int getChangeAmount() {
        return this.changeAmount;
    }

    public void selectDrink(Drink drink) {
        changeAmount -= drink.getPrice();
    }

    public CoinSet getChangeCoinSet() {
        return new CoinSet(changeAmount);
    }
}
