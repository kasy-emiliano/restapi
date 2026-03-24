package mg.mbdspringboot.restapi.controller;

import lombok.RequiredArgsConstructor;
import mg.mbdspringboot.restapi.model.Voiture;
import mg.mbdspringboot.restapi.service.VoitureService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/voitures")
@RequiredArgsConstructor
public class VoitureController {
    
    private final VoitureService voitureService;
    
    @GetMapping
    public ResponseEntity<List<EntityModel<Voiture>>> getAllVoitures() {
        List<Voiture> voitures = voitureService.findAll();
        List<EntityModel<Voiture>> resources = voitures.stream()
            .map(voiture -> EntityModel.of(voiture,
                linkTo(methodOn(VoitureController.class).getVoitureById(voiture.getId())).withSelfRel(),
                linkTo(methodOn(VoitureController.class).getAllVoitures()).withRel("voitures")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Voiture>> getVoitureById(@PathVariable Long id) {
        Optional<Voiture> voiture = voitureService.findById(id);
        return voiture.map(v -> {
            EntityModel<Voiture> resource = EntityModel.of(v,
                linkTo(methodOn(VoitureController.class).getVoitureById(id)).withSelfRel(),
                linkTo(methodOn(VoitureController.class).getAllVoitures()).withRel("voitures"));
            return ResponseEntity.ok(resource);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<EntityModel<Voiture>>> getVoituresDisponibles() {
        List<Voiture> voitures = voitureService.findByDisponible(true);
        List<EntityModel<Voiture>> resources = voitures.stream()
            .map(voiture -> EntityModel.of(voiture,
                linkTo(methodOn(VoitureController.class).getVoitureById(voiture.getId())).withSelfRel()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
    
    @GetMapping("/agence/{agenceId}")
    public ResponseEntity<List<EntityModel<Voiture>>> getVoituresByAgence(@PathVariable Long agenceId) {
        List<Voiture> voitures = voitureService.findByAgenceId(agenceId);
        List<EntityModel<Voiture>> resources = voitures.stream()
            .map(voiture -> EntityModel.of(voiture,
                linkTo(methodOn(VoitureController.class).getVoitureById(voiture.getId())).withSelfRel()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Voiture>> createVoiture(@RequestBody Voiture voiture) {
        Voiture newVoiture = voitureService.save(voiture);
        EntityModel<Voiture> resource = EntityModel.of(newVoiture,
            linkTo(methodOn(VoitureController.class).getVoitureById(newVoiture.getId())).withSelfRel(),
            linkTo(methodOn(VoitureController.class).getAllVoitures()).withRel("voitures"));
        return ResponseEntity.ok(resource);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Voiture>> updateVoiture(@PathVariable Long id, @RequestBody Voiture voiture) {
        if (!voitureService.findById(id).isPresent()) return ResponseEntity.notFound().build();
        voiture.setId(id);
        Voiture updatedVoiture = voitureService.save(voiture);
        EntityModel<Voiture> resource = EntityModel.of(updatedVoiture,
            linkTo(methodOn(VoitureController.class).getVoitureById(updatedVoiture.getId())).withSelfRel(),
            linkTo(methodOn(VoitureController.class).getAllVoitures()).withRel("voitures"));
        return ResponseEntity.ok(resource);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVoiture(@PathVariable Long id) {
        voitureService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
