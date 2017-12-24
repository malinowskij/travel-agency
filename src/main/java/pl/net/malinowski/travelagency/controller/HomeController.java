package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.logic.service.interfaces.CountryService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;

@Controller
@RequestMapping("/")
@SessionAttributes({"availableCountries", "search"})
public class HomeController {

    private CountryService countryService;
    private final TripService tripService;


    @Autowired
    public HomeController(CountryService countryService, TripService tripService) {
        this.countryService = countryService;
        this.tripService = tripService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("availableCountries", countryService.findAvailableCountries());
        if (!model.containsAttribute("search"))
            model.addAttribute("search", new TripSearch());

        model.addAttribute("lastMinute", tripService.findLastMinuteOffers());
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
