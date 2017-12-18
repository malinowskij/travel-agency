package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.Attraction;

import java.util.List;

public interface AttractionService {
    List<Attraction> findAll();
    Attraction findById(Long id);
}
