package at.htl.controller;

import at.htl.model.Coordinates;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;

@ApplicationScoped
public class ImageDataExtractor {

    public Coordinates getCoordinates(File imagePath) {

        try {

            Metadata metadata = ImageMetadataReader.readMetadata(imagePath);

            if (metadata != null) {
                if (metadata.containsDirectoryOfType(GpsDirectory.class)) {

                    GpsDirectory gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);

                    return new Coordinates(
                            gpsDir.getGeoLocation().getLongitude(),
                            gpsDir.getGeoLocation().getLatitude(),
                            gpsDir.getGpsDate()
                    );

                } else throw new IllegalArgumentException("The image have no geocoordinates!");
            } else throw new IllegalArgumentException("The Image do not have any MetaData!");

        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
