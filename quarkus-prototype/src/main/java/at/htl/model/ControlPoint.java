package at.htl.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "contrPointSeq",
        initialValue = 10,
        sequenceName = "CONTRPOINT_SEQ"
)
public class ControlPoint {

    @Id
    @GeneratedValue(generator = "contrPointSeq")
    private Long id;

    private String name;
    private Double latitudeCoordinate;
    private Double longitudeCoordinate;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Override
    public String toString() {
        return "ControlPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitudeCoordinate=" + latitudeCoordinate +
                ", longitudeCoordinate=" + longitudeCoordinate +'}';
    }
}
