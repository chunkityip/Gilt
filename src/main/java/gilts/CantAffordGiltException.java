package gilts;

public class CantAffordGiltException extends GiltException{
    public CantAffordGiltException(double amount, Gilt g) {
        super("Only have "+amount+", can't afford "+g);
    }

    public CantAffordGiltException() {
        super("Can't afford that gilt!");
    }
}