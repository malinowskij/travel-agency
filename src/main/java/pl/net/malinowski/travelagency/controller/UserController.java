package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.controller.commands.EditUserForm;
import pl.net.malinowski.travelagency.data.entity.Address;
import pl.net.malinowski.travelagency.data.entity.Country;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.logic.service.interfaces.AddressService;
import pl.net.malinowski.travelagency.logic.service.interfaces.CountryService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private CountryService countryService;
    private AddressService addressService;

    @Autowired
    public UserController(UserService userService, CountryService countryService, AddressService addressService) {
        this.userService = userService;
        this.countryService = countryService;
        this.addressService = addressService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "register";

        user = userService.save(user);
        return "redirect:/login";
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_ADMIN"})
    @GetMapping("/profile")
    public String userProfile(Model model) {
        model.addAttribute("user", userService.getLoggedInUser());
        return "userProfile";
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_ADMIN"})
    @GetMapping("/address")
    public String showAddressForm(Model model) {
        User user = userService.getLoggedInUser();
        if (user.getAddress() != null)
            model.addAttribute("address", user.getAddress());
        model.addAttribute("address", new Address());
        model.addAttribute("countries", countryService.findAll());
        return "addressForm";
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_ADMIN"})
    @PostMapping("/address")
    public String processAddress(@Valid @ModelAttribute("address") Address address, BindingResult result) {
        if (result.hasErrors())
            return "addressForm";

        address = addressService.save(address);
        User user = userService.getLoggedInUser();
        userService.updateAddress(address.getId(), user.getId());
        return "redirect:/user/profile";
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_ADMIN"})
    @GetMapping("/edit")
    public String showEditForm(Model model) {
        model.addAttribute("user", userService.mapUserToEditUserForm(userService.getLoggedInUser()));
        model.addAttribute("countries", countryService.findAll());
        return "editUserForm";
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_ADMIN"})
    @PostMapping("/edit")
    public String processEditForm(@Valid @ModelAttribute("user") EditUserForm form, BindingResult result) {
        if (result.hasErrors())
            return "editUserForm";
        userService.update(userService.mapEditUserFormToUser(form));
        return "redirect:/user/profile";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}
