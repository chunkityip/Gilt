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

    /**
     * buyGilt() method is to buy gilt base on the balance.
     * if the balance is smaller than balance, throw a CantAffordGiltException()
     */

    public void buyGilt(Gilt g) throws CantAffordGiltException{
        double price = pricingEngine.getPrice(g);
        // if the balance is smaller the price, the user can't afford to buy Gilt
        if (balance < price) throw new CantAffordGiltException();

        // If the user has enough balances, subtract the price of gilt from balance
        this.balance -= price;
        // Adding the Gilt g to user portfolio as List
        portfolio.add(g);
    }

    /**
     * sellGilt() is to sell Gilt as remove Gilt from portfolio
     * and add the price sell from Gilt into user balance
     */
    public void sellGilt(Gilt g) {
        double price = pricingEngine.getPrice(g);
        balance += price;
        portfolio.remove(g);
    }

    /**
     *  Adding user's coupon value into balance by iteration.
     *  If it has expired gilts, remove it from the portfolio.
     */
    public void tick() {
        // Creating ArrayList to store all the expiredGilts, so we can remove all at once later
        List<Gilt> expiredGilts = new ArrayList<>();
        for (Gilt g : portfolio) {
            // Adding the coupon+principal into balance
            balance += g.tick();
            // If Gilt is expired, store at ArrayList
            if (g.expired()) {
                expiredGilts.add(g);
            }
        }
        portfolio.removeAll(expiredGilts);
    }
}