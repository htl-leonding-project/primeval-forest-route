package at.htl.controller;

import at.htl.model.Coordinates;
import at.htl.model.ImageMultipartBody;
import at.htl.model.Picture;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class PictureRepository implements PanacheRepository<Picture> {

    @ConfigProperty(name = "efr.image.path")
    String imagePath;

    @Inject
    EntityManager em;

    @Inject
    ImageDataExtractor imageDataExtractor;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Transactional
    public Picture save(Picture picture) {
        return em.merge(picture);
    }

    public InputStream getPictureById(Long id) {
        try {
            Picture picture = findById(id);
            String fileUrl = String.format("%s/%s",
                    picture.getImageUrl(), picture.getFileName());

            try (InputStream os = new FileInputStream(fileUrl)) {
                return os;
            } catch (FileNotFoundException e) {
                logger.log(Level.WARNING, e.getMessage());
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.INFO, e.getMessage());
            return null;
        }
    }

    @Transactional
    public Picture uploadImage(ImageMultipartBody imageMultipartBody) {
        var file = imageMultipartBody.inputStream;

        Picture picture = this.save(
                new Picture(
                        imageMultipartBody.fileName,
                        null, null, null, null)
        );

        var path = new File(
                imagePath,
                picture.getId() + "_" + picture.getFileName() + "_cp_picture.jpg"
        );

        picture.setImageUrl(path.getPath());

        try(var os = new FileOutputStream(path)) {
            file.transferTo(os);
            Coordinates coordinates = imageDataExtractor.getCoordinates(path);
            System.out.println(coordinates.toString());
            picture.setCoordinates(coordinates);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
        return this.save(picture);
    }
}
