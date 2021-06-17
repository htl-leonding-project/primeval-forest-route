package at.htl.control;

import at.htl.model.Route;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class RouteRepo implements PanacheRepository<Route> {

    private static final String FILE_NAME = "";

    @Inject
    EntityManager em;

    @Transactional
    public Route save(Route route) {
        return em.merge(route);
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
