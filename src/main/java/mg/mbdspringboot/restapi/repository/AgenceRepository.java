package mg.mbdspringboot.restapi.repository;

import mg.mbdspringboot.restapi.model.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
    List<Agence> findByVille(String ville);
    boolean existsByNom(String nom);
}
