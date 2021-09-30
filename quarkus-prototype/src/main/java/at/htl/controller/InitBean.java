package at.htl.controller;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class InitBean {

    @Inject
    ControlPointRepository controlPointRepository;

    @Inject
    RouteRepository routeRepository;

    void onStart(@Observes StartupEvent event) {
        routeRepository.persistRoute();
        //controlPointRepository.persistCsvIntoDb();
    }
}
