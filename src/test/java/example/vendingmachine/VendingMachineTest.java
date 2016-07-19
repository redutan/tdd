package example.vendingmachine;

import org.junit.Test;

import static example.vendingmachine.CoinSet.Coin.KRW100;
import static example.vendingmachine.CoinSet.Coin.KRW500;
import static org.junit.Assert.assertEquals;

/**
 * @author myeongju.jung
 */
public class VendingMachineTest {
    // 잔액 확인
    @Test
    public void testGetChangeAmount() throws Exception {
        VendingMachine machine = new VendingMachine();

        machine.putCoin(KRW100);   // 동전 100원 투입
        assertEquals("투입금액 100원", 100, machine.getChangeAmount());

        machine.putCoin(KRW500);   // 동전 500원 투입
        assertEquals("투입금액 500원", 600, machine.getChangeAmount());
    }

    // 거스름돈 50
    @Test
    public void testReturnChangeCoinSet_oneCoin_50() throws Exception {
        VendingMachine machine = new VendingMachine();
        machine.putCoin(KRW100);
        machine.putCoin(KRW100);
        machine.putCoin(KRW500);
        machine.selectDrink(new Drink("Cola", 650));

        CoinSet expectedCoinSet = new CoinSet(50);    // 코인 컨테이너 클래스
        assertEquals("700원 투입 후 650원 음료 선택", expectedCoinSet, machine.getChangeCoinSet());
    }


    // 거스름돈 180
    @Test
    public void testReturnChangeCoinSet_coins_180() throws Exception {
        VendingMachine machine = new VendingMachine();
        machine.putCoin(KRW100);
        machine.putCoin(KRW100);
        machine.putCoin(KRW500);
        machine.selectDrink(new Drink("Soda", 520));

        CoinSet expectedCoinSet = new CoinSet(180);
        assertEquals("700원 투입 후 520원 음료 선택", expectedCoinSet, machine.getChangeCoinSet());
    }
}
