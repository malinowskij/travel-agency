package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.net.malinowski.travelagency.logic.service.interfaces.StatService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private StatService statService;

    @Autowired
    public AdminController(StatService statService) {
        this.statService = statService;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("registeredUsers", statService.registeredUsers());
        model.addAttribute("bookedTrip", statService.bookedTrip());
        model.addAttribute("createdTrip", statService.createdTrip());
        return "adminDashboard";
    }
}
