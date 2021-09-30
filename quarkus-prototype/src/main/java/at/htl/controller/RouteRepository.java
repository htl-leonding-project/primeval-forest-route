package at.htl.controller;

import at.htl.model.Route;

import javax.enterprise.context.ApplicationScoped;
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
public class RouteRepository {

    public static String routeFile = "routes.csv";

    public void persistRoute() {
        List<Route> routes = generateRoutes();

        System.out.println(routes.get(0));
    }

    public List<Route> generateRoutes() {
        List<String[]> fileData = readDataFromFile(routeFile);
        List<Route> routes = new ArrayList<>();
        Route route = new Route();

        for (int i = 0; i < fileData.size(); i++) {
            String[] routeString = fileData.get(i);
            route.setCsvId(Long.parseLong(routeString[0]));
            route.setName(routeString[1]);
            route.setLength(Double.parseDouble(routeString[2]));

            routes.add(route);
        }

        return routes;
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
