package pl.net.malinowski.travelagency.logic.service.implementations;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import pl.net.malinowski.travelagency.data.entity.Trip;
import pl.net.malinowski.travelagency.data.repository.TripRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;

import java.util.Date;

@Service
public class TripServiceImpl implements TripService {

    private TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip findById(Long id) {
        return tripRepository.findOne(id);
    }

    @Override
    public boolean validateDate(Trip trip) {
        return trip.getStartDate().before(trip.getEndDate());
    }
}
