package mg.mbdspringboot.restapi.service;

import lombok.RequiredArgsConstructor;
import mg.mbdspringboot.restapi.model.Agence;
import mg.mbdspringboot.restapi.repository.AgenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceService {
    
    private final AgenceRepository agenceRepository;

    public List<Agence> getAllAgences() { return agenceRepository.findAll(); }
    
    public Agence getAgenceById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));
    }
    
    public Agence createAgence(Agence agence) { return agenceRepository.save(agence); }
    
    public Agence updateAgence(Long id, Agence agence) {
        agence.setId(id);
        return agenceRepository.save(agence);
    }
    
    public void deleteAgence(Long id) { agenceRepository.deleteById(id); }
    
    public List<Agence> findAll() { return agenceRepository.findAll(); }
}