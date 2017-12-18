package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.data.repository.BookingRepository;
import pl.net.malinowski.travelagency.data.repository.TripRepository;
import pl.net.malinowski.travelagency.data.repository.UserRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.StatService;

import java.math.BigDecimal;

@Service
public class StatServiceImpl implements StatService {

    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private TripRepository tripRepository;

    @Autowired
    public StatServiceImpl(UserRepository userRepository, TripRepository tripRepository,
                           BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public Long registeredUsers() {
        return userRepository.count();
    }

    @Override
    public BigDecimal earnedMoney() {
        return null;
    }

    @Override
    public Long bookedTrip() {
        return bookingRepository.count();
    }

    @Override
    public Long createdTrip() {
        return tripRepository.count();
    }
}
