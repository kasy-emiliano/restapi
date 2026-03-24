package mg.mbdspringboot.restapi.service;

import lombok.RequiredArgsConstructor;
import mg.mbdspringboot.restapi.model.Voiture;
import mg.mbdspringboot.restapi.repository.VoitureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoitureService {
    
    private final VoitureRepository voitureRepository;
    
    public List<Voiture> findAll() {
        return voitureRepository.findAll();
    }
    
    public Optional<Voiture> findById(Long id) {
        return voitureRepository.findById(id);
    }
    
    public Voiture save(Voiture voiture) {
        return voitureRepository.save(voiture);
    }
    
    public void deleteById(Long id) {
        voitureRepository.deleteById(id);
    }
    
    public List<Voiture> findByDisponible(boolean disponible) {
        return voitureRepository.findByDisponible(disponible);
    }
    
    public List<Voiture> findByAgenceId(Long agenceId) {
        return voitureRepository.findByAgenceId(agenceId);
    }
}
