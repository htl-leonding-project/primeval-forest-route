package at.htl.model;

import javax.persistence.*;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "gpxDataId", fetch = FetchType.EAGER)
    private List<Coordinates> routePoints;

    public GpxData() {
    }

    public GpxData(List<Coordinates> routePoints) {
        this.routePoints = routePoints;
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

    @Override
    public String toString() {
        return "GpxData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", routePoints=" + name +
                '}';
    }
}