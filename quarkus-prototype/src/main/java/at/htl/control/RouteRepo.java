package at.htl.control;

import at.htl.model.ControlPoint;
import at.htl.model.Route;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class RouteRepo implements PanacheRepository<Route> {

    private static final String FILE_NAME = "";

    @Inject
    EntityManager em;

    @Inject
    RouteRepo routeRepo;

    @Transactional
    void readRoutesFromCSV(String fileName) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        br.lines()
                .skip(1)
                .map(this::parseCsvLine)
                .peek(System.out::println)
                .forEach(em::merge);
    }

    // TODO: parse the line mapped fom the reading
    private List<ControlPoint> parseCsvLine(String line) {

        List<ControlPoint> controlPoints = new LinkedList<>();

        String[] elements = line.split(";");

        ControlPoint controlPoint = new ControlPoint(
                elements[1],
                Double.parseDouble(elements[2]),
                Double.parseDouble(elements[3]),
                routeRepo.getRouteWithId(Long.parseLong(elements[4]))
        );
        return controlPoints;
    }

    /*public List<Route> findAll() {
        return em.createQuery("Route.findAll", Route.class).getResultList();
    }*/

    public Route findById(Long id) {
        return em.find(Route.class, id);
    }

    public void delete(Long id) {
        em.remove(findById(id));
    }

    public Route getRouteWithId(Long id) {
        Query query = em.createQuery(
                "select r from Route r where r.id = :id", Route.class
        ).setParameter("id", id);

        return (Route) query.getSingleResult();
    }

        /*@Transactional
    public void readFromCsv() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(FILE_NAME);
        try {
            assert url != null;
            try (Stream<String> stream = Files.lines(Paths.get(url.getPath()), StandardCharsets.UTF_8)) {
                stream.skip(1)
                        .map(Route::new)
                        .peek(out::println)
                        .forEach(em::merge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
