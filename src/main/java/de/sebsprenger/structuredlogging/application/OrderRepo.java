package de.sebsprenger.structuredlogging.application;

import de.sebsprenger.structuredlogging.domain.NoSuchOrder;
import de.sebsprenger.structuredlogging.domain.Order;

public interface OrderRepo {
    Order getOrder(String id) throws NoSuchOrder;
}
