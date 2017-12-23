package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.commands.ScheduleForm;
import pl.net.malinowski.travelagency.data.entity.Schedule;
import pl.net.malinowski.travelagency.data.repository.ScheduleRepository;
import pl.net.malinowski.travelagency.logic.service.interfaces.ScheduleService;
import pl.net.malinowski.travelagency.logic.util.DateUtil;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule findOne(Long id) {
        return scheduleRepository.findOne(id);
    }

    @Override
    public List<Schedule> findByTripId(Long tripId) {
        return scheduleRepository.findByTripId(tripId);
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(checkIfExistsAndModify(schedule));
    }

    private Schedule checkIfExistsAndModify(Schedule schedule) {
        Schedule sch = scheduleRepository.findByDateAndTripId(
                schedule.getDate(), schedule.getTrip().getId());
        if (sch != null)
            schedule.setId(sch.getId());
        return schedule;
    }

    @Override
    public Schedule buildSchedule(ScheduleForm scheduleForm) {
        Date date = DateUtil.addDaysToDate(scheduleForm.getSchedule().getTrip().getStartDate(), scheduleForm.getDayNo() - 1);
        return new Schedule(date, scheduleForm.getSchedule().getName(), scheduleForm.getSchedule().getDescription(),
                scheduleForm.getSchedule().getTrip(), scheduleForm.getSchedule().getAttractions());
    }
}
