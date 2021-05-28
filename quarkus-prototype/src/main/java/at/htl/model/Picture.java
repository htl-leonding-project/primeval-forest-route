package at.htl.model;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imageData;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private ControlPoint controlPoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Hike hike;

    public Picture() {
    }

    public Picture(Long id, String fileName, byte[] imageData, ControlPoint controlPoint) {
        this.id = id;
        this.fileName = fileName;
        this.imageData = imageData;
        this.controlPoint = controlPoint;
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public ControlPoint getControlPoint() {
        return controlPoint;
    }

    public void setControlPoint(ControlPoint controlPoint) {
        this.controlPoint = controlPoint;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }
}
