package mg.mbdspringboot.restapi.service;

import lombok.RequiredArgsConstructor;
import mg.mbdspringboot.restapi.model.Categorie;
import mg.mbdspringboot.restapi.repository.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategorieService {
    
    private final CategorieRepository categorieRepository;

    public List<Categorie> findAll() { return categorieRepository.findAll(); }
    
    public Optional<Categorie> findById(Long id) { return categorieRepository.findById(id); }
    
    public Categorie save(Categorie categorie) { return categorieRepository.save(categorie); }
    
    public void deleteById(Long id) { categorieRepository.deleteById(id); }
    
    public Optional<Categorie> findByNom(String nom) { return categorieRepository.findByNom(nom); }
}