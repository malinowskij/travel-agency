package pl.net.malinowski.travelagency.controller.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import pl.net.malinowski.travelagency.data.entity.Trip;

import javax.validation.Valid;

@Getter @Setter @NoArgsConstructor
public class TripWithFile {
    @Valid
    private Trip trip;

    private MultipartFile photo;
}
