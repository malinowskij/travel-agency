package pl.net.malinowski.travelagency.logic.service.mail;

import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;

public interface EmailService {
    void sendWelcomeMessage(User user);

    void sendBookingMessage(Booking booking, String pdfPath);

    void sendCancelBookingMessage(Booking booking);
}
