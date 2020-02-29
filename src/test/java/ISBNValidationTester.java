
import com.kaushal.isbnValidator.ISBNValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ISBNValidationTester {

    @Test
    @DisplayName("should return true for valid ISBN number with - as group separator")
    public void basicTestWithHiphen() {
        boolean valid = ISBNValidator.validate("0-306-40615-2");
        assertTrue(valid);
    }

    @Test
    @DisplayName("should return true for valid ISBN number with whitespace as group separator")
    public void basicTestWithWhitespace() {
        boolean valid = ISBNValidator.validate("0 306 40615 2");
        assertTrue(valid);
    }

    @Test
    @DisplayName("should return true for valid ISBN number with no group separator")
    public void basicTestWithNoSeparator() {
        boolean valid = ISBNValidator.validate("0306406152");
        assertTrue(valid);
    }

    @Test
    @DisplayName("should return false for wrongly formatted ISBN number")
    public void testWronglyFormattedISBN() {
         Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
             ISBNValidator.validate("0-306-40615-21");
         });
         assertEquals("Number should be either 10 or 13 digit long.", exception.getMessage());
    }

    @Test
    @DisplayName("should return true for 13 digit valid ISBN number")
    public void testValid13DigitISBN() {
        boolean valid = ISBNValidator.validate("978-0-306-40615-7");
        assertTrue(valid);
    }

    @Test
    @DisplayName("should return false for 13 digit invalid  ISBN number")
    public void testInvalid13DigitISBN() {
        boolean valid = ISBNValidator.validate("978-0-306-40615-3");
        assertFalse(valid);
    }
}
