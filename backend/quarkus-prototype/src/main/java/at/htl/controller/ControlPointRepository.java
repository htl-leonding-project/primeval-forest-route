package at.htl.controller;

import at.htl.model.*;

import at.htl.model.ControlPoint;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class ControlPointRepository implements PanacheRepository<ControlPoint> {

    @ConfigProperty(name = "efr.csv.path")
    String csvPath;

    public static String controlPointsFile = "/controlpoints.csv";

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Transactional
    public void saveControlPoint(ControlPoint controlPoint) {
        if (controlPoint != null) {
            persist(controlPoint);
        }
    }

//    @Transactional
//    public List<ControlPoint> getControlPointByRouteId(Long id) {
//        List<ControlPoint> controlPoints = getAllControlpoints();
//        List<ControlPoint> specificControlPoints = new ArrayList<>();
//
//        for (ControlPoint controlPoint : controlPoints) {
//            System.out.println(controlPoint);
//        }
//
//        for (int i = 0; i < controlPoints.toArray().length; i++) {
//            if(Objects.equals(controlPoints.get(i).getRoute().getId(), id)) {
//                specificControlPoints.add(controlPoints.get(i));
//            }
//        }
//
//        return specificControlPoints;
//    }

    @Transactional
    public List<ControlPoint> getAllControlpoints() {
        return findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ControlPoint> persistControlPoints(GpxData gpxData) {
        List<ControlPoint> controlPoints = generateControlPoints(gpxData);
        //System.out.println(controlPoints);
        for (ControlPoint controlPoint : controlPoints) {
            persist(controlPoint);
        }

        return controlPoints;
    }

    public List<ControlPoint> generateControlPoints(GpxData gpxData) {
        List<String[]> fileData = readDataFromFile(controlPointsFile);
        List<ControlPoint> controlPoints = new ArrayList<>();
        RouteRepository rp = new RouteRepository();
        int abschnitt;

        for (String[] controlPointString : fileData) {
            abschnitt = Integer.parseInt(controlPointString[4]);

            if(abschnitt == gpxData.getId()) {
                ControlPoint controlPoint = new ControlPoint();

                controlPoint.setName(controlPointString[1]);
                controlPoint.setLatitudeCoordinate(Double.parseDouble(controlPointString[2].replaceAll("\\s", "")));
                controlPoint.setLongitudeCoordinate(Double.parseDouble(controlPointString[3].replaceAll("\\s", "")));
                controlPoint.setGpxData(gpxData);

                controlPoints.add(controlPoint);
            }
        }
        return controlPoints;
    }

    public List<String[]> readDataFromFile(String fileName) {

        InputStream is = getClass().getResourceAsStream(fileName);
        BufferedReader br = null;
        if (is != null) {
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_16BE));
        }

        return br
                .lines()
                .skip(1)
                .map(s -> s.split(";"))
                .collect(Collectors.toUnmodifiableList());

    }

    public String importCsvCp(InputStream is, Long id) {
        var path = new File(
                csvPath,
                "controlpoints_route_" + id
        );

        try(var os = new FileOutputStream(path)) {
            is.transferTo(os);

            return String.format("CSV: %s successfully uploaded ...", path.getName());
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            return "CSV not uploaded.";
        }
    }
}
