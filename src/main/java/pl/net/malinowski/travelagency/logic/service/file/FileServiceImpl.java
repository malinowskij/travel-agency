package pl.net.malinowski.travelagency.logic.service.file;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.net.malinowski.travelagency.controller.exceptions.StorageException;
import pl.net.malinowski.travelagency.controller.exceptions.StorageNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {

    private final Path rootLocation = Paths.get("upload-dir");

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename = UUID.randomUUID() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(file.isEmpty())
                throw new StorageException("Failed to store empty file " + filename);
            if (filename.contains(".."))
                throw new StorageException("Cannot store file with relative path outside current dir");
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageNotFoundException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new StorageNotFoundException("Could not read file " + filename);
        } catch (MalformedURLException e) {
            throw new StorageNotFoundException("Could not read file " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public byte[] loadAsByteArray(String filename) {
        try {
            return IOUtils.toByteArray(this.loadAsResource(filename).getInputStream());
        } catch (IOException e) {
            throw new StorageNotFoundException("Could not load file " + filename, e);
        }
    }
}
