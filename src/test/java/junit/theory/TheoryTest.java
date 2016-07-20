package junit.theory;

import lombok.Value;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;

/**
 * @author myeongju.jung
 */
@RunWith(Theories.class)
public class TheoryTest {

    @DataPoint public static double FOUR = 4.0;
    @DataPoint public static double ZERO = 0.0;
//    @DataPoint public static double MINUS = -2.0; // 음수이기 때문에 오류 발생

    @Theory
    public void defineOfSqrt(double n) {
        assertEquals(n, Math.sqrt(n) * Math.sqrt(n), 0.01);
        assertTrue(Math.sqrt(n) >= 0);
    }

    @Theory
    public void multiplyIsInverseOfDivideWithInlineDataPoints(
            @TestedOn(ints = {0, 5, 10}) int amount,
            @TestedOn(ints = {0, 1, 2}) int m
    ) {
        assumeThat(m, not(0));
        assertThat(new Dollar(amount).times(m).divideBy(m).getAmount(), is(amount));
    }

    @Theory
    public void multiplyIsInverseOfDivideWithInlineDataPoints2(
            @Between(first = -100, last = 100) int amount,
            @Between(first = -100, last = 100) int m
    ) {
        assumeThat(m, not(0));
        assertThat(new Dollar(amount).times(m).divideBy(m).getAmount(), is(amount));
    }
}

@Value
class Dollar {
    private int amount;

    public Dollar times(int n) {
        return new Dollar(amount * n);
    }

    public Dollar divideBy(int n) {
        return new Dollar(amount / n);
    }
}


