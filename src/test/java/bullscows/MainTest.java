package bullscows;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static bullscows.Main.grade;

public class MainTest {

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
}