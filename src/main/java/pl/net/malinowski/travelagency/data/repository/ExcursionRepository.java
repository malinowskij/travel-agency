package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.net.malinowski.travelagency.data.entity.Excursion;

public interface ExcursionRepository extends JpaRepository<Excursion, Long> {
}
