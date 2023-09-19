package marketplace.customexception;

public class NoSuchClient extends RuntimeException {

    public NoSuchClient(String message) {
        super(message);
    }
}
