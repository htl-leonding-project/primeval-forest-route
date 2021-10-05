package at.htl.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class Route extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long csvId;

    private String name;
    private Double length;

    public Route() {
    }

    public Route(Long csvId, String name, Double length) {
        this.csvId = csvId;
        this.name = name;
        this.length = length;
    }

    public Long getCsvId() {
        return csvId;
    }

    public void setCsvId(Long csvId) {
        this.csvId = csvId;
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
