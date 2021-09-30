package at.htl.controller;


import at.htl.model.ControlPoint;
import at.htl.model.Route;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void persistControlPoints() {
        List<ControlPoint> controlPoints = generateControlPoints();
        System.out.println(controlPoints);
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
            controlPoint.setLatitudeCoordinate(Double.parseDouble(controlPointString[2]));
            controlPoint.setLongitudeCoordinate(Double.parseDouble(controlPointString[3]));
            controlPoint.setRoute(rp.findByCsvId(Long.parseLong(controlPointString[4])));

            controlPoints.add(controlPoint);
        }

        return controlPoints;
    }

    public List<String[]> readDataFromFile(String fileName) {

        InputStream is = getClass().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        return br
                .lines()
                .skip(1)
                .map(s -> s.split(";"))
                .collect(Collectors.toUnmodifiableList());
    }

}
