package mg.mbdspringboot.restapi.controller;

import lombok.RequiredArgsConstructor;
import mg.mbdspringboot.restapi.repository.VoitureRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {
    
    private final VoitureRepository voitureRepository;
    
    @GetMapping("/global")
    public ResponseEntity<Map<String, Object>> getGlobalStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalVoitures = voitureRepository.count();
        stats.put("totalVoitures", totalVoitures);
        
        long voituresDisponibles = voitureRepository.countByDisponible(true);
        stats.put("voituresDisponibles", voituresDisponibles);
        
        double tauxDisponibilite = totalVoitures > 0 ? 
            (double) voituresDisponibles / totalVoitures * 100 : 0;
        stats.put("tauxDisponibilite", tauxDisponibilite);
        
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/agence/{agenceId}")
    public ResponseEntity<Map<String, Object>> getAgenceStats(@PathVariable Long agenceId) {
        Map<String, Object> stats = new HashMap<>();
        
        long nbVoitures = voitureRepository.countByAgenceId(agenceId);
        stats.put("nombreVoitures", nbVoitures);
        
        Double prixMoyen = voitureRepository.avgPrixByAgenceId(agenceId);
        stats.put("prixMoyen", prixMoyen != null ? prixMoyen : 0);
        
        BigDecimal revenuTotal = voitureRepository.totalRevenuByAgenceId(agenceId);
        stats.put("revenuTotal", revenuTotal != null ? revenuTotal : BigDecimal.ZERO);
        
        return ResponseEntity.ok(stats);
    }
}
