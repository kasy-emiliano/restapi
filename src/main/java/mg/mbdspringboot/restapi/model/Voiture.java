package mg.mbdspringboot.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voiture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String marque;
    
    @Column(nullable = false)
    private String modele;
    
    @Column(nullable = false)
    private BigDecimal prixJour;
    
    @Column(nullable = false)
    private boolean disponible = true;
    
    @ManyToOne
    @JoinColumn(name = "agence_id", nullable = false)
    private Agence agence;
    
    @ManyToMany
    @JoinTable(
        name = "voiture_categorie",
        joinColumns = @JoinColumn(name = "voiture_id"),
        inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private List<Categorie> categories;
    
    @OneToMany(mappedBy = "voiture", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Location> locations;
}
