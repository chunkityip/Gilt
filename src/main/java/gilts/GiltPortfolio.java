package gilts;

import java.util.ArrayList;
import java.util.List;

public class GiltPortfolio {
    public List<Gilt> getPortfolio() {
        return portfolio;
    }

    public double getBalance() {
        return balance;
    }

    private GiltPricingEngine pricingEngine;
    private List<Gilt> portfolio;
    private double balance;

    public GiltPortfolio(GiltPricingEngine pricingEngine, List<Gilt> portfolio, double balance) {
        this.pricingEngine = pricingEngine;
        this.portfolio = portfolio;
        this.balance = balance;
    }

    public GiltPortfolio(GiltPricingEngine pricingEngine, double balance) {
        this(pricingEngine, new ArrayList<>(), balance);
    }

    // buyGilt() method is to buy gilt base on the balance.
    // if the balance is smaller than balance, throw a CantAffordGiltException()
    public void buyGilt(Gilt g) throws CantAffordGiltException{
        double price = pricingEngine.getPrice(g);
        // if the balance is smaller the price, the user can't afford to buy Gilt
        if (balance < price) throw new CantAffordGiltException();

        // If the user has enough balances, subtract the price of gilt from balance
        this.balance -= price;
        // Adding the Gilt g to user portfolio as List
        portfolio.add(g);
    }

    public void sellGilt(Gilt g) {

    }

    public void tick() {

    }
}
