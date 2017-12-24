package pl.net.malinowski.travelagency.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.net.malinowski.travelagency.data.entity.Trip;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Too late for changes!")
public class CannotMakeOperationOnTripException extends RuntimeException {
    private Trip trip;

    public CannotMakeOperationOnTripException(Trip trip) {
        this.trip = trip;
    }

    public Trip getTrip() {
        return trip;
    }
}
