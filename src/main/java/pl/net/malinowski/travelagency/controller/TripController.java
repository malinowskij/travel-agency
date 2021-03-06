package pl.net.malinowski.travelagency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.controller.commands.ScheduleForm;
import pl.net.malinowski.travelagency.controller.commands.SingleTripPhoto;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.controller.commands.TripWithFile;
import pl.net.malinowski.travelagency.data.entity.*;
import pl.net.malinowski.travelagency.logic.service.cookies.CookieService;
import pl.net.malinowski.travelagency.logic.service.file.FileService;
import pl.net.malinowski.travelagency.logic.service.interfaces.*;
import pl.net.malinowski.travelagency.logic.util.DateUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes({"countries", "search", "featureList", "driveList", "attractionList"})
@Slf4j
public class TripController {

    private CountryService countryService;
    private TripService tripService;
    private AttractionService attractionService;
    private FeatureService featureService;
    private FileService fileService;
    private DriveService driveService;
    private final StateService stateService;
    private final CityService cityService;
    private final CookieService cookieService;

    @Autowired
    public TripController(CountryService countryService, TripService tripService,
                          AttractionService attractionService, FeatureService featureService,
                          FileService fileService, DriveService driveService, StateService stateService, CityService cityService, CookieService cookieService) {
        this.countryService = countryService;
        this.tripService = tripService;
        this.attractionService = attractionService;
        this.featureService = featureService;
        this.fileService = fileService;
        this.driveService = driveService;
        this.stateService = stateService;
        this.cityService = cityService;
        this.cookieService = cookieService;
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

        log.info("TRIP CREATED ID = " + trip.getId());

        return "redirect:/admin/trip/creator/schedule/" + trip.getId();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/trip/creator/schedule/{tripId}")
    public String tripScheduleCreator(@PathVariable("tripId") Trip trip, Model model) {
        tripService.checkTripBeforeOperation(trip);
        model.addAttribute("attraction", new Attraction());
        model.addAttribute("trip", trip);
        model.addAttribute("daysCount", DateUtil.daysBetween(trip.getStartDate(), trip.getEndDate()));
        model.addAttribute("scheduleForm", new ScheduleForm());
        return "tripScheduleReviewer";
    }

    @GetMapping("/trip/search")
    public String processTripSearching(@Valid @ModelAttribute("search") TripSearch search, Model model,
                                       BindingResult result, HttpServletResponse response, HttpServletRequest request) {
        if (result.hasErrors())
            return "index";

        model.addAttribute("tripList", tripService.searchForTrip(search));
        return "foundedTripList";
    }

    @GetMapping("/trip/{id}")
    public String findOneTrip(@PathVariable("id") Trip trip, Model model, HttpServletRequest request,
                              HttpServletResponse response) {

        Cookie cookie = cookieService.addLastSearchTripsCookie(request, trip);
        if (cookie != null)
            response.addCookie(cookie);


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

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/trip/edit/{id}")
    public String editTripForm(@PathVariable("id") Trip trip, Model model) {
        tripService.checkTripBeforeOperation(trip);
        TripWithFile twf = new TripWithFile();
        twf.setTrip(trip);
        model.addAttribute("states", stateService.findById(trip.getDestinationCity().getState().getId()));
        model.addAttribute("cities", cityService.findById(trip.getDestinationCity().getId()));
        model.addAttribute("tripWithFile", twf);
        return "tripCreatorForm";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/trip/photo/{id}")
    public String addPhotoForm(@PathVariable("id") Trip trip, Model model) {
        if (trip != null) {
            model.addAttribute(new SingleTripPhoto());
            model.addAttribute(trip);
            return "singlePhotoForm";
        }

        throw new AccessDeniedException("Deny for not exists trip");
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/admin/trip/photo")
    public String processPhotoForm(@Valid @ModelAttribute SingleTripPhoto tripPhoto, BindingResult result) {
        if (result.hasErrors())
            return "singlePhotoForm";

        Photo photo = new Photo(fileService.store(tripPhoto.getFile()), tripService.findById(tripPhoto.getTripId()));
        tripService.savePhoto(photo);

        return "redirect:/admin/trip";
    }
}
