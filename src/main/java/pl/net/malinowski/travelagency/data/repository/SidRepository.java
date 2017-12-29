package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.AclSid;

@Repository
public interface SidRepository extends JpaRepository<AclSid, Long> {
}
