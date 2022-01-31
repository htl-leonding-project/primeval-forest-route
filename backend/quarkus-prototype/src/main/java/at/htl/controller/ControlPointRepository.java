package at.htl.controller;

import at.htl.model.ControlPoint;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ControlPointRepository implements PanacheRepository<ControlPoint> {

    public static String controlPointsFile = "/controlpoints.csv";

    @Transactional
    public void saveControlPoint(ControlPoint controlPoint) {
        if (controlPoint != null) {
            persist(controlPoint);
        }
    }

    @Transactional
    public List<ControlPoint> getControlPointByRouteId(Long id) {
        List<ControlPoint> controlPoints = getAllControlpoints();
        List<ControlPoint> specificControlPoints = new ArrayList<>();

        for (ControlPoint controlPoint : controlPoints) {
            System.out.println(controlPoint);
        }

        for (int i = 0; i < controlPoints.toArray().length; i++) {
            if(Objects.equals(controlPoints.get(i).getRoute().getId(), id)) {
                specificControlPoints.add(controlPoints.get(i));
            }
        }

        return specificControlPoints;
    }

    @Transactional
    public List<ControlPoint> getAllControlpoints() {
        return findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Transactional
    public void persistControlPoints() {
        List<ControlPoint> controlPoints = generateControlPoints();
        //System.out.println(controlPoints);
        for (ControlPoint controlPoint : controlPoints) {
            persist(controlPoint);
        }
    }

    public List<ControlPoint> generateControlPoints() {
        List<String[]> fileData = readDataFromFile(controlPointsFile);
        List<ControlPoint> controlPoints = new ArrayList<>();
        RouteRepository rp = new RouteRepository();

        for (String[] controlPointString : fileData) {
            ControlPoint controlPoint = new ControlPoint();

            controlPoint.setName(controlPointString[1]);
            controlPoint.setLatitude(Double.parseDouble(controlPointString[2].replaceAll("\\s", "")));
            controlPoint.setLongitude(Double.parseDouble(controlPointString[3].replaceAll("\\s", "")));
            controlPoint.setRoute(rp.findByCsvId(Long.parseLong(controlPointString[4])));

            controlPoints.add(controlPoint);
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
}
