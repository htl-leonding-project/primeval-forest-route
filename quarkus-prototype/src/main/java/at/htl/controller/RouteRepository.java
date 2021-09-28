package at.htl.controller;

import at.htl.model.Route;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class RouteRepository implements PanacheRepository<Route> {

    @Transactional
    public void save(Route route) {
        if (route != null && !route.isPersistent()) {
            route.persist();
        }
    }

}
