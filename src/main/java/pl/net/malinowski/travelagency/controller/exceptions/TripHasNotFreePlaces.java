package pl.net.malinowski.travelagency.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Trip is not available for booking")
public class TripHasNotFreePlaces extends RuntimeException {
}
