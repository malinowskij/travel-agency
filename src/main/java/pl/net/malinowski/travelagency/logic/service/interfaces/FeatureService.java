package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.Feature;

import java.util.List;

public interface FeatureService {
    List<Feature> findAll();
    Feature findById(Long id);
}
