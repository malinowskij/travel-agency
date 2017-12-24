package pl.net.malinowski.travelagency.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.net.malinowski.travelagency.controller.commands.ScheduleForm;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.controller.commands.TripWithFile;
import pl.net.malinowski.travelagency.data.entity.*;
import pl.net.malinowski.travelagency.logic.service.file.FileService;
import pl.net.malinowski.travelagency.logic.service.interfaces.*;
import pl.net.malinowski.travelagency.logic.util.DateUtil;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"countries", "search", "featureList", "driveList", "attractionList"})
public class TripController {

    private CountryService countryService;
    private TripService tripService;
    private AttractionService attractionService;
    private FeatureService featureService;
    private FileService fileService;
    private DriveService driveService;

    @Autowired
    public TripController(CountryService countryService, TripService tripService,
                          AttractionService attractionService, FeatureService featureService,
                          FileService fileService, DriveService driveService) {
        this.countryService = countryService;
        this.tripService = tripService;
        this.attractionService = attractionService;
        this.featureService = featureService;
        this.fileService = fileService;
        this.driveService = driveService;
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

    @ModelAttribute("driveList")
    public List<Drive> drives() {
        return driveService.findAll();
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

        if (!form.getPhoto().isEmpty()) {
            String photoPath = fileService.store(form.getPhoto());
            trip.setPhotoPath(photoPath);
        }

        trip = tripService.save(trip);

        return "redirect:/admin/trip/creator/schedule/" + trip.getId();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/trip/creator/schedule/{tripId}")
    public String tripScheduleCreator(@PathVariable("tripId") Trip trip, Model model) {
        tripService.checkTripBeforeOperation(trip);
        model.addAttribute("trip", trip);
        model.addAttribute("daysCount", DateUtil.daysBetween(trip.getStartDate(), trip.getEndDate()));
        model.addAttribute("scheduleForm", new ScheduleForm());
        return "tripScheduleReviewer";
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

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/trip/cancel/{tripId}")
    public String cancelTrip(@PathVariable("tripId") Trip trip) {
        tripService.cancelTrip(trip);

        return "redirect:/admin/trip";
    }

    @GetMapping("/trip/lastMinute")
    public String lastMinuteTripList(Model model) {
        model.addAttribute("tripList", tripService.findLastMinuteOffers());
        return "foundedTripList";
    }
}
