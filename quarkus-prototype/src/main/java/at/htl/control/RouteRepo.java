package at.htl.control;

import at.htl.model.Route;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class RouteRepo {

    private static final String FILE_NAME = "";

    @Inject
    EntityManager em;

    @Transactional
    public Route save(Route route) {
        return em.merge(route);
    }


}
