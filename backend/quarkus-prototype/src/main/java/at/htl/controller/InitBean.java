package at.htl.controller;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class InitBean {

    @Inject
    ControlPointRepository controlPointRepository;

    @Inject
    RouteRepository routeRepository;

    @Inject
    GpxDataRepository gpxDataRepository;

    void onStart(@Observes StartupEvent event) {
        try {
            gpxDataRepository.persistGpxFromStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        routeRepository.persistRoute();
    }
}
