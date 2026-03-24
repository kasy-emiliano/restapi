package mg.mbdspringboot.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenceStatsResponse {
    private Long agenceId;
    private String agenceNom;
    private String agenceVille;
    private int nombreVoitures;
    private int voituresDisponibles;
    private int voituresLouees;
    private BigDecimal revenuTotal;
    private List<VoitureResponse> voitures;
    private List<LocationResponse> locationsRecentes;

    public Long getAgenceId() { return agenceId; }
    public void setAgenceId(Long agenceId) { this.agenceId = agenceId; }
    
    public String getAgenceNom() { return agenceNom; }
    public void setAgenceNom(String agenceNom) { this.agenceNom = agenceNom; }
    
    public String getAgenceVille() { return agenceVille; }
    public void setAgenceVille(String agenceVille) { this.agenceVille = agenceVille; }
    
    public int getNombreVoitures() { return nombreVoitures; }
    public void setNombreVoitures(int nombreVoitures) { this.nombreVoitures = nombreVoitures; }
    
    public int getVoituresDisponibles() { return voituresDisponibles; }
    public void setVoituresDisponibles(int voituresDisponibles) { this.voituresDisponibles = voituresDisponibles; }
    
    public int getVoituresLouees() { return voituresLouees; }
    public void setVoituresLouees(int voituresLouees) { this.voituresLouees = voituresLouees; }
    
    public BigDecimal getRevenuTotal() { return revenuTotal; }
    public void setRevenuTotal(BigDecimal revenuTotal) { this.revenuTotal = revenuTotal; }
    
    public List<VoitureResponse> getVoitures() { return voitures; }
    public void setVoitures(List<VoitureResponse> voitures) { this.voitures = voitures; }
    
    public List<LocationResponse> getLocationsRecentes() { return locationsRecentes; }
    public void setLocationsRecentes(List<LocationResponse> locationsRecentes) { this.locationsRecentes = locationsRecentes; }
}
