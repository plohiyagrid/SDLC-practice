package SportEvent.repository;

import SportEvent.match.Match;
import SportEvent.match.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByStatus(MatchStatus status);
    List<Match> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
} 