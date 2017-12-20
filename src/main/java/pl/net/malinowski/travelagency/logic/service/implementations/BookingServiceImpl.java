package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.data.repository.BookingRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.BookingService;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking save(Booking booking) {
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
        return null;
    }
}
