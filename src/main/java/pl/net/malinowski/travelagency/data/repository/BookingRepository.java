package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(User user);

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Booking> findAll();

    @PreAuthorize("hasPermission(#booking, 'DELETE') or hasRole('ROLE_ADMIN')")
    void delete(@Param("booking") Booking booking);

    Long countByTripId(Long tripId);
}
