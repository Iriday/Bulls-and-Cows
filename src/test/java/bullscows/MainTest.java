package bullscows;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import static bullscows.Main.grade;
import static bullscows.Main.createInitialGameMessage;

public class MainTest {
    public final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";

    @Test
    public void testGrade() {
        // only unique digits
        assertArrayEquals(grade("1234", "1234"), new int[]{0, 4});
        assertArrayEquals(grade("1234", "5678"), new int[]{0, 0});
        assertArrayEquals(grade("1234", "4321"), new int[]{4, 0});
        assertArrayEquals(grade("1234", "1324"), new int[]{2, 2});
        assertArrayEquals(grade("0123456789", "0193857246"), new int[]{6, 4});
        // not unique digits
        assertArrayEquals(grade("1234", "1111"), new int[]{3, 1});
        assertArrayEquals(grade("1234", "1212"), new int[]{2, 2});
        assertArrayEquals(grade("9876543210", "9072222123"), new int[]{8, 2});
    }

    @Test
    public void testCreateInitialGameMessage() {
        assertEquals(createInitialGameMessage(10, SYMBOLS, 10), "The secret is prepared: ********** (0-9).");
        assertEquals(createInitialGameMessage(5, SYMBOLS, 11), "The secret is prepared: ***** (0-9, a-a).");
        assertEquals(createInitialGameMessage(3, SYMBOLS, 36), "The secret is prepared: *** (0-9, a-z).");
        assertEquals(createInitialGameMessage(12, SYMBOLS, 12), "The secret is prepared: ************ (0-9, a-b).");
        assertEquals(createInitialGameMessage(36, SYMBOLS, 36), "The secret is prepared: ************************************ (0-9, a-z).");
    }
}