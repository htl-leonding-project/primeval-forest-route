package at.htl.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

@Entity
public class ControlPoint extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    private Route route;

    public ControlPoint() {
    }

    public ControlPoint(String name, Double latitude, Double longitude, Route route) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.route = route;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitudeCoordinate) {
        this.latitude = latitudeCoordinate;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitudeCoordinate) {
        this.longitude = longitudeCoordinate;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "ControlPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitudeCoordinate=" + latitude +
                ", longitudeCoordinate=" + longitude +'}';
    }
}
