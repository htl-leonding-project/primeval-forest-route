package at.htl.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ControlPoint {

    @Id
    private Long id;

    private String name;
    private Double latitudeCoordinate;
    private Double longitudeCoordinate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "controlpoint")
    private List<Picture> pictures;

    @ManyToOne(fetch = FetchType.LAZY)
    private Route route;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitudeCoordinate() {
        return latitudeCoordinate;
    }

    public void setLatitudeCoordinate(Double latitudeCoordinate) {
        this.latitudeCoordinate = latitudeCoordinate;
    }

    public Double getLongitudeCoordinate() {
        return longitudeCoordinate;
    }

    public void setLongitudeCoordinate(Double longitudeCoordinate) {
        this.longitudeCoordinate = longitudeCoordinate;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "ControlPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitudeCoordinate=" + latitudeCoordinate +
                ", longitudeCoordinate=" + longitudeCoordinate +
                ", pictures=" + pictures +
                '}';
    }
}
