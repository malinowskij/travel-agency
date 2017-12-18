package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();
    Country findById(Long id);
}
