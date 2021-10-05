package at.htl.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
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
    private Double latitudeCoordinate;
    private Double longitudeCoordinate;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    private Route route;

    public ControlPoint() {
    }

    public ControlPoint(String name, Double latitudeCoordinate, Double longitudeCoordinate, Route route) {
        this.name = name;
        this.latitudeCoordinate = latitudeCoordinate;
        this.longitudeCoordinate = longitudeCoordinate;
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
                ", latitudeCoordinate=" + latitudeCoordinate +
                ", longitudeCoordinate=" + longitudeCoordinate +'}';
    }
}
