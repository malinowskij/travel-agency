package pl.net.malinowski.travelagency.logic.service.interfaces;

import org.springframework.validation.BindingResult;
import pl.net.malinowski.travelagency.data.entity.Trip;

public interface TripService {
    Trip save(Trip trip);
    Trip findById(Long id);
    boolean validateDate(Trip trip);
}
