package example.vendingmachine;

import java.util.ArrayList;
import java.util.List;

/**
 * 잔돈 코인을 담기 위해 사용하는 코인 컨테이너
 * @author myeongju.jung
 */
public class CoinSet {
    public enum Coin {
        KRW500(500), KRW100(100), KRW50(50), KRW10(10)
        ;
        private final int value;
        Coin (int coin) {
            this.value = coin;
        }

        private int moneyToCoin(List<Integer> coinSet, int money) {
            while (money >= value) {
                money -= value;
                coinSet.add(value);
            }
            return money;
        }

        int getMoney() {
            return value;
        }
    }

    private List<Integer> coinSet = new ArrayList<>();

    public CoinSet(int money) {
        for (Coin coin : Coin.values()) {
            money = coin.moneyToCoin(coinSet, money);
        }
        if (money != 0) {
            throw new RuntimeException("Not supported money : " + money);
        }
    }

    public CoinSet(Coin... coins) {
        for (Coin coin : coins) {
            add(coin);
        }
    }

    public void add(Coin coin) {
        this.coinSet.add(coin.value);
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
