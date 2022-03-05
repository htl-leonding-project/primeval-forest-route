package at.htl.model;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Coordinates.getByCoordinates",
                query = "select c from Coordinates c where c.longitude = :LONG and c.latitude = :LAT"
        )
})
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double longitude;
    private Double latitude;

    @JsonbDateFormat("yyyy:MM:dd")
    private Date date;

    @JsonbTransient
    @ManyToOne
    @JoinTable(name = "GPXDATA_COORDINATES_LINK")
    GpxData gpxData;

    private boolean ignore;

    public Coordinates() {
    }

    public Coordinates(Double longitude, Double latitude, Date date) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
    }

    public Coordinates(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coordinates(Long id, Double longitude, Double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coordinates(Double longitude, Double latitude, GpxData gpxData) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.gpxData = gpxData;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GpxData getGpxData() {
        return gpxData;
    }

    public void setGpxData(GpxData gpxData) {
        this.gpxData = gpxData;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", gpxData=" + gpxData +
                '}';
    }
}
