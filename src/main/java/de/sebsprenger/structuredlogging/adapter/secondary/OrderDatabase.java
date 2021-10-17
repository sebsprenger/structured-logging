package de.sebsprenger.structuredlogging.adapter.secondary;

import de.sebsprenger.structuredlogging.application.OrderRepo;
import de.sebsprenger.structuredlogging.domain.NoSuchOrder;
import de.sebsprenger.structuredlogging.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static de.sebsprenger.structuredlogging.util.JsonAttributeMarker.put;
import static net.logstash.logback.argument.StructuredArguments.*;
import static net.logstash.logback.marker.Markers.append;

@Repository
@Slf4j
class OrderDatabase implements OrderRepo {

    private final Map<String, Order> db;

    OrderDatabase() {
        this.db = new HashMap<>();

        Order order1 = new Order("123456", 14400);
        Order order2 = new Order("123457", 25680);
        Order order3 = new Order("123458", 0);

        db.put(order1.getOrderNumber(), order1);
        db.put(order2.getOrderNumber(), order2);
        db.put(order3.getOrderNumber(), order3);
    }

    @Override
    public Order getOrder(String id) throws NoSuchOrder {
        log.info("Fetching order from the database");

        Instant start = Instant.now();
        Order result = retrieve(id);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toNanos();

        int imaginaryDbShardId = 17;
        printLogStatement(imaginaryDbShardId, timeElapsed);

        if (result == null) {
            throw new NoSuchOrder(id);
        }
        return result;
    }

    private Order retrieve(String id) {
        Order result = db.get(id);
        try {
            Thread.sleep(0, new Random().nextInt(150));
        } catch (InterruptedException e) {
            log.error("The sleep didn't work", e);
        }
        log.info(append("order", result),
                "Fetched order");
        return result;
    }

    private void printLogStatement(int dbShardId, long timeElapsed) {
        // prints "It took 123456ns to fetch order from DB-shard 17 (value)"
        // + and adds properties to JSON output
        log.info("It took {}ns to fetch order from DB-shard {} (value)",
                value("timeElapsed", timeElapsed),
                v("dbShardId", dbShardId)
        );

        // prints "It took timeElapsed=123456ns to fetch order from DB-shard dbShardI=17 (keyValue)"
        // + and adds properties to JSON output
        log.info("It took {}ns to fetch order from DB-shard {} (keyValue)",
                keyValue("timeElapsed", timeElapsed),
                kv("dbShardId", dbShardId)
        );

        // prints "Measured db access (kv+v)"
        // + and adds properties to JSON output
        // but my IDE highlights "More arguments provided (2) than placeholders specified (0)"
        log.info("Measured db access (kv+v)",
                kv("timeElapsed", timeElapsed),
                v("dbShardId", dbShardId)
        );

        // prints "Measured db access (append)"
        // + and adds properties to JSON output
        // clean IDE, but append-interface is ugly
        log.info(append("timeElapsed", timeElapsed).
                        and(append("dbShardId", dbShardId)),
                "Measured db access (append)"
        );

        // prints "Measured db access (customer appender)"
        // + and adds properties to JSON output
        // clean IDE and better interface, but custom code
        log.info(put("timeElapsed", timeElapsed).
                        and("dbShardId", dbShardId),
                "Measured db access (custom appender)"
        );
    }
}
