package example.vendingmachine;

import java.util.ArrayList;
import java.util.List;

/**
 * 잔돈 코인을 담기 위해 사용하는 코인 컨테이너
 * @author myeongju.jung
 */
public class CoinSet {
    private List<Integer> coinSet = new ArrayList<>();

    public CoinSet() {
    }

    public CoinSet(int money) {
        money = moneyToCoin(money, 500);
        money = moneyToCoin(money, 100);
        money = moneyToCoin(money, 50);
        money = moneyToCoin(money, 10);
    }

    private int moneyToCoin(int money, int coin) {
        while (money >= coin) {
            money -= coin;
            coinSet.add(coin);
        }
        return money;
    }

    public void add(int coin) {
        this.coinSet.add(coin);
    }

    @Override
    public boolean equals(Object coinSet) {
        if (!(coinSet instanceof CoinSet)) {
            return false;
        }
        return this.toString().equals(coinSet.toString());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Integer coin : this.coinSet) {
            builder.append(coin);
        }
        return builder.toString();
    }
}
