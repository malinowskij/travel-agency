package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.net.malinowski.travelagency.data.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
