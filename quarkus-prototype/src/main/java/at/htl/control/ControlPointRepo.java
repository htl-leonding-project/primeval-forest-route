package at.htl.control;

import at.htl.model.ControlPoint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.ldap.Control;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.out;

@ApplicationScoped
public class ControlPointRepo {

    private static final String FILE_NAME = "controlpoints.csv";

    @Inject
    EntityManager em;

    /*@Transactional
    public void readFromCsv() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(FILE_NAME);
        try {
            assert url != null;
            try (Stream<String> stream = Files.lines(Paths.get(url.getPath()), StandardCharsets.UTF_8)) {
                stream.skip(1)
                        .map(ControlPoint::new)
                        .peek(out::println)
                        .forEach(em::merge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public ControlPoint findById(Long id) {
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
    }
}
