package at.htl.controller;

import at.htl.model.Coordinates;
import io.jenetics.jpx.GPX;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CoordinatesRepository implements PanacheRepository<Coordinates> {

    @Transactional
    public List<Coordinates> persistCoordinates(int size, GPX gpx, List<Coordinates> coordinatesList) {

        for (int i = 0; i < size; i++) {
            Coordinates coordinates = new Coordinates();

            double longitude = Double.parseDouble(gpx.getTracks().get(0).getSegments().get(0).getPoints().get(i).getLongitude().toString());
            double latitude = Double.parseDouble(gpx.getTracks().get(0).getSegments().get(0).getPoints().get(i).getLatitude().toString());
            coordinates.setLongitude(longitude);
            coordinates.setLattitude(latitude);

            persist(coordinates);

            coordinatesList.add(coordinates);
        }

        return coordinatesList;
    }
}
