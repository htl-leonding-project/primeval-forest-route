package at.htl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@Table(name = "POINTS")
public class ControlPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double latitudeCoordinate;
    private Double longitudeCoordinate;

    @JsonbTransient
    @ManyToOne
    @JoinTable(name = "GPXDATA_POINTS_LINK")
    GpxData gpxData;

    public ControlPoint() {
    }

    public ControlPoint(String name, Double latitudeCoordinate, Double longitudeCoordinate, GpxData gpxData) {
        this.name = name;
        this.latitudeCoordinate = latitudeCoordinate;
        this.longitudeCoordinate = longitudeCoordinate;
        this.gpxData = gpxData;
    }

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

    public GpxData getGpxData() {
        return gpxData;
    }

    public void setGpxData(GpxData gpxData) {
        this.gpxData = gpxData;
    }

    @Override
    public String toString() {
        return "ControlPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitudeCoordinate=" + latitudeCoordinate +
                ", longitudeCoordinate=" + longitudeCoordinate +'}';
    }
}
