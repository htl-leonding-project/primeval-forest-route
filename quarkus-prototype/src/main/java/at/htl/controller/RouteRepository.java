package at.htl.controller;

import at.htl.model.Route;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RouteRepository implements PanacheRepository<Route> {

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

}
