package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.data.entity.Feature;
import pl.net.malinowski.travelagency.data.repository.FeatureRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.FeatureService;

import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    private FeatureRepository featureRepository;

    @Autowired
    public FeatureServiceImpl(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public List<Feature> findAll() {
        return featureRepository.findAll();
    }

    @Override
    public Feature findById(Long id) {
        return featureRepository.findOne(id);
    }
}
