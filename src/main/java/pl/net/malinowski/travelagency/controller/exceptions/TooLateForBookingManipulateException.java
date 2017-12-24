package pl.net.malinowski.travelagency.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "too late for manipulation")
public class TooLateForBookingManipulateException extends RuntimeException {
}
