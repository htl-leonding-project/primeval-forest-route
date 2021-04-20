package at.htl.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Route {

    @Id
    private Long id;

    private String name;
    private Double length;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    private List<ControlPoint> controlPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hike hike;

    public Route() {
    }

    public Route(Long id, String name, Double length, List<ControlPoint> controlPoints, Hike hike) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.controlPoints = controlPoints;
        this.hike = hike;
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

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public List<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(List<ControlPoint> controlPoints) {
        this.controlPoints = controlPoints;
    }

    public Hike getHike() {
        return hike;
    }

    public void setHike(Hike hike) {
        this.hike = hike;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", controlPoints=" + controlPoints +
                '}';
    }
}
