package mg.mbdspringboot.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import mg.mbdspringboot.restapi.dto.VoitureResponse;
import mg.mbdspringboot.restapi.model.Categorie;
import mg.mbdspringboot.restapi.model.Voiture;

@Mapper(componentModel = "spring")
public interface VoitureMapper {
    @Mapping(target = "agenceNom", source = "agence.nom")
    @Mapping(target = "agenceVille", source = "agence.ville")
    @Mapping(target = "nombreLocations", expression = "java(entity.getLocations() != null ? entity.getLocations().size() : 0)")
    @Mapping(target = "categories", source = "categories")
    VoitureResponse voitureToVoitureResponse(Voiture entity);

    default String map(Categorie category) {
        return category.getNom();
    }

    default List<String> map(List<Categorie> categories) {
        return categories.stream().map(this::map).toList();
    }
}
