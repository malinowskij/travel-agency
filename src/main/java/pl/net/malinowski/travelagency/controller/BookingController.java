package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.logic.service.interfaces.BookingService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;
import pl.net.malinowski.travelagency.logic.service.mail.EmailService;

import javax.validation.Valid;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService,
                             EmailService emailService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.emailService = emailService;
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
        User user = userService.getLoggedInUser();
        if (!bookingService.checkUserPrivilegesForBooking(booking, user))
            throw new AccessDeniedException(user.getFirstName() + " nie posiadasz uprawnień do oglądania tego zasobu!");

        model.addAttribute(booking);

        return "redirect:/";
    }
}
