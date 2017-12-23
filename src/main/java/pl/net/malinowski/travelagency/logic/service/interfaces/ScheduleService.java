package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.controller.commands.ScheduleForm;
import pl.net.malinowski.travelagency.data.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule findOne(Long id);

    List<Schedule> findByTripId(Long tripId);

    Schedule save(Schedule schedule);

    Schedule buildSchedule(ScheduleForm scheduleForm);
}
