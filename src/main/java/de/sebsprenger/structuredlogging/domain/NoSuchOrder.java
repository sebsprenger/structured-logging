package de.sebsprenger.structuredlogging.domain;

public class NoSuchOrder extends Throwable {

    public NoSuchOrder(String id) {
        super("No order with id " + id + " found");
    }
}
