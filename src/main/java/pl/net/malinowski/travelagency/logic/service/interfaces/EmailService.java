package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.User;

public interface EmailService {
    void sendWelcomeMessage(User user);
}
