package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.net.malinowski.travelagency.data.entity.City;
import pl.net.malinowski.travelagency.data.entity.Country;
import pl.net.malinowski.travelagency.data.entity.State;
import pl.net.malinowski.travelagency.logic.service.interfaces.CityService;
import pl.net.malinowski.travelagency.logic.service.interfaces.CountryService;
import pl.net.malinowski.travelagency.logic.service.interfaces.StateService;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private CountryService countryService;
    private StateService stateService;
    private CityService cityService;

    @Autowired
    public AddressController(CountryService countryService, StateService stateService, CityService cityService) {
        this.countryService = countryService;
        this.stateService = stateService;
        this.cityService = cityService;
    }

    @GetMapping("/countries")
    public List<Country> countries() {
        return countryService.findAll();
    }

    @GetMapping("/country/{id}/states")
    public List<State> states(@PathVariable Long id) {
        return stateService.findByCountryId(id);
    }

    @GetMapping("/state/{id}/cities")
    public List<City> cities(@PathVariable Long id) {
        return cityService.findByStateId(id);
    }
}
