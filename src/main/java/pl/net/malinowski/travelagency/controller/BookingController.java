package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Attr;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.data.entity.Attraction;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.logic.service.interfaces.AttractionService;
import pl.net.malinowski.travelagency.logic.service.interfaces.BookingService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;
import pl.net.malinowski.travelagency.logic.service.mail.EmailService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;
    private UserService userService;
    private EmailService emailService;
    private final AttractionService attractionService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService,
                             EmailService emailService, AttractionService attractionService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.emailService = emailService;
        this.attractionService = attractionService;
    }

    @ModelAttribute("attractionList")
    public List<Attraction> attractions() {
        return attractionService.findAll();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/book")
    public String processBooking(@Valid @ModelAttribute("search") TripSearch search, BindingResult result,
                                 Model model) {
        if (result.hasErrors())
            return "redirect:/trip/" + search.getTripId();

        User usr = userService.getLoggedInUser();
        Booking booking = bookingService.buildBooking(search, usr);
        model.addAttribute("booking", booking);
        model.addAttribute("user", usr);

        return "bookingAcceptTemplate";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/book/accept")
    public String acceptedBookingProcess(@Valid @ModelAttribute("booking") Booking booking, BindingResult result) {
        if (result.hasErrors())
            return "bookingAcceptTemplate";

        booking = bookingService.save(booking);
        emailService.sendBookingMessage(booking);

        return "redirect:/user/profile";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}/edit")
    public String showEditBookingForm(@PathVariable("id") Booking booking, Model model) {
        bookingService.checkIfOperationIsAvailable(booking);
        bookingService.checkPrivilegesForBooking(booking, userService.getLoggedInUser());
        model.addAttribute(booking);

        return "redirect:/";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable("id") Booking booking) {
        bookingService.cancelBooking(booking);
        emailService.sendCancelBookingMessage(booking);

        return "redirect:/user/profile";
    }
}
