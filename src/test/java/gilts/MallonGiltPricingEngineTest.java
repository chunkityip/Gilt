package gilts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Use twoYear if the yearsRemaining is 1 or 2
 * Use fiveYear if the yearsRemaining between 3 and 5
 * Use tenYear if the yearsRemaining between 6 and 10
 * Else, use twentyYear
 */

/**
 * assume a spread of yields of:
 * 2.35% / 100 as 0.0235
 * 3.02% as 0.0302
 * 3.56% as 0.0356
 * 4.06% as 0.0406
 */

/**
 * Price is
 * (2 * Years * Coupon) - (Years * Principal * Yield) + (2 * Principal)
 * --------------------------------------------------------------------
 *                        (Years * Yield Parent + 2)
 */

public class MallonGiltPricingEngineTest {
    @Mock
    static Gilt sharedGilt;

    private GiltPricingEngine pricer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        sharedGilt = mock(Gilt.class);
        pricer = new MallonGiltPricingEngine(2.35, 3.02, 3.56, 4.06);
    }


    /**
     * (2 * 2 * 42.50) - (2 * 1000 * 0.0446) + (2 * 1000)
     * -------------------------------------------------
     *               (2 * 0.0235)
     *
     * (170 - 89.2 + 2000)
     * ------------------        = 995.979 = 995.98
     *    2.098
     */
    @Test
    void twoYearGiltTest() {
        GiltPricingEngine pricer = new MallonGiltPricingEngine(4.46, 4.50, 4.11, 4.23);
        when(sharedGilt.getYearsRemaining()).thenReturn(2);
        when(sharedGilt.getCoupon()).thenReturn(42.50);
        when(sharedGilt.getCouponPercent()).thenReturn(4.25);
        when(sharedGilt.getPrincipal()).thenReturn(1000.0);

        double calculatedPrice = pricer.getPrice(sharedGilt);
        System.out.println("Calculated Price: " + calculatedPrice);

        assertEquals(995.98, calculatedPrice, 0.01);
    }

    /**
     * (2 * 5 * 10.25) - (5 * 1000 * 0.0302) + (2 * 1000)
     * --------------------------------------------------
     *              (5 * 0.0302) + 2
     *
     *  102 - 151 + 2000
     *  ----------------          =  1951.5 / 2.151 = 907.25
     *     0.151 + 2
     */
    @Test
    void fiveYearGiltTest() {
        when(sharedGilt.getYearsRemaining()).thenReturn(5);
        when(sharedGilt.getCoupon()).thenReturn(10.25);
        when(sharedGilt.getCouponPercent()).thenReturn(1.025);
        when(sharedGilt.getPrincipal() ).thenReturn(1000.0);
        assertEquals(907.25, pricer.getPrice(sharedGilt), 0.01);
    }


    /**
     * It should be 326.32, not 325.32
     * (2 * 10 * 22.0) - (10 * 200 * 0.0356) + (2 * 200)
     * -------------------------------------------------
     *         (10 * 0.0356) + 2
     *
     *
     *  440 - 71.2 + 400
     *  ----------------   = 769.8 / 2.356 = 326.31578 = 326.32
     *     0.356 + 2
     */
    @Test
    void tenYearGiltTest() {
        when(sharedGilt.getYearsRemaining()).thenReturn(10);
        when(sharedGilt.getCoupon()).thenReturn(22.0);
        when(sharedGilt.getCouponPercent()).thenReturn(11.0);
        when(sharedGilt.getPrincipal()).thenReturn(200.0);
        assertEquals(326.31, pricer.getPrice(sharedGilt), 0.01);
    }

    /**
     * (2 * 20 * 0.0) - (20 * 1000 * 0.0406) + (2 * 1000)
     * --------------------------------------------------   =  1188/ 2.812 = 422.475 = 422.48
     *           (20 * 0.0406) + 2
     */
    @Test
    void twentyYearGiltTest() {
        when(sharedGilt.getYearsRemaining()).thenReturn(20);
        when(sharedGilt.getCoupon()).thenReturn(0.0);
        when(sharedGilt.getCouponPercent()).thenReturn(0.0);
        when(sharedGilt.getPrincipal()).thenReturn(1000.0);
        assertEquals(422.48, pricer.getPrice(sharedGilt), 0.01);
    }

    /**
     * (2 * 1 * 1.00) - (1 * 100 * 0.0235) + (2 * 100)
     * ----------------------------------------------
     *             (1 * 0.0235) + 2
     *
     *
     *
     *             2 - 2.35 + 200
     *             -------------       = 199365 / 2.0235 = 986..567 = 98.67
     *                2.0235
     */
    @Test
    void oneYearGiltTest() {
        when(sharedGilt.getYearsRemaining()).thenReturn(1);
        when(sharedGilt.getCoupon()).thenReturn(1.00);
        when(sharedGilt.getCouponPercent()).thenReturn(1.00);
        when(sharedGilt.getPrincipal()).thenReturn(100.0);
        assertEquals(98.67, pricer.getPrice(sharedGilt), 0.01);
    }

    /**
     * (2 * 4 * 10.0) - (4 * 1000 * 0.0302) + (2 * 1000)
     * -------------------------------------------------
     *                (4 * 0.0302) + 2
     *
     *
     *                80 - 120.8 + 2000
     *                ----------------       = 1959.2 / 2.1208 = 923.80
     *                    0.1208 + 2
     */

    @Test
    void fourYearGiltTest() {
        when(sharedGilt.getYearsRemaining()).thenReturn(4);
        when(sharedGilt.getCoupon()).thenReturn(10.0);
        when(sharedGilt.getCouponPercent()).thenReturn(1.0);
        when(sharedGilt.getPrincipal()).thenReturn(1000.0);
        assertEquals(923.80, pricer.getPrice(sharedGilt), 0.01);
    }


    /**
     * (2 * 8 * 2550.0) - (8 * 100000 * 0.0356) + (2 * 100000)
     * ------------------------------------------------------
     *            (8 * 0.0356) + 2
     *
     *
     *  36000 - 284800 + 200000
     *  -----------------------       =  207520 / 2.2848 = 90826.33
     *      0.2848 + 2
     */
    @Test
    void eightYearGiltTest() {
        when(sharedGilt.getYearsRemaining()).thenReturn(8);
        when(sharedGilt.getCoupon()).thenReturn(2250.0);
        when(sharedGilt.getCouponPercent()).thenReturn(2.25);
        when(sharedGilt.getPrincipal()).thenReturn(100000.0);
        assertEquals(90826.33, pricer.getPrice(sharedGilt), 0.01);
    }

    /**
     * (2 * 15 * 0.50) - (15 * 50 * 0.0406) + (2 * 50)
     * ------------------------------------------------
     *               (15 * 0.0406) + 2
     *
     *
     *
     *               15 - 30.45 + 100
     *               ---------------   = 84.55 / 2.609 = 32.407 = 32.41
     *                  2.609
     */
    @Test
    void fifteenYearGiltTest() {
        when(sharedGilt.getYearsRemaining()).thenReturn(15);
        when(sharedGilt.getCoupon()).thenReturn(0.50);
        when(sharedGilt.getCouponPercent()).thenReturn(1.0);
        when(sharedGilt.getPrincipal()).thenReturn(50.0);
        assertEquals(32.41, pricer.getPrice(sharedGilt), 0.01);
    }
}