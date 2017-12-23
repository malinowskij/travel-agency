package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.controller.commands.PhraseSearch;
import pl.net.malinowski.travelagency.controller.commands.TripAdvancedSearch;
import pl.net.malinowski.travelagency.logic.service.interfaces.StatService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;

@Controller
@RequestMapping("/admin")
@SessionAttributes({"phraseSearchUser", "tripSearch"})
public class AdminController {

    private StatService statService;
    private final UserService userService;
    private final TripService tripService;

    @Autowired
    public AdminController(StatService statService, UserService userService, TripService tripService) {
        this.statService = statService;
        this.userService = userService;
        this.tripService = tripService;
    }

    @ModelAttribute("phraseSearchUser")
    public PhraseSearch phraseSearch() {
        return new PhraseSearch();
    }

    @ModelAttribute("tripSearch")
    public TripAdvancedSearch tripAdvancedSearch() {
        return new TripAdvancedSearch();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("registeredUsers", statService.registeredUsers());
        model.addAttribute("bookedTrip", statService.bookedTrip());
        model.addAttribute("createdTrip", statService.createdTrip());
        return "adminDashboard";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String userList(@ModelAttribute("phraseSearchUser") PhraseSearch phraseSearch,
                           Model model, Pageable pageable) {
        if (phraseSearch.getPhrase() == null) phraseSearch.setPhrase("");
        model.addAttribute("usersList", userService.findByPhrasePaginated(phraseSearch, pageable));
        return "usersList";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/trip", method = {RequestMethod.GET, RequestMethod.POST})
    public String tripList(@ModelAttribute("tripSearch") TripAdvancedSearch search,
                           Model model, Pageable pageable, BindingResult result) {
        if (result.hasErrors())
            return "tripList";

        if (search.getPhrase() == null) search.setPhrase("");

        model.addAttribute("tripList", tripService.findByManyFields(search, pageable));
        return "tripList";
    }
}
