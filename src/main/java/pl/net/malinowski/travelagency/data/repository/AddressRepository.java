package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
