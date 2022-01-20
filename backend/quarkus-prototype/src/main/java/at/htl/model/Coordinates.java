package at.htl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

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

    @JoinColumn
    @ManyToOne
    GpxData gpxDataId;

    public Coordinates() {
    }

    public Coordinates(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coordinates(Double longitude, Double latitude, GpxData gpxDataId) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.gpxDataId = gpxDataId;
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

    public GpxData getGpxDataId() {
        return gpxDataId;
    }

    public void setGpxDataId(GpxData gpxDataId) {
        this.gpxDataId = gpxDataId;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", gpxDataId=" + gpxDataId +
                '}';
    }
}
