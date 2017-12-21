package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.logic.service.interfaces.CountryService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/")
@SessionAttributes({"availableCountries", "search"})
public class HomeController {

    private CountryService countryService;

    @Autowired
    public HomeController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("availableCountries", countryService.findAvailableCountries());
        if (!model.containsAttribute("search"))
            model.addAttribute("search", new TripSearch());
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
