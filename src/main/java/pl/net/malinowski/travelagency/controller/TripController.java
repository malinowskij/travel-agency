package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.data.entity.Country;
import pl.net.malinowski.travelagency.data.entity.Schedule;
import pl.net.malinowski.travelagency.data.entity.Trip;
import pl.net.malinowski.travelagency.logic.service.interfaces.CountryService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class TripController {

    private CountryService countryService;
    private TripService tripService;

    @Autowired
    public TripController(CountryService countryService, TripService tripService) {
        this.countryService = countryService;
        this.tripService = tripService;
    }

    @ModelAttribute("countries")
    public List<Country> countries() {
        return countryService.findAll();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/trip/creator")
    public String tripCreatorForm(Model model) {
        model.addAttribute("trip", new Trip());
        return "tripCreatorForm";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/admin/trip/creator")
    public String processTripCreatorForm(@Valid @ModelAttribute("trip") Trip trip, BindingResult result) {
        if (!tripService.validateDate(trip))
            result.addError(new ObjectError("startDate", "Niepoprawne daty!"));
        if (result.hasErrors())
            return "tripCreatorForm";

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}
