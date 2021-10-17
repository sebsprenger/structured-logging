package de.sebsprenger.structuredlogging.application;

import de.sebsprenger.structuredlogging.domain.InvalidOrder;
import de.sebsprenger.structuredlogging.domain.NoSuchOrder;
import de.sebsprenger.structuredlogging.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderUseCases {
    private final OrderRepo db;

    public Order getOrder(String id) throws NoSuchOrder, InvalidOrder {
        log.info("Retrieving order");
        Order order = db.getOrder(id);
        return order.validate();
    }
}
