package at.htl.controller;

import at.htl.model.Route;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;
import static org.assertj.db.output.Outputs.output;

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
        Route r1 = new Route("route1", 145.3);

        assertThat(table).hasNumberOfRows(0);

        routeRepository.save(r1);

        table = new Table(source, "route");
        output(table).toConsole();

        assertThat(table)
                .hasNumberOfRows(1)
                .row(0)
                .column("name")
                .value()
                .isEqualTo("route1");
    }
}