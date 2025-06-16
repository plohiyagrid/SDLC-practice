package SportEvent.service;

import SportEvent.event.Event;
import SportEvent.event.EventStatus;
import SportEvent.registration.Registration;
import SportEvent.repository.EventRepository;
import SportEvent.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public RegistrationService(
            RegistrationRepository registrationRepository,
            EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    public Registration registerParticipant(Registration registration, Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getStatus() != EventStatus.REGISTRATION_OPEN) {
            throw new RuntimeException("Registration is closed for this event");
        }

        registration.setEvent(event);
        registration.setRegistrationDate(LocalDateTime.now());
        return registrationRepository.save(registration);
    }

    public List<Registration> findRegistrationsByEvent(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    public List<Registration> findRegistrationsByDateRange(LocalDateTime start, LocalDateTime end) {
        return registrationRepository.findByRegistrationDateBetween(start, end);
    }

    public List<Registration> searchRegistrationsByParticipantName(String name) {
        return registrationRepository.findByParticipantNameContainingIgnoreCase(name);
    }

    public void cancelRegistration(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }
} 