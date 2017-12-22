package pl.net.malinowski.travelagency.logic.service.mail;

import pl.net.malinowski.travelagency.data.entity.User;

public interface EmailService {
    void sendWelcomeMessage(User user);
}
