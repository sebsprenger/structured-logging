package de.sebsprenger.structuredlogging.domain;

public class InvalidOrder extends Exception {
    public InvalidOrder(String msg) {
        super(msg);
    }
}
