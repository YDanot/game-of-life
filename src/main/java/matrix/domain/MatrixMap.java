package matrix.domain;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MatrixMap<E> implements Matrix<E> {

    private final Map<Position, E> positionCellMap;
    private final Supplier<E> supplier;
    private final int width;
    private final int height;

    public MatrixMap(int width, int height, Supplier<E> elementSupplier) {
        this.width = width;
        this.height = height;
        this.supplier = elementSupplier;
        positionCellMap = generatePosition(width, height);
    }

    private Map<Position, E> generatePosition(int width, int height) {
        Map<Position, E> positionCellMap = new HashMap<>();
        for (int column = 1; column <= width; column++) {
            for (int line = 1; line <= height; line++) {
                positionCellMap.put(Position.of(line, column), supplier.get());
            }
        }
        return positionCellMap;
    }

    @Override
    public List<E> adjacentElementOf(Position position) {
        return positionCellMap.keySet().stream()
                .filter(adjacent(position))
                .limit(8)
                .map(positionCellMap::get)
                .collect(toList());
    }

    @Override
    public List<Position> adjacentPositionOf(Position position) {
        return positionCellMap.keySet().stream()
                .filter(adjacent(position))
                .limit(8)
                .collect(toList());
    }

    private Predicate<Position> adjacent(Position position) {
        return p -> Math.abs(position.line() - p.line()) <= 1
                &&
                Math.abs(position.column() - p.column()) <= 1
                && !p.equals(position);
    }

    @Override
    public void put(E element, Position position) {
        positionCellMap.put(position, element);
    }

    @Override
    public E get(Position p) {
        return positionCellMap.get(p);
    }

    @Override
    public Stream<Matrix.Cell<E>> cellStream() {
        return positionCellMap.keySet().stream().map(p -> new Matrix.Cell<>(p, get(p)));
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public String toString() {
        String res = "";
        for (int l = 1; l <= height; l++) {
            for (int c = 1; c <= width; c++) {
                res += get(Position.of(l, c)) + " ";
            }
            res += "\n";
        }
        return res.substring(0, res.length()-1);
    }

}
