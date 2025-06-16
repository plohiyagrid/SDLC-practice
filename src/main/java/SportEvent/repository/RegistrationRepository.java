package SportEvent.repository;

import SportEvent.registration.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEventId(Long eventId);
    List<Registration> findByRegistrationDateBetween(LocalDateTime start, LocalDateTime end);
    List<Registration> findByParticipantNameContainingIgnoreCase(String name);
} 