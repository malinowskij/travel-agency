package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.net.malinowski.travelagency.data.entity.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
