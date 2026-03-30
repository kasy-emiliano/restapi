package mg.mbdspringboot.restapi.controller;

import lombok.RequiredArgsConstructor;
import mg.mbdspringboot.restapi.dto.VoitureResponse;
import mg.mbdspringboot.restapi.model.Agence;
import mg.mbdspringboot.restapi.service.AgenceService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final AgenceService agenceService;

    @GetMapping
    public ResponseEntity<List<EntityModel<Agence>>> getAllAgences() {
        List<Agence> agences = agenceService.getAllAgences();
        List<EntityModel<Agence>> agenceResources = agences.stream()
                .map(agence -> EntityModel.of(agence,
                        linkTo(methodOn(AgenceController.class).getAgenceById(agence.getId())).withSelfRel(),
                        linkTo(methodOn(AgenceController.class).getAllAgences()).withRel("agences")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(agenceResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Agence>> getAgenceById(@PathVariable Long id) {
        Agence agence = agenceService.getAgenceById(id);
        EntityModel<Agence> resource = EntityModel.of(agence,
                linkTo(methodOn(AgenceController.class).getAgenceById(id)).withSelfRel(),
                linkTo(methodOn(AgenceController.class).getAllAgences()).withRel("agences"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}/voitures")
    public Iterable<VoitureResponse> getVoitures(@PathVariable Long id) {

        return agenceService.findVoitureByAgence(id);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Agence>> createAgence(@RequestBody Agence agence) {

        agence.setId(null);
        Agence newAgence = agenceService.createAgence(agence);
        EntityModel<Agence> resource = EntityModel.of(newAgence,
                linkTo(methodOn(AgenceController.class).getAgenceById(newAgence.getId())).withSelfRel(),
                linkTo(methodOn(AgenceController.class).getAllAgences()).withRel("agences"));
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Agence>> updateAgence(@PathVariable Long id, @RequestBody Agence agence) {
        Agence updatedAgence = agenceService.updateAgence(id, agence);
        EntityModel<Agence> resource = EntityModel.of(updatedAgence,
                linkTo(methodOn(AgenceController.class).getAgenceById(updatedAgence.getId())).withSelfRel(),
                linkTo(methodOn(AgenceController.class).getAllAgences()).withRel("agences"));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable Long id) {
        agenceService.deleteAgence(id);
        return ResponseEntity.noContent().build();
    }
}
