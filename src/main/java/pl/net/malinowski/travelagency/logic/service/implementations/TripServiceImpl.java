package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.commands.TripAdvancedSearch;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.controller.exceptions.CannotMakeOperationOnTripException;
import pl.net.malinowski.travelagency.data.entity.Trip;
import pl.net.malinowski.travelagency.data.repository.TripRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.BookingService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;
import pl.net.malinowski.travelagency.logic.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private TripRepository tripRepository;
    private BookingService bookingService;

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

    @Override
    public List<Trip> searchForTrip(TripSearch search) {
        return tripRepository.findAvailableTrips(search.getStartDate(), search.getEndDate(),
                search.getCountry().getId(), search.getPeopleCount())
                .stream().filter(t -> t.getPeopleLimit() >= search.getPeopleCount())
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasTripFreePlaces(Long tripId, int peopleQuantity) {
        return tripRepository.hasFreePlaces(tripId, peopleQuantity).size() != 0;
    }

    @Override
    public Page<Trip> findByManyFields(TripAdvancedSearch search, Pageable pageable) {
        String from;
        String to;
        BigDecimal priceMin = search.getPriceMin();
        BigDecimal priceMax = search.getPriceMax();
        if (search.getStartDate() == null) from = DateUtil.formatDate(DateUtil.buildDate(1, 1, 1900));
        else from = DateUtil.formatDate(search.getStartDate());

        if (search.getEndDate() == null) to = DateUtil.formatDate(DateUtil.addDaysToDate(new Date(), 30));
        else to = DateUtil.formatDate(search.getEndDate());

        if (priceMin == null) priceMin = BigDecimal.valueOf(0.0);
        if (priceMax == null) priceMax = BigDecimal.valueOf(Double.MAX_VALUE);

        return tripRepository.findByManyFields("%" + search.getPhrase() + "%", from, to, priceMin, priceMax, pageable);
    }

    @Override
    public void cancelTrip(Trip trip) {
        this.checkTripBeforeOperation(trip);
        tripRepository.delete(trip);
    }

    @Override
    public void checkTripBeforeOperation(Trip trip) {
        if (!DateUtil.getTodayFormatted().before(trip.getStartDate()))
            throw new CannotMakeOperationOnTripException(trip);

        if (bookingService.countBookingsByTripId(trip.getId()) != 0)
            throw new CannotMakeOperationOnTripException(trip);
    }

    @Override
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
}
