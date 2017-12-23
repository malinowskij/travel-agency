package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.data.entity.Attraction;
import pl.net.malinowski.travelagency.data.repository.AttractionRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.AttractionService;

import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService {

    private AttractionRepository attractionRepository;

    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    @Override
    public List<Attraction> findAll() {
        return attractionRepository.findAll();
    }

    @Override
    public Attraction findById(Long id) {
        return attractionRepository.findOne(id);
    }

    @Override
    public Attraction save(Attraction attraction) {
        return attractionRepository.save(attraction);
    }
}
