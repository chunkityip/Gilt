package dao;

public class UserData {
    private double coupon;
    private double principal;
    private int yearsRemaining;

    public UserData(double coupon, double principal, int yearsRemaining) {
        this.coupon = coupon;
        this.principal = principal;
        this.yearsRemaining = yearsRemaining;
    }

    public UserData() {
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

    @Override
    public String toString() {
        return "GiltData{" +
                "coupon=" + coupon +
                ", principal=" + principal +
                ", yearsRemaining=" + yearsRemaining +
                '}';
    }
}
