package at.htl.backend.Entity;

import javax.persistence.*;

@Entity
@Table(name = "catalog_image", schema = "public")
public class CatalogImage {

    @Id
    @GeneratedValue
    private int imageId;

    @Column(name = "image_name")
    private String imageName;

    @Lob
    @Column(name = "image")
    private byte[] catlogImg;

    @Column(name = "category")
    private String category;

    @Column(name = "image_size")
    private long imgSize;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getCatlogImg() {
        return catlogImg;
    }

    public void setCatlogImg(byte[] catlogImg) {
        this.catlogImg = catlogImg;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getImgSize() {
        return imgSize;
    }

    public void setImgSize(long imgSize) {
        this.imgSize = imgSize;
    }

    public CatalogImage() {
        super();
    }

}
