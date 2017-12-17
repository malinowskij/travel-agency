package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.net.malinowski.travelagency.data.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
