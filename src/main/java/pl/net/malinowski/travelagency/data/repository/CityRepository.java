package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.City;
import pl.net.malinowski.travelagency.data.entity.State;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.state.id = ?1")
    List<City> findByStateId(Long stateId);
}
