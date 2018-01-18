package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.controller.exceptions.TooLateForBookingManipulateException;
import pl.net.malinowski.travelagency.controller.exceptions.TripHasNotFreePlaces;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.data.repository.BookingRepository;
import pl.net.malinowski.travelagency.logic.service.acl.AclManager;
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
    private final AclManager aclManager;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService,
                              TripService tripService, AclManager aclManager) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.tripService = tripService;
        this.aclManager = aclManager;
    }

    @PostConstruct
    public void init() {
        tripService.setBookingService(this);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking save(Booking booking) {
        if (!tripService.hasTripFreePlaces(booking.getTrip().getId(), booking.getPeopleQuantity())
                || booking.getTrip().getPeopleLimit() < booking.getPeopleQuantity())
            throw new TripHasNotFreePlaces(booking);
        booking.setBookingDate(new Date());
        booking = bookingRepository.save(booking);

        aclManager.buildFullPermission(Booking.class, booking.getId(),
                new PrincipalSid(userService.getLoggedInUser().getEmail()));

        return booking;
    }

    @Override
    public Booking update(Booking booking, int prevPeopleQuantity) {
        if (prevPeopleQuantity < booking.getPeopleQuantity())
            if (this.countReservationsByTripId(booking.getTrip().getId()) + (booking.getPeopleQuantity() - prevPeopleQuantity) >
                    booking.getTrip().getPeopleLimit())
                throw new TripHasNotFreePlaces(booking);

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
    public void checkIfOperationIsAvailable(Booking booking) {
        if (!DateUtil.getTodayFormatted().before(booking.getTrip().getStartDate()))
            throw new TooLateForBookingManipulateException();
    }

    @Override
    public void canBook(Booking booking) {
        if (DateUtil.getTodayFormatted().after(booking.getTrip().getStartDate()))
            throw new TooLateForBookingManipulateException();
    }

    @Override
    public Long countBookingsByTripId(Long tripId) {
        return bookingRepository.countByTripId(tripId);
    }

    @Override
    public void cancelBooking(Booking booking) {
        checkIfOperationIsAvailable(booking);
        bookingRepository.delete(booking);
        aclManager.removeFullPermissions(Booking.class, booking.getId(),
                new PrincipalSid(userService.getLoggedInUser().getEmail()));
    }

    @Override
    public Integer countReservationsByTripId(Long tripId) {
        return bookingRepository.sumPeopleQuantityByTripId(tripId);
    }
}
