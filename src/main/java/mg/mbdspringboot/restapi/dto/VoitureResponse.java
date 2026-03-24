package mg.mbdspringboot.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoitureResponse {
    private Long id;
    private String marque;
    private String modele;
    private BigDecimal prixJour;
    private boolean disponible;
    private String agenceNom;
    private String agenceVille;
    private List<String> categories;
    private int nombreLocations;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }
    
    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }
    
    public BigDecimal getPrixJour() { return prixJour; }
    public void setPrixJour(BigDecimal prixJour) { this.prixJour = prixJour; }
    
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    
    public String getAgenceNom() { return agenceNom; }
    public void setAgenceNom(String agenceNom) { this.agenceNom = agenceNom; }
    
    public String getAgenceVille() { return agenceVille; }
    public void setAgenceVille(String agenceVille) { this.agenceVille = agenceVille; }
    
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    
    public int getNombreLocations() { return nombreLocations; }
    public void setNombreLocations(int nombreLocations) { this.nombreLocations = nombreLocations; }
}
