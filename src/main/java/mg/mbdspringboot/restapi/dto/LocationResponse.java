package mg.mbdspringboot.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal total;
    private String username;
    private String userEmail;
    private String voitureMarque;
    private String voitureModele;
    private BigDecimal voiturePrixJour;
    private String agenceNom;
    private String agenceVille;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public String getVoitureMarque() { return voitureMarque; }
    public void setVoitureMarque(String voitureMarque) { this.voitureMarque = voitureMarque; }
    
    public String getVoitureModele() { return voitureModele; }
    public void setVoitureModele(String voitureModele) { this.voitureModele = voitureModele; }
    
    public BigDecimal getVoiturePrixJour() { return voiturePrixJour; }
    public void setVoiturePrixJour(BigDecimal voiturePrixJour) { this.voiturePrixJour = voiturePrixJour; }
    
    public String getAgenceNom() { return agenceNom; }
    public void setAgenceNom(String agenceNom) { this.agenceNom = agenceNom; }
    
    public String getAgenceVille() { return agenceVille; }
    public void setAgenceVille(String agenceVille) { this.agenceVille = agenceVille; }
}
