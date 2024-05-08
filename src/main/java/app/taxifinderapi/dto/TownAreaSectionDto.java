package app.taxifinderapi.dto;

import app.taxifinderapi.model.Area;
import app.taxifinderapi.model.Section;
import app.taxifinderapi.model.Town;

public class TownAreaSectionDto {
    private Town fromTown;
    private Area fromArea;
    private Section fromSection;
    private Section toSection;
    private Area toArea;
    private Town toTown;

    public TownAreaSectionDto() {
    }

    public Town getFromTown() {
        return fromTown;
    }

    public void setFromTown(Town fromTown) {
        this.fromTown = fromTown;
    }

    public Area getFromArea() {
        return fromArea;
    }

    public void setFromArea(Area fromArea) {
        this.fromArea = fromArea;
    }

    public Section getFromSection() {
        return fromSection;
    }

    public void setFromSection(Section fromSection) {
        this.fromSection = fromSection;
    }

    public Section getToSection() {
        return toSection;
    }

    public void setToSection(Section toSection) {
        this.toSection = toSection;
    }

    public Area getToArea() {
        return toArea;
    }

    public void setToArea(Area toArea) {
        this.toArea = toArea;
    }

    public Town getToTown() {
        return toTown;
    }

    public void setToTown(Town toTown) {
        this.toTown = toTown;
    }

    @Override
    public String toString() {
        return "TownAreaSectionDto{" +
                "fromTown=" + fromTown +
                ", fromArea=" + fromArea +
                ", fromSection=" + fromSection +
                ", toSection=" + toSection +
                ", toArea=" + toArea +
                ", toTown=" + toTown +
                '}';
    }
}
