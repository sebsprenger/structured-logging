package de.sebsprenger.structuredlogging.domain;

import lombok.Value;

@Value
public class Order {
    String orderNumber;
    long sumInCents;

    public Order validate() throws InvalidOrder {
        if (sumInCents <= 0) {
            throw new InvalidOrder("Sum " + sumInCents + " is <= 0");
        }
        return this;
    }
}
