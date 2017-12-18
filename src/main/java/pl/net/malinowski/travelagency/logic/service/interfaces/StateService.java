package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.State;

import java.util.List;

public interface StateService {
    List<State> findAll();
    State findById(Long id);
    List<State> findByCountryId(Long countryId);
}
