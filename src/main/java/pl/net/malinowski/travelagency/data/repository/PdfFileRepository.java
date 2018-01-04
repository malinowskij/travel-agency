package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.PdfFile;

@Repository
public interface PdfFileRepository extends JpaRepository<PdfFile, Long> {

    @PostAuthorize("hasPermission(returnObject, 'READ') or hasRole('ROLE_ADMIN')")
    PdfFile findOne(Long id);
}
