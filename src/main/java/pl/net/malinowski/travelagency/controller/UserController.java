package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.controller.commands.EditUserForm;
import pl.net.malinowski.travelagency.data.entity.Address;
import pl.net.malinowski.travelagency.data.entity.Country;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.logic.service.interfaces.*;
import pl.net.malinowski.travelagency.logic.service.mail.EmailService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private CountryService countryService;
    private AddressService addressService;
    private BookingService bookingService;
    private EmailService emailService;

    @Autowired
    public UserController(UserService userService, CountryService countryService,
                          AddressService addressService, BookingService bookingService,
                          EmailService emailService) {
        this.userService = userService;
        this.countryService = countryService;
        this.addressService = addressService;
        this.bookingService = bookingService;
        this.emailService = emailService;
    }

    @ModelAttribute("countries")
    public List<Country> countryList() {
        return countryService.findAll();
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (!user.getPassword().equals(user.getConfirmPassword()))
            bindingResult.addError(new ObjectError("password", "Hasła muszą być identyczne"));
        if (bindingResult.hasErrors())
            return "register";

        user = userService.save(user);
        emailService.sendWelcomeMessage(user);

        return "redirect:/login";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/profile")
    public String userProfile(Model model) {
        User usr = userService.getLoggedInUser();
        model.addAttribute("user", usr);
        model.addAttribute("bookings", bookingService.findAll());
        return "userProfile";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/address")
    public String showAddressForm(Model model) {
        User user = userService.getLoggedInUser();
        if (user.getAddress() != null)
            model.addAttribute("address", user.getAddress());
        model.addAttribute("address", new Address());
        return "addressForm";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/address")
    public String processAddress(@Valid @ModelAttribute("address") Address address, BindingResult result) {
        if (result.hasErrors())
            return "addressForm";

        address = addressService.save(address);
        User user = userService.getLoggedInUser();
        userService.updateAddress(address.getId(), user.getId());
        return "redirect:/user/profile";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/edit")
    public String showEditForm(Model model) {
        model.addAttribute("user", userService.mapUserToEditUserForm(userService.getLoggedInUser()));
        return "editUserForm";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/edit")
    public String processEditForm(@Valid @ModelAttribute("user") EditUserForm form, BindingResult result) {
        if (!form.getPassword().equals(form.getConfirmPassword()))
            result.addError(new ObjectError("password", "Hasła muszą być identyczne"));
        if (!userService.checkEmailAvailability(form.getEmail()))
            result.addError(new ObjectError("email", "Email istnieje w serwisie!"));
        if (result.hasErrors())
            return "editUserForm";

        userService.save(userService.mapEditUserFormToUser(form));
        return "redirect:/user/profile";
    }
}
