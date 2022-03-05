package at.htl.controller;

import at.htl.model.*;
import io.jenetics.jpx.GPX;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class GpxDataRepository implements PanacheRepository<GpxData> {

    @ConfigProperty(name = "efr.xml.path")
    String imagePath;

    @Inject
    CoordinatesRepository coordinatesRepository;

    @Inject
    ControlPointRepository controlPointRepository;

    @Inject
    EntityManager em;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static String[] allPaths = getAllPathsOfRoutes();

    @Transactional
    public void persistGpxFromStart() throws IOException {
        for (String allPath : allPaths) {
            GpxData gpxData = parseGpxData(allPath);
            persist(gpxData);

            List<Coordinates> coords = persistCoordinates(allPath, gpxData);
            gpxData.setRoutePoints(coords);
            em.merge(gpxData);

            List<ControlPoint> points = controlPointRepository.persistControlPoints(gpxData);
            gpxData.setControlPoints(points);
            em.merge(gpxData);
        }
    }

    @Transactional
    public GpxData persistGpxFromUpload(String path) throws IOException {
        GpxData gpxData = parseGpxData(path);
        persist(gpxData);

        List<Coordinates> coords = persistCoordinates(gpxData.getPath(), gpxData);
        gpxData.setRoutePoints(coords);
        return em.merge(gpxData);
    }

    @Transactional
    public List<Coordinates> getCoordinateListById(Long i) {
        TypedQuery<GpxData> query = em.createNamedQuery("GpxData.findById", GpxData.class)
                .setParameter("INT", i);
        GpxData gpxData = query.getSingleResult();
        return gpxData.getRoutePoints();
    }

    @Transactional
    public List<ControlPoint> getControlPointListById(Long i) {
        TypedQuery<GpxData> query = em.createNamedQuery("GpxData.findById", GpxData.class)
                .setParameter("INT", i);
        GpxData gpxData = query.getSingleResult();
        return gpxData.getControlPoints();
    }

    @Transactional
    public List<Coordinates> getCoordinateListByName(String name) {
        TypedQuery<GpxData> query = em.createNamedQuery("GpxData.findByName", GpxData.class)
                .setParameter("NAME", name);
        GpxData gpxData = query.getSingleResult();
        return gpxData.getRoutePoints();
    }

    @Transactional
    public List<ControlPoint> getControlPointListByName(String name) {
        TypedQuery<GpxData> query = em.createNamedQuery("GpxData.findByName", GpxData.class)
                .setParameter("NAME", name);
        GpxData gpxData = query.getSingleResult();
        return gpxData.getControlPoints();
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
        String name = "";
        GPX gpx = GPX.read(path);

        name = gpx.getTracks().get(0).getName().get();

        return new GpxData(name, path);
    }

    @Transactional
    public List<Coordinates> persistCoordinates(String path, GpxData gpxData) throws IOException {
        List<Coordinates> coordinatesList = new ArrayList<>();
        GPX gpx = GPX.read(path);

        coordinatesList = coordinatesRepository.persistCoordinates(gpx, coordinatesList, gpxData);

        return coordinatesList;
    }

    private static String[] getAllPathsOfRoutes() {
        return new String[]{"../src/main/resources/route/route1.gpx",
                "../src/main/resources/route/route2.gpx"};
    }

    @Transactional
    public GpxData uploadXml(InputStream xml, String routeName) {
        var path = new File(
                imagePath,
                routeName + ".gpx"
        );
        System.out.println(path.getName());
        try(var os = new FileOutputStream(path)) {
            xml.transferTo(os);
            GpxData gpxData = new GpxData(
                    routeName,
                    imagePath
            );

            return persistGpxFromUpload(imagePath + "/" + routeName + ".gpx");
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            return null;
        }
    }
}
