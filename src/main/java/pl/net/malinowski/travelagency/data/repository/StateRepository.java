package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.State;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    @Query("SELECT s FROM State s WHERE s.country.id = ?1")
    List<State> findByCountryId(Long countryId);
}
