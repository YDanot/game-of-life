package matrix;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MatrixTests {

    private Matrix<String> matrix;
    private List<Position> adjacentPositions;

    @Test
    public void top_left_corner() {
        given_a_board(2, 2);
        when_we_ask_for_adjacent_position_of(1, 1);
        then_system_should_return(position(1, 2), position(2, 1), position(2, 2));
    }

    @Test
    public void bottom_right_corner() {
        given_a_board(2, 2);
        when_we_ask_for_adjacent_position_of(2, 2);
        then_system_should_return(position(1, 2), position(2, 1), position(1, 1));
    }

    @Test
    public void bottom_left_corner() {
        given_a_board(2, 2);
        when_we_ask_for_adjacent_position_of(2, 1);
        then_system_should_return(position(1, 2), position(2, 2), position(1, 1));
    }

    @Test
    public void top_right_corner() {
        given_a_board(2, 2);
        when_we_ask_for_adjacent_position_of(1, 2);
        then_system_should_return(position(2, 2), position(2, 1), position(1, 1));
    }

    @Test
    public void top_left_corner_of_3x3_matrix() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(1, 1);
        then_system_should_return(position(1, 2), position(2, 2), position(2, 1));
    }

    @Test
    public void top_right_corner_of_3x3_matrix() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(1, 3);
        then_system_should_return(position(1, 2), position(2, 3), position(2, 2));
    }

    @Test
    public void bottom_right_corner_of_3x3_matrix() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(3, 3);
        then_system_should_return(position(2, 2), position(2, 3), position(3, 2));
    }

    @Test
    public void bottom_left_corner_of_3x3_matrix() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(3, 1);
        then_system_should_return(position(2, 1), position(2, 2), position(3, 2));
    }

    @Test
    public void center() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(2, 2);
        then_system_should_return(
                position(1, 1), position(1, 2), position(1, 3),
                position(2, 1), position(2, 3),
                position(3, 1), position(3, 2), position(3, 3));
    }

    @Test
    public void top_edge() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(1, 2);
        then_system_should_return(
                position(1, 1), position(1, 3),
                position(2, 1), position(2, 2), position(2, 3));
    }

    @Test
    public void right_edge() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(2, 3);
        then_system_should_return(
                position(1, 2), position(1, 3),
                position(2, 2),
                position(3, 2), position(3, 3));
    }

    @Test
    public void bottom_edge() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(3, 2);
        then_system_should_return(
                position(2, 1), position(2, 2), position(2, 3),
                position(3, 1), position(3, 3));
    }

    @Test
    public void left_edge() {
        given_a_board(3, 3);
        when_we_ask_for_adjacent_position_of(2, 1);
        then_system_should_return(
                position(1, 1), position(1, 2),
                position(2, 2),
                position(3, 1), position(3, 2));
    }

    @Test
    public void an_element_can_be_add_to_matrix() {
        given_a_board(12, 12);
        Position p = Position.of(4, 5);
        matrix.put("ELEMENT", p);
        assertThat(matrix.get(p)).isEqualTo("ELEMENT");
    }

    private Position position(int line, int column) {
        return Position.of(line, column);
    }


    private void then_system_should_return(Position... positions) {
        assertThat(adjacentPositions).containsOnlyElementsOf(Lists.newArrayList(positions));
    }

    private void when_we_ask_for_adjacent_position_of(int line, int column) {
        adjacentPositions = matrix.adjacentPositionOf(Position.of(line, column));
    }

    private Matrix<String> given_a_board(int width, int height) {
        matrix =  new MatrixSupplier<String>().getInstance(width, height,()->"");
        return matrix;
    }
}
