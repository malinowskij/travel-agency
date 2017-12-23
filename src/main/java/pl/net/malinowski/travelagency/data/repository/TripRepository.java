package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.Trip;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface TripRepository extends PagingAndSortingRepository<Trip, Long> {
    @Query(value = "SELECT trips.* FROM trips " +
            "  LEFT JOIN countries " +
            "    ON trips.destination_country_id = countries.id " +
            "  LEFT JOIN bookings " +
            "    ON bookings.trip_id = trips.id " +
            "WHERE trips.destination_country_id = ?3 " +
            "      AND trips.start_date >= ?1 AND trips.end_date <= ?2 " +
            "GROUP BY trips.id " +
            "HAVING SUM(bookings.people_quantity) < (trips.people_limit - ?4) " +
            " OR SUM(bookings.people_quantity) IS NULL", nativeQuery = true)
    List<Trip> findAvailableTrips(Date startDate, Date endDate, Long countryId, int peopleCount);

    @Query(value = "SELECT trips.* FROM trips " +
            "  LEFT JOIN bookings " +
            "  ON bookings.trip_id = trips.id " +
            "WHERE trips.id = ?1 " +
            "GROUP BY trips.id, bookings.id " +
            "HAVING SUM(bookings.people_quantity) < (trips.people_limit - ?2) " +
            "OR bookings.id IS NULL", nativeQuery = true)
    List<Trip> hasFreePlaces(Long tripId, int peopleQuantity);

    @Query("SELECT t FROM Trip t WHERE (lower(t.title) LIKE lower(?1) " +
            "OR lower(t.destinationCountry.name) LIKE lower(?1) " +
            "OR lower (t.destinationCity.name) LIKE lower(?1)) " +
            "AND " +
            "(t.startDate >= date(?2) AND t.endDate <= date(?3)) " +
            "AND " +
            "(t.tripPrice >= ?4 AND t.tripPrice <= ?5)")
    Page<Trip> findByManyFields(String phrase, String from, String to, BigDecimal priceMin, BigDecimal priceMax, Pageable pageable);
}
