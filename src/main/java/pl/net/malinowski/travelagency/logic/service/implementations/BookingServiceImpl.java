package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.controller.exceptions.TripHasNotFreePlaces;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.data.repository.BookingRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.BookingService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private TripService tripService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, TripService tripService) {
        this.bookingRepository = bookingRepository;
        this.tripService = tripService;
    }

    @Override
    public Booking save(Booking booking) {
        if (!tripService.hasTripFreePlaces(booking.getTrip().getId(), booking.getPeopleQuantity())
                || booking.getTrip().getPeopleLimit() < booking.getPeopleQuantity())
            throw new TripHasNotFreePlaces(booking);
        booking.setBookingDate(new Date());
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findOne(id);
    }

    @Override
    public List<Booking> findByUser(User user) {
        return bookingRepository.findByCustomer(user);
    }

    @Override
    public Booking buildBooking(TripSearch search, User user) {
        return new Booking(user, tripService.findById(search.getTripId()), search.getPeopleCount());
    }
}
