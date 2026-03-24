package mg.mbdspringboot.restapi.service;

import mg.mbdspringboot.restapi.dto.LocationResponse;
import mg.mbdspringboot.restapi.model.Location;
import mg.mbdspringboot.restapi.repository.LocationRepository;
import mg.mbdspringboot.restapi.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    public Location save(Location location) {
        if (location.getDateDebut() != null && location.getDateFin() != null && location.getVoiture() != null) {
            long jours = ChronoUnit.DAYS.between(location.getDateDebut(), location.getDateFin());
            BigDecimal total = location.getVoiture().getPrixJour().multiply(BigDecimal.valueOf(jours));
            location.setTotal(total);
            
            location.getVoiture().setDisponible(false);
            voitureRepository.save(location.getVoiture());
        }
        return locationRepository.save(location);
    }

    public void deleteById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            location.get().getVoiture().setDisponible(true);
            voitureRepository.save(location.get().getVoiture());
        }
        locationRepository.deleteById(id);
    }

    public List<Location> findByUserId(Long userId) {
        return locationRepository.findByUserId(userId);
    }

    public List<Location> findByVoitureId(Long voitureId) {
        return locationRepository.findByVoitureId(voitureId);
    }

    public List<Location> findByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return locationRepository.findByUserAndDateRange(userId, startDate, endDate);
    }

    public List<Location> findConflictingLocations(Long voitureId, LocalDate startDate, LocalDate endDate) {
        return locationRepository.findConflictingLocations(voitureId, startDate, endDate);
    }

    public boolean isVoitureDisponible(Long voitureId, LocalDate startDate, LocalDate endDate) {
        List<Location> conflictingLocations = findConflictingLocations(voitureId, startDate, endDate);
        return conflictingLocations.isEmpty();
    }

    public List<LocationResponse> convertToLocationResponse(List<Location> locations) {
        return locations.stream().map(location -> {
            LocationResponse response = new LocationResponse();
            response.setId(location.getId());
            response.setDateDebut(location.getDateDebut());
            response.setDateFin(location.getDateFin());
            response.setTotal(location.getTotal());
            response.setUsername(location.getUser().getUsername());
            response.setUserEmail(location.getUser().getEmail());
            response.setVoitureMarque(location.getVoiture().getMarque());
            response.setVoitureModele(location.getVoiture().getModele());
            response.setVoiturePrixJour(location.getVoiture().getPrixJour());
            response.setAgenceNom(location.getVoiture().getAgence().getNom());
            response.setAgenceVille(location.getVoiture().getAgence().getVille());
            return response;
        }).collect(Collectors.toList());
    }
}
