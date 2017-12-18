package pl.net.malinowski.travelagency.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.net.malinowski.travelagency.data.entity.Address;
import pl.net.malinowski.travelagency.data.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE lower(u.email) LIKE lower(?1) AND lower(u.email) NOT LIKE lower(?2)")
    int countUserWithEmail(String email, String loggedInEmail);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET address_id = ?1 WHERE id = ?2", nativeQuery = true)
    int updateAddress(Long addressId, Long userId);
}
