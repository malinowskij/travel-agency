package pl.net.malinowski.travelagency.controller.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.net.malinowski.travelagency.data.entity.Schedule;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Getter @Setter @NoArgsConstructor
public class ScheduleForm {
    @Valid
    private Schedule schedule;

    @Min(1)
    private int dayNo;
}
