package test.bank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author myeongju.jung
 */
@RunWith(Parameterized.class)
public class StandardIncomeTaxTest {

    @Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                {   0, 2008,    0},
                {1000, 2008,   80},
                {1200, 2008,   96},
                {1205, 2008,  204},
                {1206, 2008,  205},
                {4600, 2008,  782},
                {5000, 2008, 1300}
        });
    }

    private int income;
    private int year;
    private int taxAmount;

    public StandardIncomeTaxTest(int income, int year, int taxAmount) {
        this.income = income;
        this.year = year;
        this.taxAmount = taxAmount;
    }

    @Test
    public void testGetTaxAmount() throws Exception {
        StandardTax incomeTax = new StandardTax(this.year);
        assertEquals(this.taxAmount, incomeTax.getTaxAmount(this.income));
    }
}
