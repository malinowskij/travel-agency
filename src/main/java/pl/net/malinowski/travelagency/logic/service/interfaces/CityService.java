package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.City;

import java.util.List;

public interface CityService {
    City findById(Long id);
    List<City> findAll();
    List<City> findByStateId(Long stateId);
}
