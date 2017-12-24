package pl.net.malinowski.travelagency.controller.validator.classes;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.net.malinowski.travelagency.controller.validator.annotations.FileExtension;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileExtensionValidator implements ConstraintValidator<FileExtension, MultipartFile> {
    private List<String> ext = new ArrayList<>();

    @Override
    public void initialize(FileExtension fileExtension) {
        ext.addAll(Arrays.asList(fileExtension.elements()));
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return ext.stream()
                .anyMatch(e ->
                        e.equalsIgnoreCase(
                                FilenameUtils.getExtension(file.getOriginalFilename())));
    }
}
