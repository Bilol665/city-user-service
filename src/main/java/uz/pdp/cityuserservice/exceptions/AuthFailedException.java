package uz.pdp.cityuserservice.exceptions;

public class AuthFailedException extends RuntimeException{
    public AuthFailedException(String message) {
        super(message);
    }
}
