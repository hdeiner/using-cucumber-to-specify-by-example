package test.com.solutionsiq.creditcard;

import com.solutionsiq.creditcard.*;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class LuhnUnitTest {

    @Test
    public void testReverseCheckDigits() {
        Luhn luhn = new Luhn("4556737586899855");
        assertArrayEquals(new int[]{5,8,9,9,8,6,8,5,7,3,7,6,5,5,4}, luhn.getReversedCheckDigits());
    }

    @Test
    public void testProcessDigit1() {
        Luhn luhn = new Luhn("4556737586899855");
        assertEquals(1, luhn.processDigit(1));
    }

    @Test
    public void testProcessDigit2() {
        Luhn luhn = new Luhn("4556737586899855");
        assertEquals(8, luhn.processDigit(2));
    }

    @Test
    public void testProcessDigit4() {
        Luhn luhn = new Luhn("4556737586899855");
        assertEquals(9, luhn.processDigit(4));
    }

    @Test
    public void testProcessDigit9() {
        Luhn luhn = new Luhn("4556737586899855");
        assertEquals(5, luhn.processDigit(9));
    }

    @Test
    public void testValid() {
        Luhn luhn = new Luhn("4556737586899855");
        assertTrue(luhn.isValid());
    }

    @Test
    public void testInvalid() {
        Luhn luhn = new Luhn("1556737586899855");
        assertFalse(luhn.isValid());
    }

    @Test
    public void testValidMyCard() {
        Luhn luhn = new Luhn("5466576003262578");
        assertTrue(luhn.isValid());
    }

}
