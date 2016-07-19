package test.matcher;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * @author myeongju.jung
 */
public class IsNotANumber extends CustomTypeSafeMatcher<Double> {

    public IsNotANumber(String description) {
        super(description);
    }

    @Override
    protected boolean matchesSafely(Double number) {
        return number.isNaN();
    }

    @Factory
    public static <T> Matcher<Double> notANumber() {
        return new IsNotANumber("숫자가 아니어야함");
    }
}
