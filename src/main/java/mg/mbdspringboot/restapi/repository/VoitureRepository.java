package mg.mbdspringboot.restapi.repository;

import mg.mbdspringboot.restapi.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    
    List<Voiture> findByDisponible(boolean disponible);
    
    long countByDisponible(boolean disponible);
    
    List<Voiture> findByAgenceId(Long agenceId);
    
    @Query("SELECT COUNT(v) FROM Voiture v WHERE v.agence.id = :agenceId")
    long countByAgenceId(Long agenceId);
    
    @Query("SELECT AVG(v.prixJour) FROM Voiture v WHERE v.agence.id = :agenceId")
    Double avgPrixByAgenceId(Long agenceId);
    
    @Query("SELECT SUM(l.total) FROM Location l WHERE l.voiture.agence.id = :agenceId")
    BigDecimal totalRevenuByAgenceId(Long agenceId);
}
