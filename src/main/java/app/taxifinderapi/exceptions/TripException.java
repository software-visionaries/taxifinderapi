package app.taxifinderapi.exceptions;

public class TripException extends RuntimeException{
    public TripException(String message) {
        super(message);
    }
}
