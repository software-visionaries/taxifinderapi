package app.taxifinderapi.dto;

import app.taxifinderapi.model.Area;
import app.taxifinderapi.model.Section;
import app.taxifinderapi.model.Town;

import java.util.List;

public record UserAddressDTO(List<Area> areas, List<Town> towns, List<Section> sections) {
}
