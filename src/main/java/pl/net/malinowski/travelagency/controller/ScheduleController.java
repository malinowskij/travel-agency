package pl.net.malinowski.travelagency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.controller.commands.ScheduleForm;
import pl.net.malinowski.travelagency.data.entity.Schedule;
import pl.net.malinowski.travelagency.data.entity.Trip;
import pl.net.malinowski.travelagency.logic.service.interfaces.ScheduleService;
import pl.net.malinowski.travelagency.logic.service.interfaces.TripService;

import javax.validation.Valid;

@Controller
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final TripService tripService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, TripService tripService) {
        this.scheduleService = scheduleService;
        this.tripService = tripService;
    }


    @PostMapping("/admin/schedule")
    public String processScheduleCreation(@Valid @ModelAttribute("scheduleForm") ScheduleForm scheduleForm, Model model,
                                          BindingResult result) {
        if (result.hasErrors())
            return "tripScheduleReviewer";


        Schedule schedule = scheduleService.buildSchedule(scheduleForm);
        schedule = scheduleService.save(schedule);
        Trip trip = schedule.getTrip();
        trip.getSchedules().add(schedule);
        tripService.save(trip);

        log.info("SCHEDULE CREATED ID = " + schedule.getId() + " TO TRIP ID = " + scheduleForm.getSchedule().getTrip().getId());

        return "redirect:/admin/trip/creator/schedule/" + scheduleForm.getSchedule().getTrip().getId();
    }
}
