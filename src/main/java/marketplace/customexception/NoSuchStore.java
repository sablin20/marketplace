package marketplace.customexception;

public class NoSuchStore extends RuntimeException {

    public NoSuchStore(String message) {
        super(message);
    }
}
