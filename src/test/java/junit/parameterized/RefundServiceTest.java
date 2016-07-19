package junit.parameterized;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * 가격 별 취소수수료
 * 0 ~ 9999 : 0%
 * 10000 ~ 49999 : 10%
 * 50000 ~ : 20%
 */
@RunWith(Parameterized.class)   // 파라메터화된 테스트를 위한 선언
public class RefundServiceTest {

    // 파라미터들 제공 메소드 : static 이면서 Collection을 반환해야한다. 경계영역이 잘 설정되어야함.
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { 9999L,     0L},
                {10000L,  1000L},
                {49999L,  4999L},
                {50000L, 10000L}
        });
    }

    // @Parameter로 주입 시 public 으로 선언되어야 한다.
    @Parameter(0)   // data() 항 항목의 첫번째 인자
    public long amount;
    @Parameter(1)   // data() 항 항목의 두번째 인자
    public long refundFee;

    Order order;

    @Before
    public void setUp() throws Exception {
        order = new Order(amount);
    }

    @Test
    public void testGetRefundFee() throws Exception {
        RefundService refundService = new RefundService();
        long refundFee = refundService.getRefundFee(order);
        assertThat(refundFee, is(this.refundFee));
    }
}

class Order {
    private long amount;

    public Order(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must not be negative number : " + amount);
        }
        this.amount = amount;
    }

    public long getAmount() {
        return this.amount;
    }
}

class RefundService {
    public long getRefundFee(Order order) {
        long amount = order.getAmount();
        if (amount < 10000L)
            return 0;
        else if (amount < 50000L)
            return amount * 10 / 100;
        else
            return amount * 20 / 100;
    }
}
