package at.htl.controller;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Arrays;

@ApplicationScoped
public class InitBean {

    @Inject
    ControlPointRepository controlPointRepository;

    @Inject
    RouteRepository routeRepository;

    @Inject
    GpxDataRepository gpxDataRepository;

    void onStart(@Observes StartupEvent event) {
//        try {
//            gpxDataRepository.persistGpxFromStart();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        routeRepository.persistRoute();
        System.out.println(Arrays.toString(controlPointRepository.readDataFromFile("/uploadedCsv/controlpoints_route1.csv").get(0)));
     }
}
