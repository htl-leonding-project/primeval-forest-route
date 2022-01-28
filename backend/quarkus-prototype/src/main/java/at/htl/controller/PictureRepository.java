package at.htl.controller;

import at.htl.model.ControlPoint;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class PictureRepository implements PanacheRepository<Picture> {

    @ConfigProperty(name = "efr.image.path")
    String imagePath;

    @Inject
    EntityManager em;

    @Inject
    ImageDataExtractor imageDataExtractor;

    @Inject
    ControlPointRepository controlPointRepository;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Transactional
    public Picture save(Picture picture) {
        return em.merge(picture);
    }

    /*public InputStream getPictureById(Long id) {
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
    }*/

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
            picture.setCoordinates(coordinates);

            picture.setControlPoint(this.getClosestControlPoint(coordinates));

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
        return this.save(picture);
    }

    public File getPictureById(Long id) {
        Picture picture = findById(id);

        return new File(
                picture.getImageUrl()
        );
        /*try (FileInputStream is = new FileInputStream()) {
            is.


        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public ControlPoint getClosestControlPoint(Coordinates pictureCoordinates) {

        int minDistance = -1;
        int distance;
        int index = -1;

        List<ControlPoint> controlPoints = controlPointRepository
                .findAll()
                .stream()
                .collect(Collectors.toList());

        List<Coordinates> cpCoordinates = controlPoints
                .stream()
                .map(c ->
                        new Coordinates(
                                c.getId(),
                                c.getLongitudeCoordinate(),
                                c.getLatitudeCoordinate()
                        ))
                .collect(Collectors.toList());

        System.out.println(cpCoordinates);

        for (int i = 0; i < cpCoordinates.size(); i++) {
            Coordinates cpCoordinate = cpCoordinates.get(i);
            distance = this.getDistanceBetweenTwoCoordinates(
                    pictureCoordinates,
                    cpCoordinate
            );

            if (minDistance > distance || minDistance == -1) {
                minDistance = distance;
                index = i;
            }
        }
        System.out.println(minDistance);
        // TODO: if statement ignored for test purpose
        //if (minDistance <= 200 && minDistance != -1 && index != -1) {
        return controlPoints.get(index);
        //}
        // return null;
    }

    public int getDistanceBetweenTwoCoordinates(Coordinates pictureCoordinate, Coordinates controlPointCoordinate) {

        double lon1 = Math.toRadians(pictureCoordinate.getLongitude());
        double lon2 = Math.toRadians(controlPointCoordinate.getLongitude());
        double lat1 = Math.toRadians(pictureCoordinate.getLatitude());
        double lat2 = Math.toRadians(controlPointCoordinate.getLatitude());

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers.
        double r = 6371;

        // calculate the result
        return (int) ((c * r) * 1000);
    }
}
