package pl.net.malinowski.travelagency.logic.service.mail;

import org.springframework.scheduling.annotation.Async;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;

public interface EmailService {
    void sendWelcomeMessage(User user);

    void sendBookingMessage(Booking booking);
}
