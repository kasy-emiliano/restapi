package mg.mbdspringboot.restapi.controller;

import mg.mbdspringboot.restapi.dto.LocationResponse;
import mg.mbdspringboot.restapi.dto.MessageResponse;
import mg.mbdspringboot.restapi.model.Location;
import mg.mbdspringboot.restapi.model.User;
import mg.mbdspringboot.restapi.service.LocationService;
import mg.mbdspringboot.restapi.service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private VoitureService voitureService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EntityModel<Location>>> getAllLocations() {
        List<Location> locations = locationService.findAll();
        List<EntityModel<Location>> resources = locations.stream()
            .map(location -> EntityModel.of(location,
                linkTo(methodOn(LocationController.class).getLocationById(location.getId())).withSelfRel(),
                linkTo(methodOn(LocationController.class).getAllLocations()).withRel("locations")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @locationService.findById(#id).get().user.username == authentication.principal.username")
    public ResponseEntity<EntityModel<Location>> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.findById(id);
        return location.map(l -> {
            EntityModel<Location> resource = EntityModel.of(l,
                linkTo(methodOn(LocationController.class).getLocationById(id)).withSelfRel(),
                linkTo(methodOn(LocationController.class).getAllLocations()).withRel("locations"));
            return ResponseEntity.ok(resource);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public ResponseEntity<Object> createLocation(@Valid @RequestBody Location location) {
        if (!locationService.isVoitureDisponible(location.getVoiture().getId(), location.getDateDebut(), location.getDateFin())) {
            return ResponseEntity.badRequest().body(new MessageResponse("La voiture n'est pas disponible pour ces dates"));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        location.setUser(currentUser);

        location.setVoiture(voitureService.findById(location.getVoiture().getId()).orElseThrow(() 
            -> new RuntimeException("Voiture non trouvée")));

        Location savedLocation = locationService.save(location);
        EntityModel<Location> resource = EntityModel.of(savedLocation,
            linkTo(methodOn(LocationController.class).getLocationById(savedLocation.getId())).withSelfRel(),
            linkTo(methodOn(LocationController.class).getAllLocations()).withRel("locations"));
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Location>> updateLocation(@PathVariable Long id, @Valid @RequestBody Location locationDetails) {
        Optional<Location> existingLocation = locationService.findById(id);
        
        if (existingLocation.isPresent()) {
            Location location = existingLocation.get();
            location.setDateDebut(locationDetails.getDateDebut());
            location.setDateFin(locationDetails.getDateFin());
            location.setVoiture(locationDetails.getVoiture());
            location.setUser(locationDetails.getUser());
            
            Location updatedLocation = locationService.save(location);
            EntityModel<Location> resource = EntityModel.of(updatedLocation,
                linkTo(methodOn(LocationController.class).getLocationById(updatedLocation.getId())).withSelfRel(),
                linkTo(methodOn(LocationController.class).getAllLocations()).withRel("locations"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @locationService.findById(#id).get().user.username == authentication.principal.username")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (locationService.findById(id).isPresent()) {
            locationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public ResponseEntity<List<LocationResponse>> getMyLocations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        List<Location> locations = locationService.findByUserId(currentUser.getId());
        List<LocationResponse> locationResponses = locationService.convertToLocationResponse(locations);
        
        return ResponseEntity.ok(locationResponses);
    }

    @GetMapping("/voiture/{voitureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LocationResponse>> getLocationsByVoiture(@PathVariable Long voitureId) {
        List<Location> locations = locationService.findByVoitureId(voitureId);
        List<LocationResponse> locationResponses = locationService.convertToLocationResponse(locations);
        
        return ResponseEntity.ok(locationResponses);
    }

    @GetMapping("/disponible/{voitureId}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> checkDisponibilite(
            @PathVariable Long voitureId,
            @RequestParam LocalDate dateDebut,
            @RequestParam LocalDate dateFin) {
        
        boolean disponible = locationService.isVoitureDisponible(voitureId, dateDebut, dateFin);
        return ResponseEntity.ok(disponible);
    }
}
