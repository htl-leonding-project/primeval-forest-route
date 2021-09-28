package at.htl.controller;

import at.htl.model.Route;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class RouteRepositoryTest {

    final Source source = new Source("jdbc:postgresql://localhost:5432/db", "app", "app");

    @Test
    @Transactional
    void t01_saveRouteToDB() {
        RouteRepository routeRepository = new RouteRepository();
        Table table = new Table(source, "route");
        Route r1 = new Route("r1", 123.3);

        assertThat(table).hasNumberOfRows(0);

        routeRepository.save(r1);

        assertThat(table).hasNumberOfRows(1);
        assertThat(table)
                .row(1)
                .column("name")
                .value()
                .isEqualTo("r1");
    }
}