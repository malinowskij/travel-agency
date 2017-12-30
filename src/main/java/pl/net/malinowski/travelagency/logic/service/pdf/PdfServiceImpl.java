package pl.net.malinowski.travelagency.logic.service.pdf;

import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.exceptions.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfServiceImpl implements PdfService {

    private final Path rootLocation = Paths.get("pdf-dir");

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
