package gilts;

public interface GiltPricingEngine {
    double getPrice(Gilt g);
    double getYield(Gilt g);
}
