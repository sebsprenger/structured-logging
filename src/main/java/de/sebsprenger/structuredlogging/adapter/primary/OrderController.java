package de.sebsprenger.structuredlogging.adapter.primary;

import de.sebsprenger.structuredlogging.application.OrderUseCases;
import de.sebsprenger.structuredlogging.domain.InvalidOrder;
import de.sebsprenger.structuredlogging.domain.NoSuchOrder;
import de.sebsprenger.structuredlogging.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
class OrderController {

    private final OrderUseCases applicationService;

    @GetMapping("/api/orders/{id}")
    @ResponseBody
    Order getEmployeesById(@PathVariable String id) throws NoSuchOrder, InvalidOrder {
        MDC.put("orderId", id);

        log.info("Received a new request");
        return applicationService.getOrder(id);
    }
}
