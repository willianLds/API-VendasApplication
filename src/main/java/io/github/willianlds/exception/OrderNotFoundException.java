package io.github.willianlds.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(){
        super("Order not found.");
    }
}
