package SportEvent.service;

import SportEvent.repository.VenueRepository;
import SportEvent.venues.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VenueService {
    private final VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue createVenue(Venue venue) {
        venue.setIsAvailable(true);
        return venueRepository.save(venue);
    }

    public Venue updateVenueAvailability(Long venueId, boolean isAvailable) {
        Venue venue = venueRepository.findById(venueId)
            .orElseThrow(() -> new RuntimeException("Venue not found"));
        venue.setIsAvailable(isAvailable);
        return venueRepository.save(venue);
    }

    public List<Venue> findAvailableVenues() {
        return venueRepository.findByIsAvailable(true);
    }

    public List<Venue> findVenuesByMinCapacity(int minCapacity) {
        return venueRepository.findByCapacityGreaterThanEqual(minCapacity);
    }

    public void deleteVenue(Long venueId) {
        venueRepository.deleteById(venueId);
    }
} 