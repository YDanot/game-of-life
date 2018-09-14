package gameoflife;

import matrix.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static gameoflife.Cell.ALIVE;
import static gameoflife.Cell.DEAD;

public class GOLTests {

    private GoL gol;

    @Test
    public void circle_environment_example() {
        given_an_environment(
                ". . . . . . . . .",
                ". . . A A A . . .",
                ". . . . . . . . .",
                ". A . . . . . A .",
                ". A . . . . . A .",
                ". A . . . . . A .",
                ". . . . . . . . .",
                ". . . A A A . . .",
                ". . . . . . . . .");

        when_it_evolves();

        then_the_environment_should_be(
                ". . . . A . . . .",
                ". . . . A . . . .",
                ". . . . A . . . .",
                ". . . . . . . . .",
                "A A A . . . A A A",
                ". . . . . . . . .",
                ". . . . A . . . .",
                ". . . . A . . . .",
                ". . . . A . . . .");
    }

    @Test
    public void any_dead_cell_with_exactly_three_live_neighbours_becomes_a_live_cell() {

        given_an_environment(
                ". A .",
                "A . .",
                ". A .");
        when_it_evolves();
        then_center_cell_should_be(ALIVE);
    }

    private void then_center_cell_should_be(Cell cell) {
        Assertions.assertThat(gol.matrix().get(Position.of(2,2))).isEqualTo(cell);
    }

    @Test
    public void any_live_cell_with_two_or_three_live_neighbours_stay_alive() {
        given_an_environment(
                ". A .",
                "A A .",
                ". . .");
        when_it_evolves();
        then_center_cell_should_be(ALIVE);

        given_an_environment(
                "A A .",
                "A A .",
                ". . .");
        when_it_evolves();
        then_center_cell_should_be(ALIVE);
    }

    @Test
    public void any_live_cell_with_fewer_than_two_live_neighbours_dies() {
        given_an_environment(
                ". . .",
                ". A .",
                ". . .");
        when_it_evolves();
        then_center_cell_should_be(DEAD);

    }

    @Test
    public void any_live_cell_with_more_than_three_live_neighbours_dies() {
        given_an_environment(
                ". A .",
                "A A A",
                ". A .");
        when_it_evolves();
        then_center_cell_should_be(DEAD);

        given_an_environment(
                "A A .",
                "A A A",
                ". A .");
        when_it_evolves();
        then_center_cell_should_be(DEAD);

        given_an_environment(
                "A A A",
                "A A A",
                ". A .");
        when_it_evolves();
        then_center_cell_should_be(DEAD);

        given_an_environment(
                "A A A",
                "A A A",
                "A A .");
        when_it_evolves();
        then_center_cell_should_be(DEAD);

        given_an_environment(
                "A A A",
                "A A A",
                "A A A");
        when_it_evolves();
        then_center_cell_should_be(DEAD);
    }

    private void then_the_environment_should_be(String... lines) {
        Matrix<Cell> expectedMatrix = buildMatrix(lines);
        Assertions.assertThat(gol.matrix().toString()).isEqualTo(expectedMatrix.toString());
    }

    private void when_it_evolves() {
        gol = gol.nextGeneration();
    }

    private void given_an_environment(String... lines) {
        gol = new GoL(buildMatrix(lines));
    }

    private Matrix<Cell> buildMatrix(String... lines) {
        Matrix<Cell> cellMatrix = newMatrix(lines);

        for (int l = 1; l <= lines.length; l++) {
            String[] columns = lines[l - 1].split(" ");
            for (int c = 1; c <= columns.length; c++) {
                cellMatrix.put((columns[c - 1].equals(".") ? DEAD : ALIVE), Position.of(l, c));
            }
        }
        return cellMatrix;
    }

    private Matrix<Cell> newMatrix(String[] lines) {
        return new MatrixSupplier<Cell>().getInstance(lines[0].trim().split(" ").length, lines.length, () -> DEAD);
    }

}

