package controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDescriptor;
import com.drew.metadata.exif.GpsDirectory;
import entity.GeoCoordinates;

import java.io.File;
import java.io.IOException;

public class Main {

    static File imagePath = new File("images/testBild.jpg");

    public static void main(String[] args) {

        GeoCoordinates c = getCoordinates(imagePath);

        System.out.println(c);

    }

    private static GeoCoordinates getCoordinates(File imagePath) {

        try {

            Metadata metadata = ImageMetadataReader.readMetadata(imagePath);
            String[] coordinates = new String[2];


            if (metadata != null) {
                if (metadata.containsDirectoryOfType(GpsDirectory.class)) {

                    GpsDirectory gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);
                    GpsDescriptor gpsDesc = new GpsDescriptor(gpsDir);


                    coordinates[0] = gpsDesc.getGpsLatitudeDescription(); // Breitengrad auslesen
                    coordinates[1] = gpsDesc.getGpsLongitudeDescription(); // Längengrad auslesen

                    GeoCoordinates c =
                            new GeoCoordinates(
                                    coordinates[0],
                                    coordinates[1]
                            );

                    return c;

                } else throw new IllegalArgumentException("The image have no geocoordinates!");
            } else throw new IllegalArgumentException("The Image do not have any MetaData!");

        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
