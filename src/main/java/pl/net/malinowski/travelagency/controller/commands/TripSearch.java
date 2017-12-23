package pl.net.malinowski.travelagency.controller.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import pl.net.malinowski.travelagency.data.entity.Country;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class TripSearch {

    @NotNull
    private Country country;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @Max(4)
    @Min(1)
    @NotNull
    private int peopleCount;

    private Long tripId;

    private boolean allInclusive;
}
