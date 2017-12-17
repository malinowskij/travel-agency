package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.net.malinowski.travelagency.data.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
