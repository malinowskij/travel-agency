package pl.net.malinowski.travelagency.logic.service.interfaces;

import java.math.BigDecimal;

public interface StatService {
    Long registeredUsers();
    BigDecimal earnedMoney();
    Long bookedTrip();
    Long createdTrip();
}
