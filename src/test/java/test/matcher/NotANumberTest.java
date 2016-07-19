package test.matcher;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static test.matcher.IsNotANumber.notANumber;

/**
 * @author myeongju.jung
 */
public class NotANumberTest {

    @Test
    public void testSquareRootOfMinusOneIsNotANumber() throws Exception {
        assertThat(Math.sqrt(-1), is(notANumber()));
    }

    @Test
    public void testSquare() throws Exception {
        // 숫자가 아니여야함 오류 발생
        assertThat(Math.sqrt(2), is(notANumber()));
    }
}
