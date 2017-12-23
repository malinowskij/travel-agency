package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.data.entity.Drive;
import pl.net.malinowski.travelagency.data.repository.DriveRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.DriveService;

import java.util.List;

@Service
public class DriveServiceImpl implements DriveService {

    private final DriveRepository driveRepository;

    @Autowired
    public DriveServiceImpl(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    @Override
    public List<Drive> findAll() {
        return driveRepository.findAll();
    }

    @Override
    public Drive findOne(Long id) {
        return driveRepository.findOne(id);
    }
}
