package de.sebsprenger.structuredlogging.adapter.primary;

import de.sebsprenger.structuredlogging.domain.InvalidOrder;
import de.sebsprenger.structuredlogging.domain.NoSuchOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
class ExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchOrder.class)
    @ResponseBody
    String handle(NoSuchOrder e) {
        log.warn("User screwed up", e);
        return "ERROR: NOT FOUND";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InvalidOrder.class)
    @ResponseBody
    String handle(InvalidOrder e) {
        log.error("We screwed up", e);
        return "ERROR: internal";
    }
}
