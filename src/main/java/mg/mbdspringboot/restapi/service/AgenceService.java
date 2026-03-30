package mg.mbdspringboot.restapi.service;

import lombok.RequiredArgsConstructor;
import mg.mbdspringboot.restapi.dto.VoitureResponse;
import mg.mbdspringboot.restapi.mapper.VoitureMapper;
import mg.mbdspringboot.restapi.model.Agence;
import mg.mbdspringboot.restapi.model.Voiture;
import mg.mbdspringboot.restapi.repository.AgenceRepository;
import mg.mbdspringboot.restapi.repository.VoitureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgenceService {

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private VoitureMapper voitureMapper;

    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    public Agence getAgenceById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence non trouvée"));
    }

    public Agence createAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    public Agence updateAgence(Long id, Agence agence) {
        agence.setId(id);
        return agenceRepository.save(agence);
    }

    public void deleteAgence(Long id) {
        agenceRepository.deleteById(id);
    }

    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    public Iterable<VoitureResponse> findVoitureByAgence(Long id) {

        return voitureRepository.findByAgenceId(id).stream()
                .map(voitureMapper::voitureToVoitureResponse)
                .toList();
    }
}