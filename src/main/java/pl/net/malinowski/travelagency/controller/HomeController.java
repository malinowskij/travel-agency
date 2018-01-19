package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.data.entity.Trip;
import pl.net.malinowski.travelagency.logic.service.cookies.CookieService;
import pl.net.malinowski.travelagency.logic.service.interfaces.CountryService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"availableCountries", "search"})
public class HomeController {

    private CountryService countryService;
    private final TripService tripService;
    private final CookieService cookieService;


    @Autowired
    public HomeController(CountryService countryService, TripService tripService, CookieService cookieService) {
        this.countryService = countryService;
        this.tripService = tripService;
        this.cookieService = cookieService;
    }

    @GetMapping
    public String home(Model model, HttpServletRequest request) {

        model.addAttribute("availableCountries", countryService.findAvailableCountries());

        if (!model.containsAttribute("search"))
            model.addAttribute("search", new TripSearch());

        model.addAttribute("lastMinute", tripService.findLastMinuteOffers());
        model.addAttribute("lastWatched", cookieService.getLastWatchedTripsFromCookie(request));

        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
