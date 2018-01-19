package pl.net.malinowski.travelagency.logic.service.cookies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import pl.net.malinowski.travelagency.data.entity.Trip;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CookieServiceImpl implements CookieService {

    private ObjectMapper mapper = new ObjectMapper();
    private final TripService tripService;

    @Autowired
    public CookieServiceImpl(TripService tripService) {
        this.tripService = tripService;
    }

    @Override
    public Cookie addLastSearchTripsCookie(HttpServletRequest request, Trip trip) {
        Cookie listOfTrips = getSpecifiedCookie(request, LAST_TRIPS_SEARCH);
        List<Long> tripsListCookie = new ArrayList<>();

        Cookie cookie = null;
        try {
            if (listOfTrips != null) {
                tripsListCookie = getTripsListFromCookie(listOfTrips);
                tripsListCookie = tripsListCookie.subList(0, tripsListCookie.size() >= 5 ? 5 : tripsListCookie.size());

                if (!tripsListCookie.contains(trip.getId()))
                    tripsListCookie.add(trip.getId());
            } else
                tripsListCookie.add(trip.getId());

            cookie = new Cookie(LAST_TRIPS_SEARCH, URLEncoder.encode(mapper.writeValueAsString(tripsListCookie), "UTF-8"));
            cookie.setMaxAge(60 * 24 * 60);
            cookie.setPath("/");
            cookie.setDomain("localhost");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    @Override
    public List<Trip> getLastWatchedTripsFromCookie(HttpServletRequest request) {
        Cookie listOfTrips = getSpecifiedCookie(request, LAST_TRIPS_SEARCH);

        List<Trip> trips = new ArrayList<>();

        if (listOfTrips != null) {
            try {
                List<Long> tripIds = getTripsListFromCookie(listOfTrips);
                tripIds.forEach(c -> trips.add(tripService.findById(c)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return trips;
    }

    private Cookie getSpecifiedCookie(HttpServletRequest request, String cookieName) {
        return WebUtils.getCookie(request, cookieName);
    }

    private List<Long> getTripsListFromCookie(Cookie cookie) throws IOException {
        return mapper.readValue(URLDecoder.decode(cookie.getValue(), "UTF-8"),
                new TypeReference<List<Long>>() {
                });
    }
}
