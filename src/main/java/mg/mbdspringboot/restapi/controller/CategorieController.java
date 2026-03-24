package mg.mbdspringboot.restapi.controller;

import mg.mbdspringboot.restapi.model.Categorie;
import mg.mbdspringboot.restapi.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping
    public ResponseEntity<List<EntityModel<Categorie>>> getAllCategories() {
        List<Categorie> categories = categorieService.findAll();
        List<EntityModel<Categorie>> resources = categories.stream()
            .map(categorie -> EntityModel.of(categorie,
                linkTo(methodOn(CategorieController.class).getCategorieById(categorie.getId())).withSelfRel(),
                linkTo(methodOn(CategorieController.class).getAllCategories()).withRel("categories")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Categorie>> getCategorieById(@PathVariable Long id) {
        Optional<Categorie> categorie = categorieService.findById(id);
        return categorie.map(c -> {
            EntityModel<Categorie> resource = EntityModel.of(c,
                linkTo(methodOn(CategorieController.class).getCategorieById(id)).withSelfRel(),
                linkTo(methodOn(CategorieController.class).getAllCategories()).withRel("categories"));
            return ResponseEntity.ok(resource);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Categorie>> createCategorie(@Valid @RequestBody Categorie categorie) {
        Categorie savedCategorie = categorieService.save(categorie);
        EntityModel<Categorie> resource = EntityModel.of(savedCategorie,
            linkTo(methodOn(CategorieController.class).getCategorieById(savedCategorie.getId())).withSelfRel(),
            linkTo(methodOn(CategorieController.class).getAllCategories()).withRel("categories"));
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Categorie>> updateCategorie(@PathVariable Long id, @Valid @RequestBody Categorie categorieDetails) {
        Optional<Categorie> existingCategorie = categorieService.findById(id);
        
        if (existingCategorie.isPresent()) {
            Categorie categorie = existingCategorie.get();
            categorie.setNom(categorieDetails.getNom());
            
            Categorie updatedCategorie = categorieService.save(categorie);
            EntityModel<Categorie> resource = EntityModel.of(updatedCategorie,
                linkTo(methodOn(CategorieController.class).getCategorieById(updatedCategorie.getId())).withSelfRel(),
                linkTo(methodOn(CategorieController.class).getAllCategories()).withRel("categories"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        if (categorieService.findById(id).isPresent()) {
            categorieService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<EntityModel<Categorie>> getCategorieByNom(@PathVariable String nom) {
        Optional<Categorie> categorie = categorieService.findByNom(nom);
        return categorie.map(c -> {
            EntityModel<Categorie> resource = EntityModel.of(c,
                linkTo(methodOn(CategorieController.class).getCategorieById(c.getId())).withSelfRel(),
                linkTo(methodOn(CategorieController.class).getAllCategories()).withRel("categories"));
            return ResponseEntity.ok(resource);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
