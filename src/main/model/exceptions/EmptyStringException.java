package model.exceptions;

public class EmptyStringException extends IllegalArgumentException {
    public EmptyStringException() {
        super();
    }

    public EmptyStringException(String msg) {
        super(msg);
    }
}
