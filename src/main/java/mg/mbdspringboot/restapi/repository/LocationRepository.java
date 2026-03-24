package mg.mbdspringboot.restapi.repository;

import mg.mbdspringboot.restapi.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    
    List<Location> findByUserId(Long userId);
    
    List<Location> findByVoitureId(Long voitureId);
    
    @Query("SELECT l FROM Location l WHERE l.user.id = :userId AND l.dateDebut >= :startDate AND l.dateFin <= :endDate")
    List<Location> findByUserAndDateRange(@Param("userId") Long userId, 
                                         @Param("startDate") LocalDate startDate, 
                                         @Param("endDate") LocalDate endDate);
    
    @Query("SELECT l FROM Location l WHERE l.voiture.id = :voitureId AND " +
           "(l.dateDebut <= :endDate AND l.dateFin >= :startDate)")
    List<Location> findConflictingLocations(@Param("voitureId") Long voitureId, 
                                           @Param("startDate") LocalDate startDate, 
                                           @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(l) FROM Location l WHERE l.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
}
