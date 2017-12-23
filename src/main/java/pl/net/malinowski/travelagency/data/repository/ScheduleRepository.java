package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.Schedule;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTripId(Long tripId);
    Schedule findByDateAndTripId(Date date, Long tripId);
}
