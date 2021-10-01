package at.htl.controller;

import at.htl.model.Route;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
<<<<<<< HEAD
=======
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
>>>>>>> main
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RouteRepository implements PanacheRepository<Route> {

<<<<<<< HEAD
    @Transactional
    public void save(Route route) {
        if (route != null) {
            route.persist();
        }
    }

    public List<Route> getAllRoutes() {
        return findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Route route) {
        route.delete();
    }

    public Route findRouteWithId(Long id) {
        return findById(id);
    }

=======
    public static String routeFile = "/routes.csv";

    public Route findByCsvId(Long csvId){
        return find("csvid", csvId).firstResult();
    }

    @Transactional
    public void persistRoute() {
        List<Route> routes = generateRoutes();
        System.out.println(routes);
        for (Route r : routes) {
            persist(r);
        }
    }

    public List<Route> generateRoutes() {
        List<String[]> fileData = readDataFromFile(routeFile);
        List<Route> routes = new ArrayList<>();

        for (String[] routeString : fileData) {
            Route route = new Route();
            route.setCsvId(Long.parseLong(routeString[0]));
            route.setName(routeString[1]);
            route.setLength(Double.parseDouble(routeString[2]));

            routes.add(route);
        }

        return routes;
    }

    public List<String[]> readDataFromFile(String fileName) {

        InputStream is = getClass().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        return br
                .lines()
                .skip(1)
                .map(s -> s.split(";"))
                .collect(Collectors.toUnmodifiableList());

        /*List<String[]> fileData = new ArrayList<>();

        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        assert url != null;
        try {
            List<String> listOfLines = Files.readAllLines(Paths.get(url.getPath()), StandardCharsets.UTF_8);
            fileData = listOfLines
                    .stream()
                    .skip(1)
                    .distinct()
                    .map(s -> s.split(";"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;*/

    }
>>>>>>> main
}
