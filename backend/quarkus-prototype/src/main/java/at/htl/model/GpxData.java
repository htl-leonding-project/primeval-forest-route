package at.htl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GpxData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany()
    List<Coordinates> routePoints;

    public GpxData() {
    }

    public GpxData(String name, List<Coordinates> coordinates) {
        this.name = name;
        this.routePoints = coordinates;
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
                ", routePoints=" + routePoints +
                '}';
    }
}
