package gilts;

import gilts.AlreadyExpiredGiltException;
import gilts.Gilt;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;



class GiltTest {

    private Gilt gilt;
    @BeforeEach
    void setUp() {
        this.gilt = new Gilt(42.50, 1000, 0);
    }

    @Test
    public void testExceptionIsThrown() {
        AlreadyExpiredGiltException giltException =
                new AlreadyExpiredGiltException();

        // Trigger the exception
        assertThrows(AlreadyExpiredGiltException.class, () -> {
            throw giltException;
        });
    }

    @Test
    void testGetCouponPercent() {
        double expectedPercent = 0.0425;
        assertEquals(expectedPercent, gilt.getCouponPercent(),
                () -> "The coupon percent should be " + expectedPercent);
    }

    @Test
    void testGetCoupon() {
        double coupon = gilt.getCoupon();
        assertEquals(42.50, coupon);
    }

    @Test
    void testGetPrincipla() {
        double principla = gilt.getPrincipal();
        assertEquals(1000 , principla);
    }

    // Ensure that "ticking" a non-expiring gilt reduces the number of years
    // remaining on the Gilt and returns the coupon
    @Test
    void testTickReducesYearsRemaining() {
        Gilt gilt1 = new Gilt(42.50, 1000, 5);
        gilt1.tick();
        // After gilt1.tick(); , the years remaining should decrease by 1
        // 5 - 1 = 4
        assertEquals(4, gilt1.getYearsRemaining());
    }

    // Ensure that a Gilt can be created, and that its getCoupon, getCouponPercent, getPrincipal,
    // and getYearsRemaining methods work correctly.
    @Test
    void testGetters() {
        Gilt gilt = new Gilt(42.50, 1000, 5);
        assertEquals(42.50, gilt.getCoupon());
        assertEquals(1000, gilt.getPrincipal());
        assertEquals(5, gilt.getYearsRemaining());
    }

    @Test
    void testGiltConstructor() {
        double coupon = 42.50;
        int yearsRemaining = 5;

        Gilt gilt = new Gilt(coupon, yearsRemaining);

        assertAll(
                () -> assertEquals(coupon, gilt.getCoupon(),
                        "Coupon should be correct"),
                () -> assertEquals(100.0, gilt.getPrincipal(),
                        "Principal should be 100"),
                () -> assertEquals(yearsRemaining, gilt.getYearsRemaining(),
                        "Years remaining should be correct"),
                () -> assertEquals(coupon/100, gilt.getCouponPercent(), 0.0001,
                        "Coupon percent should be correct")
        );
    }

    // Ensure that a Gilt with 0 years remaining is expired
    // Ensure that "ticking" a gilt with 0 years left throws an AlreadyExpiredGiltException
    @Test
    void testTickForAlreadyExpiredGilt() {
        // Test with an already expired Gilt
        Gilt expiredGilt = new Gilt(42.50, 1000, 0);
        assertThrows(AlreadyExpiredGiltException.class,
                () -> expiredGilt.tick());
        assertTrue(() -> expiredGilt.expired());

    }

    // Ensure that a Gilt with more than 0 years remaining is NOT expired
    @Test
    void testTickForNotExpiredGilt() {
        // Test with non expired gilt
        Gilt notExpiredGilt = new Gilt(42.520 , 1000 , 5);
        assertDoesNotThrow(() -> notExpiredGilt.tick());
        assertFalse(() -> notExpiredGilt.expired());
    }

    // Ensure that "ticking" a gilt with 1 year left returns the coupon plus the principal
    @Test
    void testTickForOneYear() {
        // Test with a Gilt that has 1 year remaining
        Gilt gilt = new Gilt(42.50, 1000, 1);
        double expected = 42.50 + 1000; // coupon + principal
        assertEquals(expected, gilt.tick());
    }

    @Test
    void testTickForOneThousandYear() {
        // Test with a Gilt that has 1000 year remaining
        Gilt gilt = new Gilt(42.50, 1000, 1000);
        double expected = 42.50; // coupon only
        assertEquals(expected, gilt.tick());
    }
}