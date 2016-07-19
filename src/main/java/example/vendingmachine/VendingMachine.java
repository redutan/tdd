package example.vendingmachine;

import example.vendingmachine.CoinSet.Coin;

/**
 * @author myeongju.jung
 */
public class VendingMachine {
    private int changeAmount;

    public void putCoin(Coin coin) {
        this.changeAmount += coin.getMoney();
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
