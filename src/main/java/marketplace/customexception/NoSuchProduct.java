package marketplace.customexception;

public class NoSuchProduct extends RuntimeException {

    public NoSuchProduct(String message) {
        super(message);
    }
}
