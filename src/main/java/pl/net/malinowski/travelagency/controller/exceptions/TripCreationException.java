package pl.net.malinowski.travelagency.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.net.malinowski.travelagency.data.entity.Trip;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Date isn't fine")
public class TripCreationException extends RuntimeException {
}
