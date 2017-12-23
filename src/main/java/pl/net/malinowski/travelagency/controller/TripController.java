package pl.net.malinowski.travelagency.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.controller.commands.TripWithFile;
import pl.net.malinowski.travelagency.data.entity.*;
import pl.net.malinowski.travelagency.logic.service.file.FileService;
import pl.net.malinowski.travelagency.logic.service.interfaces.AttractionService;
import pl.net.malinowski.travelagency.logic.service.interfaces.CountryService;
import pl.net.malinowski.travelagency.logic.service.interfaces.FeatureService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"countries", "search"})
public class TripController {

    private CountryService countryService;
    private TripService tripService;
    private AttractionService attractionService;
    private FeatureService featureService;
    private FileService fileService;

    @Autowired
    public TripController(CountryService countryService, TripService tripService,
                          AttractionService attractionService, FeatureService featureService,
                          FileService fileService) {
        this.countryService = countryService;
        this.tripService = tripService;
        this.attractionService = attractionService;
        this.featureService = featureService;
        this.fileService = fileService;
    }

    @ModelAttribute("countries")
    public List<Country> countries() {
        return countryService.findAll();
    }

    @ModelAttribute("availableCountries")
    public List<Country> availCountries() {
        return countryService.findAvailableCountries();
    }

    @ModelAttribute("attractionList")
    public List<Attraction> attractions() {
        return attractionService.findAll();
    }

    @ModelAttribute("featureList")
    public List<Feature> features() {
        return featureService.findAll();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/trip/creator")
    public String tripCreatorForm(Model model) {
        model.addAttribute("tripWithFile", new TripWithFile());
        return "tripCreatorForm";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/admin/trip/creator")
    public String processTripCreatorForm(@Valid @ModelAttribute("tripWithFile") TripWithFile form, BindingResult result,
                                         HttpServletRequest request) {
        if (!tripService.validateDate(form.getTrip()))
            result.addError(new ObjectError("startDate", "Niepoprawne daty!"));
        if (result.hasErrors())
            return "tripCreatorForm";


        Trip trip = form.getTrip();

        String photoPath = fileService.store(form.getPhoto());
        trip.setPhotoPath(photoPath);
        trip = tripService.save(trip);
        return "redirect:/admin/trip/creator/schedule/" + trip.getId();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/trip/creator/schedule/{tripId}")
    public String tripScheduleCreator(@PathVariable Long tripId, Model model) {
        model.addAttribute("trip", tripService.findById(tripId));
        model.addAttribute("schedule", new Schedule());
        return "tripScheduleCreator";
    }

    @GetMapping("/trip/search")
    public String processTripSearching(@Valid @ModelAttribute("search") TripSearch search, Model model,
                                       BindingResult result) {
        if (result.hasErrors())
            return "index";

        model.addAttribute("tripList", tripService.searchForTrip(search));
        return "foundedTripList";
    }

    @GetMapping("/trip/{id}")
    public String findOneTrip(@PathVariable("id") Trip trip, Model model) {
        model.addAttribute("trip", trip);
        return "tripDetails";
    }
}
