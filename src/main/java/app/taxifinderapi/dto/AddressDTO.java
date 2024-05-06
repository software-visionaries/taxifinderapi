package app.taxifinderapi.dto;

public class AddressDTO {

    private String town;
    private String section;
    private String area;

    public AddressDTO() {
    }

    public AddressDTO(String town, String section, String area) {
        this.town = town;
        this.section = section;
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}