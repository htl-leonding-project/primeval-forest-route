package at.htl.control;

import at.htl.model.ControlPoint;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.ldap.Control;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.out;

@ApplicationScoped
public class ControlPointRepo implements PanacheRepository<ControlPoint> {

    @Inject
    EntityManager em;

    @Inject
    RouteRepo routeRepo;

    @Transactional
    void readControlPointsFromCSV(String fileName) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        br.lines()
                .skip(1)
                .map(this::parseCsvLine)
                .forEach(this::persist);
    }

    // TODO: parse the line mapped fom the reading
    private List<ControlPoint> parseCsvLine(String line) {

        List<ControlPoint> controlPoints = new LinkedList<>();

        String[] elements = line.split(";");

        ControlPoint controlPoint = new ControlPoint(
                elements[1],
                Double.parseDouble(elements[2]),
                Double.parseDouble(elements[3]),
                routeRepo.getRouteWithContrP(Long.parseLong(elements[4]))
        );
        return controlPoints;
    }
}
