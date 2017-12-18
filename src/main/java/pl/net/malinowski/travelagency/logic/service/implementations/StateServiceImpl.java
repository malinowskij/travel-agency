package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.data.entity.State;
import pl.net.malinowski.travelagency.data.repository.StateRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.StateService;

import java.util.List;

@Service
public class StateServiceImpl implements StateService{

    private StateRepository stateRepository;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public List<State> findAll() {
        return stateRepository.findAll();
    }

    @Override
    public State findById(Long id) {
        return stateRepository.findOne(id);
    }

    @Override
    public List<State> findByCountryId(Long countryId) {
        return stateRepository.findByCountryId(countryId);
    }
}
