package gilts;

public class Gilt {
    private double coupon; // Interest (absolute: i.e. how much are they being paid?)

    public double getCouponPercent() {
        return coupon/principal;
    }

    public double getCoupon() {
        return coupon;
    }

    public double getPrincipal() {
        return principal;
    }

    public int getYearsRemaining() {
        return yearsRemaining;
    }

    private double principal; // Face value
    private int yearsRemaining; // Years remaining on the bond

    public Gilt(double coupon, double principal, int yearsRemaining) {
        this.coupon = coupon;
        this.principal = principal;
        this.yearsRemaining = yearsRemaining;
    }

    public Gilt(double coupon, int yearsRemaining) {
        this(coupon, 100, yearsRemaining);
    }

    public double tick() {
        if (this.expired()) {
            throw new AlreadyExpiredGiltException(this);
        }
        this.yearsRemaining -= 1;
        if (this.yearsRemaining >= 1) {
            return this.coupon;
        } else {
            return this.coupon+this.principal;
        }
    }

    public boolean expired() {
        return this.yearsRemaining <= 0;
    }

}
