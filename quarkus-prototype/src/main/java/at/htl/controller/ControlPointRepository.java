package at.htl.controller;


import at.htl.model.ControlPoint;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.io.IOException;
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

    public static String controlPointsFile = "controlpoints.csv";

    @Transactional
    public void saveControlPoint(ControlPoint controlPoint) {
        if (controlPoint != null) {
            persist(controlPoint);
        }
    }

    public void persistCsvIntoDb() {
        List<String[]> controlPoints = readDataFromFile(controlPointsFile);

        System.out.println(controlPoints.size());
    }

    public List<String[]> readDataFromFile(String fileName) {
        List<String[]> fileData = new ArrayList<>();

        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        assert url != null;
        try (Stream<String> stream = Files.lines(Paths.get(url.getPath()), StandardCharsets.UTF_8)) {
            fileData = stream
                    .skip(1)
                    .distinct()
                    .map(s -> s.split(";"))
                    .collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;

    }
}
