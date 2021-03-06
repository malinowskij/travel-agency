package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;

import java.util.List;

public interface BookingService {
    List<Booking> findAll();

    Booking save(Booking booking);

    Booking findById(Long id);

    List<Booking> findByUser(User user);

    Booking buildBooking(TripSearch search, User user);

    void checkIfOperationIsAvailable(Booking booking);

    void canBook(Booking booking);

    Long countBookingsByTripId(Long tripId);

    void cancelBooking(Booking booking);

    Booking update(Booking booking, int prevPeopleQuantity);

    Integer countReservationsByTripId(Long tripId);
}
