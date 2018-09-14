package matrix;


import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Mask {

    private final Position positions[];

    private Mask(Position... positions) {
        this.positions = positions;
    }

    public static Mask adjacent(Position position) {
        return new Mask(
                Position.of(position.line() - 1, position.column()),
                Position.of(position.line() - 1, position.column() + 1),
                Position.of(position.line() - 1, position.column() - 1),

                Position.of(position.line(), position.column() - 1),
                Position.of(position.line() + 1, position.column() - 1),

                Position.of(position.line() + 1, position.column()),

                Position.of(position.line(), position.column() + 1),
                Position.of(position.line() + 1, position.column() + 1));

    }

    public List<Position> applyOn(Matrix matrixTab) {
        return Stream.of(positions).filter(p -> isValidOn(p, matrixTab)).collect(toList());
    }

    private boolean isValidOn(Position position, Matrix matrix) {
        return position.line() > 0
                && position.line() <= matrix.height()
                && position.column() > 0
                && position.column() <= matrix.width();
    }

}
