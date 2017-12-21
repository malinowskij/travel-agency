package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.controller.commands.TripSearch;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.logic.service.interfaces.BookingService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;
    private UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
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

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/book/accept")
    public String acceptedBookingProcess(@Valid @ModelAttribute("booking") Booking booking, BindingResult result) {
        if (result.hasErrors())
            return "bookingAcceptTemplate";

        bookingService.save(booking);

        return "redirect:/user/profile";
    }
}
