package pl.net.malinowski.travelagency.logic.service.interfaces;

import org.springframework.validation.BindingResult;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.data.entity.Trip;

import java.util.List;

public interface TripService {
    Trip save(Trip trip);
    Trip findById(Long id);
    boolean validateDate(Trip trip);
    List<Trip> searchForTrip(TripSearch search);
}
