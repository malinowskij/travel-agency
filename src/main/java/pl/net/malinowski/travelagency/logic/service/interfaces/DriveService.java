package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.Drive;

import java.util.List;

public interface DriveService {
    List<Drive> findAll();
    Drive findOne(Long id);
}
