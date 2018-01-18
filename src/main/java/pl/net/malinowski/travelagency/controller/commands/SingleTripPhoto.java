package pl.net.malinowski.travelagency.controller.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import pl.net.malinowski.travelagency.controller.validator.annotations.FileExtension;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SingleTripPhoto {
    @NotNull
    private Long tripId;

    @NotNull
    @FileExtension(elements = {"jpg", "png", "jpeg", "gif", "bmp"})
    private MultipartFile file;
}
