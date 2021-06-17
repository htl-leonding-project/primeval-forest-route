package at.htl.control;

import at.htl.model.Route;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RouteRepo {

    private static final String FILE_NAME = "routes.csv";

    @Inject
    EntityManager em;



    @Transactional
    public Route save(Route route) {
        return em.merge(route);
    }

    public List<Route> findAll() {
        return em.createNamedQuery("Route.findAll", Route.class).getResultList();
    }

    public Route findById(Long id) {
        return em.find(Route.class, id);
    }

    public void delete(Long id) {
        em.remove(findById(id));
    }
}
