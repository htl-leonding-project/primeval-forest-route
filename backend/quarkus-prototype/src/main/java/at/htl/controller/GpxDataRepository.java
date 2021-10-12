package at.htl.controller;

import at.htl.model.Coordinates;
import at.htl.model.GpxData;
import io.jenetics.jpx.GPX;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GpxDataRepository implements PanacheRepository<GpxData> {

    @Inject
    CoordinatesRepository coordinatesRepository;

    public static String gpxData = "C:/Users/jratz/school/5AHITM/ITP/Projekt/primeval-forest-route/primeval-forest-route/backend/quarkus-prototype/src/main/resources/route1.gpx";

    @Transactional
    public void persistGpx() throws IOException {
        GpxData data = parseGpxData();

        persist(data);
    }

    @Transactional
    public GpxData parseGpxData() throws IOException {
        GPX gpx = GPX.read(gpxData);
        int size;
        String name;
        GpxData gpxEntity = new GpxData();
        List<Coordinates> coordinatesList = new ArrayList<>();

        size = gpx.getTracks().get(0).getSegments().get(0).getPoints().size();
        name = gpx.getTracks().get(0).getName().get();

        coordinatesList = coordinatesRepository.persistCoordinates(size, gpx, coordinatesList);

        gpxEntity.setName(name);
        gpxEntity.setRoutePoints(coordinatesList);

        return gpxEntity;
    }
}
