package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "SELECT countries.* FROM countries " +
            "LEFT JOIN trips ON countries.id = trips.destination_country_id " +
            "WHERE trips.destination_country_id = countries.id" +
            " GROUP BY countries.id", nativeQuery = true)
    List<Country> findAvailableCountries();
}
