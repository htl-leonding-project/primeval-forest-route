package at.htl.controller;

import at.htl.model.*;

import at.htl.model.ControlPoint;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class ControlPointRepository implements PanacheRepository<ControlPoint> {

    @ConfigProperty(name = "efr.csv.path")
    String csvPath;

    @Inject
    GpxDataRepository gpxDataRepository;

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
        List<ControlPoint> controlPoints = generateControlPointsFromStart(gpxData);
        //System.out.println(controlPoints);
        for (ControlPoint controlPoint : controlPoints) {
            saveControlPoint(controlPoint);
        }

        return controlPoints;
    }

    @Transactional
    public List<ControlPoint> persistControlPointsByUpload(GpxData gpxData, String csvPath) {
        List<ControlPoint> controlPoints = generateControlPointsFromUpload(gpxData, csvPath);

        for (ControlPoint controlPoint : controlPoints) {
            saveControlPoint(controlPoint);
        }

        return controlPoints;
    }

    public List<ControlPoint> generateControlPointsFromStart(GpxData gpxData) {
        List<String[]> fileData = readDataFromFile(controlPointsFile);
        List<ControlPoint> controlPoints = new ArrayList<>();
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

    public List<ControlPoint> generateControlPointsFromUpload(GpxData gpxData, String csvPath) {
        List<String[]> fileData = readDataFromFile(csvPath);
        List<ControlPoint> controlPoints = new ArrayList<>();

        for (String[] controlPointString : fileData) {
            ControlPoint controlPoint = new ControlPoint();

            controlPoint.setName(controlPointString[0]);
            controlPoint.setLatitudeCoordinate(Double.parseDouble(controlPointString[1].replaceAll("\\s", "")));
            controlPoint.setLongitudeCoordinate(Double.parseDouble(controlPointString[2].replaceAll("\\s", "")));
            controlPoint.setGpxData(gpxData);

            controlPoints.add(controlPoint);
        }
        return controlPoints;
    }

    public List<String[]> readDataFromFile(String fileName) {

        InputStream is = getClass().getResourceAsStream(fileName);
        BufferedReader br = null;
        if (is != null) {
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        }

        List<String[]> allLines = br
                .lines()
                .skip(1)
                .map(s -> s.split(";"))
                .collect(Collectors.toUnmodifiableList());
        System.out.println(allLines);

        return allLines;
    }

    @Transactional
    public String importCsvCp(InputStream is, Long id) {
        var path = new File(
                csvPath,
                "controlpoints_route" + id + ".csv"
        );

        try(var os = new FileOutputStream(path)) {
            is.transferTo(os);

            GpxData gpxData = gpxDataRepository.findById(id);
            System.out.println(gpxData);
            gpxData.setControlPoints(persistControlPointsByUpload(gpxData,
                    "/uploadedCsv/" + "controlpoints_route" + id + ".csv"));
            getEntityManager().merge(gpxData);

            return String.format("CSV: %s successfully uploaded ...", path.getName());
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            return "CSV not uploaded.";
        }
    }
}
