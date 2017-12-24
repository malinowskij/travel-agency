package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.controller.exceptions.TooLateForBookingManipulateException;
import pl.net.malinowski.travelagency.controller.exceptions.TripHasNotFreePlaces;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.Role;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.data.repository.BookingRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.BookingService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;
import pl.net.malinowski.travelagency.logic.util.DateUtil;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final TripService tripService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, TripService tripService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.tripService = tripService;
    }

    @PostConstruct
    public void init() {
        tripService.setBookingService(this);
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
        return new Booking(user, tripService.findById(search.getTripId()), search.getPeopleCount(), search.isAllInclusive());
    }

    @Override
    public void checkPrivilegesForBooking(Booking booking, User user) {
        if (user.getRoles().stream().noneMatch(r -> r.getName().equals(Role.Type.ROLE_ADMIN)) ||
                !booking.getCustomer().getId().equals(user.getId()))
            throw new AccessDeniedException(user.getFirstName() + " nie posiadasz uprawnień do oglądania tego zasobu!");
    }

    @Override
    public void checkIfOperationIsAvailable(Booking booking) {
        if (!DateUtil.getTodayFormatted().before(booking.getTrip().getStartDate()))
            throw new TooLateForBookingManipulateException();
    }

    @Override
    public Long countBookingsByTripId(Long tripId) {
        return bookingRepository.countByTripId(tripId);
    }

    @Override
    public void cancelBooking(Booking booking) {
        checkIfOperationIsAvailable(booking);
        checkPrivilegesForBooking(booking, userService.getLoggedInUser());
        bookingRepository.delete(booking);
    }
}
