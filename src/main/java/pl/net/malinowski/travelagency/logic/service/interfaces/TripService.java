package pl.net.malinowski.travelagency.logic.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import pl.net.malinowski.travelagency.controller.commands.PhraseSearch;
import pl.net.malinowski.travelagency.controller.commands.TripAdvancedSearch;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.data.entity.Photo;
import pl.net.malinowski.travelagency.data.entity.Trip;

import java.util.List;

public interface TripService {
    Trip save(Trip trip);

    Trip findById(Long id);

    boolean validateDate(Trip trip);

    List<Trip> searchForTrip(TripSearch search);

    boolean hasTripFreePlaces(Long tripId, int peopleQuantity);

    Page<Trip> findByManyFields(TripAdvancedSearch search, Pageable pageable);

    void cancelTrip(Trip trip);

    void checkTripBeforeOperation(Trip trip);

    void setBookingService(BookingService bookingService);

    List<Trip> findLastMinuteOffers();

    Photo savePhoto(Photo photo);
}
