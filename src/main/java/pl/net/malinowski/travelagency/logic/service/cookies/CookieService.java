package pl.net.malinowski.travelagency.logic.service.cookies;

import pl.net.malinowski.travelagency.data.entity.Trip;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CookieService {
    String LAST_TRIPS_SEARCH = "tripsListCookie";

    Cookie addLastSearchTripsCookie(HttpServletRequest request, Trip trip);

    List<Trip> getLastWatchedTripsFromCookie(HttpServletRequest request);
}
