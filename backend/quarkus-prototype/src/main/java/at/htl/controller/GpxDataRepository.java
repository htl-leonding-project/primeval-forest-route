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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GpxDataRepository implements PanacheRepository<GpxData> {

    @Inject
    CoordinatesRepository coordinatesRepository;

    @Inject
    EntityManager em;

    public static String[] allPaths = getAllPathsOfRoutes();

    @Transactional
    public void persistGpx() throws IOException {
        for (String allPath : allPaths) {
            GpxData data = parseGpxData(allPath);

            persist(data);
            //coordinatesRepository.updateCoordinates(data);
        }
    }

    @Transactional
    public List<Coordinates> getCoordinateList(Long i) {
        TypedQuery<GpxData> query = em.createNamedQuery("GpxData.findById", GpxData.class).setParameter("INT", i);
        GpxData gpxData = query.getSingleResult();
        return gpxData.getRoutePoints();
    }

    @Transactional
    public List<String> getPathNames() throws IOException {
        List<String> allNamesOfPaths = new ArrayList<>();

        for (String allPath : allPaths) {
            GPX gpx = GPX.read(allPath);

            String name = gpx.getTracks().get(0).getName().get();

            allNamesOfPaths.add(name);
        }

        return allNamesOfPaths;
    }

    @Transactional
    public List<GpxData> getAllGpxData() {
        return em.createNamedQuery("GpxData.findAll", GpxData.class)
                .getResultList();
    }

    @Transactional
    public GpxData parseGpxData(String path) throws IOException {
        List<Coordinates> coordinatesList = new ArrayList<>();
        String name = "";
        GPX gpx = GPX.read(path);
        int size;

        size = gpx.getTracks().get(0).getSegments().get(0).getPoints().size();
        name = gpx.getTracks().get(0).getName().get();

        coordinatesList = coordinatesRepository.persistCoordinates(size, gpx, coordinatesList);

        return new GpxData(name, coordinatesList);
    }

    private static String[] getAllPathsOfRoutes() {
        return new String[]{"../src/main/resources/route/route1.gpx",
                "../src/main/resources/route/route2.gpx",
                "../src/main/resources/route/route3.gpx"};
    }
}
