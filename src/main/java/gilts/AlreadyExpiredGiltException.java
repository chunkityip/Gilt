package gilts;

public class AlreadyExpiredGiltException extends GiltException{
    public AlreadyExpiredGiltException() {
        super("gilts.Gilt has already expired.");
    }

    public AlreadyExpiredGiltException(Gilt g) {
        super("Already expired gilt: " + g);
    }
}

