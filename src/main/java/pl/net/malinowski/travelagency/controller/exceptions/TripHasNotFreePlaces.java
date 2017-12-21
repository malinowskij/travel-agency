package pl.net.malinowski.travelagency.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.net.malinowski.travelagency.data.entity.Booking;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Trip is not available for booking")
public class TripHasNotFreePlaces extends RuntimeException {
    private Booking booking;

    public TripHasNotFreePlaces(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }
}
