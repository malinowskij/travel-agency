package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(User user);

    Long countByTripId(Long tripId);
}
