package at.htl.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GPXDATA")
@NamedQueries({
        @NamedQuery(
                name = "GpxData.findAll",
                query = "select g from GpxData g"
        ),
        @NamedQuery(
                name = "GpxData.findById",
                query = "select g from GpxData g where g.id = :INT"
        ),
        @NamedQuery(
                name = "GpxData.findByName",
                query = "select g from GpxData g where g.name = :NAME"
        )
})
public class GpxData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String path;

    @OneToMany(mappedBy = "gpxData", fetch = FetchType.EAGER, cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Coordinates> routePoints = new ArrayList<>();

    @OneToMany(mappedBy = "gpxData", fetch = FetchType.EAGER, cascade={CascadeType.PERSIST,  CascadeType.REMOVE})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ControlPoint> controlPoints = new ArrayList<>();

    public GpxData() {
    }

    public GpxData(List<Coordinates> routePoints) {
        this.routePoints = routePoints;
    }

    public GpxData(String name) {
        this.name = name;
    }

    public GpxData(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public GpxData(String name, List<Coordinates> routePoints) {
        this.name = name;
        this.routePoints = routePoints;
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

    public List<Coordinates> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<Coordinates> routePoints) {
        this.routePoints = routePoints;
    }

    public List<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(List<ControlPoint> controlPoints) {
        this.controlPoints = controlPoints;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "GpxData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", routePoints=" + name +
                '}';
    }
}
