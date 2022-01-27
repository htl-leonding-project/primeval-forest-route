package at.htl.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class Picture extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String imageUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Coordinates coordinates;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private ControlPoint controlPoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Hike hike;

    public Picture() {
    }

    public Picture(String fileName, String imageUrl, Coordinates coordinates, ControlPoint controlPoint, Hike hike) {
        this.fileName = fileName;
        this.imageUrl = imageUrl;
        this.coordinates = coordinates;
        this.controlPoint = controlPoint;
        this.hike = hike;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ControlPoint getControlPoint() {
        return controlPoint;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setControlPoint(ControlPoint controlPoint) {
        this.controlPoint = controlPoint;
    }

    public Hike getHike() {
        return hike;
    }

    public void setHike(Hike hike) {
        this.hike = hike;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", controlPoint=" + controlPoint +
                ", hike=" + hike +
                '}';
    }
}
