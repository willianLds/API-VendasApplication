package io.github.willianlds.exception;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(){
        super("Password invalid.");
    }
}
