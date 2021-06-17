package at.htl.control;

import at.htl.model.ControlPoint;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.ldap.Control;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.out;

@ApplicationScoped
public class ControlPointRepo implements PanacheRepository<ControlPoint> {

    private static final String FILE_NAME = "";

    @Inject
    EntityManager em;

    @Transactional
    void readControlPointsFromCSV(String fileName) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        br.lines()
                .skip(1)
                .map(this::parseCsvLine)
                .forEach(out::println);
    }

    // TODO: parse the line mapped fom the reading
    private String[] parseCsvLine(String line) {
        return null;
    }

    /*public ControlPoint findById(Long id) {
        return em.find(ControlPoint.class, id);
    }

    public List<ControlPoint> findAll() {
        return em.createNamedQuery("ControlPoint.findAll", ControlPoint.class).getResultList();
    }

    @Transactional
    public ControlPoint save(ControlPoint controlPoint) {
        return em.merge(controlPoint);
    }

    @Transactional
    public void delete(Long id) {
        em.remove(findById(id));
    }*/
}
