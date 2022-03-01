package at.htl.controller;

import at.htl.model.Coordinates;
import at.htl.model.GpxData;
import io.jenetics.jpx.GPX;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CoordinatesRepository implements PanacheRepository<Coordinates> {

    @Inject
    EntityManager em;

    @Transactional
    public void updateCoordinates(GpxData gpxData) {
        for (int i = 0; i < gpxData.getRoutePoints().size(); i++) {
            Double longitude = gpxData.getRoutePoints().get(i).getLongitude();
            Double latitude = gpxData.getRoutePoints().get(i).getLatitude();

            Coordinates coordinates = getCoordinatesByCoords(longitude, latitude);

            GpxData gpxData1 = getGpxDataByName(gpxData.getName());

            coordinates.setGpxData(gpxData1);

            persist(coordinates);
        }
    }

    @Transactional
    private GpxData getGpxDataByName(String name) {
        TypedQuery<GpxData> query = em.createNamedQuery("GpxData.findByName", GpxData.class)
                .setParameter("NAME", name);

        return query.getSingleResult();
    }

    @Transactional
    private Coordinates getCoordinatesByCoords(Double longitude, Double latitude) {
        TypedQuery<Coordinates> query = em.createNamedQuery("Coordinates.getByCoordinates", Coordinates.class)
                .setParameter("LONG", longitude)
                .setParameter("LAT", latitude);

        return query.getSingleResult();
    }

    @Transactional
    public List<Coordinates> getAllCoordinates() {
        return findAll()
                .stream()
                .filter(c -> !c.isIgnore())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Coordinates> persistCoordinates(GPX gpx, List<Coordinates> coordinatesList, GpxData gpxData) {
        int size;

        size = gpx.getTracks().get(0).getSegments().get(0).getPoints().size();

        for (int i = 0; i < size; i++) {
            Coordinates coordinates = new Coordinates();

            double longitude = Double.parseDouble(gpx.getTracks().get(0).getSegments().get(0).getPoints().get(i).getLongitude().toString());
            double latitude = Double.parseDouble(gpx.getTracks().get(0).getSegments().get(0).getPoints().get(i).getLatitude().toString());
            coordinates.setLongitude(longitude);
            coordinates.setLatitude(latitude);
            coordinates.setGpxData(gpxData);
            coordinates.setIgnore(false);

            persist(coordinates);

            coordinatesList.add(coordinates);
        }

        return coordinatesList;
    }
}
