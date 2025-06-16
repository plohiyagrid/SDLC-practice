package SportEvent.repository;

import SportEvent.event.Event;
import SportEvent.event.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatus(EventStatus status);
    List<Event> findByStartDateBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByVenueId(Long venueId);
} 