package at.htl.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.List;

@Entity
public class Route {

    @Id
    private Long id;

    private String name;
    private Double length;

    public Route() {
    }

    public Route(Long id, String name, Double length) {
        this.id = id;
        this.name = name;
        this.length = length;
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

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", length=" + length + '}';
    }
}
