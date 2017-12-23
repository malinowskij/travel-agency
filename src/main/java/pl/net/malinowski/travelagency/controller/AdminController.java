package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.net.malinowski.travelagency.controller.commands.PhraseSearch;
import pl.net.malinowski.travelagency.logic.service.interfaces.StatService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private StatService statService;
    private final UserService userService;

    @Autowired
    public AdminController(StatService statService, UserService userService) {
        this.statService = statService;
        this.userService = userService;
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
    @GetMapping("/user")
    public String userList(@ModelAttribute("phraseSearch") PhraseSearch phraseSearch,
                           Model model, Pageable pageable) {
        if (phraseSearch.getPhrase() == null) phraseSearch.setPhrase("");
        model.addAttribute("usersList", userService.findByPhrasePaginated(phraseSearch, pageable));
        model.addAttribute("phraseSearch", phraseSearch);
        return "usersList";
    }
}
