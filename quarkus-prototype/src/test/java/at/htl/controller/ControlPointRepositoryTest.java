package at.htl.controller;

import at.htl.model.ControlPoint;
import at.htl.model.Route;
import org.assertj.db.type.Source;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

import static org.assertj.db.api.Assertions.assertThat;

class ControlPointRepositoryTest {

    final Source source = new Source("jdbc:postgresql://localhost:5432/db", "app", "app");

    @Test
    void t01_persistCPToDB() {

        /*ControlPointRepository controlPointRepository = new ControlPointRepository();
        Table table = new Table(source, "controlpoint");
        ControlPoint cp1 =
                new ControlPoint("cp1", 12.3, 45.6,
                new Route("r1", 123.2));

        assertThat(table).hasNumberOfRows(0);

        controlPointRepository.save(cp1);

        assertThat(table).hasNumberOfRows(1);*/

    }

}