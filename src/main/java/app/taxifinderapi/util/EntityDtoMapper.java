package app.taxifinderapi.util;

import app.taxifinderapi.dto.TripRequestDto;
import app.taxifinderapi.model.TripRequest;

public class EntityDtoMapper {

    public static TripRequest mapToEntity(TripRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }

        // Validate mandatory fields
        if (dto.getFromTown() == null || dto.getFromTown().isEmpty()) {
            throw new IllegalArgumentException("FromTown is required");
        }

        if (dto.getToTown() == null || dto.getToTown().isEmpty()) {
            throw new IllegalArgumentException("ToTown is required");
        }

        // Create a new TripRequest entity
        TripRequest entity = new TripRequest();

        // Map fields from DTO to entity
        entity.setFromTown(dto.getFromTown());
        entity.setToTown(dto.getToTown());
        entity.setFromArea(dto.getFromArea());
        entity.setToArea(dto.getToArea());
        entity.setFromSection(dto.getFromSection());
        entity.setToSection(dto.getToSection());

        return entity;
    }
}
