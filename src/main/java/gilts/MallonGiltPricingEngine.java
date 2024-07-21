package gilts;

public class MallonGiltPricingEngine implements GiltPricingEngine {
    private double twoYear, fiveYear, tenYear, twentyYear;
    public MallonGiltPricingEngine(double twoYear, double fiveYear, double tenYear, double twentyYear) {
        this.twoYear = twoYear / 100;
        this.fiveYear = fiveYear / 100;
        this.tenYear = tenYear / 100;
        this.twentyYear = twentyYear / 100;
    }
    @Override
    public double getPrice(Gilt g) {
        double yield = getYield(g);

        return calculatePriceHelper(g.getCoupon() ,
                g.getPrincipal() ,
                g.getYearsRemaining() ,
                yield);
    }

    /**
     * A helper method to calculate the Price base on the following formula:
     * (2 * Years * Coupon) - (Years * Principal * Yield) + (2 * Principal)
     * --------------------------------------------------------------------
     *                        (Years * Yield Parent + 2)
     */

    private double calculatePriceHelper(double coupon, double principal, int yearsRemaining, double yield) {
        return ((2 * yearsRemaining * coupon) -
                (yearsRemaining * principal * yield) +
                (2 * principal)) /
                ((yearsRemaining * yield) + 2);
    }

    // getYield() is a method to determine Yield base on the YearsRemaining
    public double getYield(Gilt g) {
        int yearRemaining = g.getYearsRemaining();

        /**
         * If 1 or 2 years: twoYear
         * If 3 to 5 years: fiveYear
         * If 6 to 10 years: tenYear
         * else, twentyYear
         */
        return switch (yearRemaining) {
            case 1 , 2 -> twoYear;
            case 3 , 4 , 5 -> fiveYear;
            case 6 , 7 , 8 , 9 , 10 -> tenYear;
            default -> twentyYear;
        };
    }
}
