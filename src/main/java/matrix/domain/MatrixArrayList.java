package matrix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MatrixArrayList<E> implements Matrix<E> {

    private ArrayList<E> mat;
    private final int width;
    private final int height;

    MatrixArrayList(int width, int height, Supplier<E> elementSupplier) {
        this.width = width;
        this.height = height;
        mat = new ArrayList<>();
        for (int c = 0; c < width * height; c++) {
            mat.add(elementSupplier.get());
        }
    }

    public List<E> adjacentElementOf(Position position) {
        List<E> list = new ArrayList<>();
        List<Position> mask = adjacentPositionOf(position);
        for (Position p : mask) {
            list.add(get(p));
        }
        return list;
    }


    public List<Position> adjacentPositionOf(Position position) {
        Mask mask = Mask.adjacent(position);
        return mask.applyOn(this);
    }


    public void put(Cell<E> c) {
        mat.set(width * (c.position().line() - 1) + c.position().column() - 1, c.element());
    }

    public E get(Position p) {
        return mat.get(width * (p.line() - 1) + p.column() - 1);
    }

    @Override
    public String toString() {
        String res = "";

        for (int i = 0; i < mat.size(); i++) {
            if (i != 0 && i % width == 0) {
                res += "\n";
            }
            res += mat.get(i) + " ";
        }
        return res;
    }

    public Stream<Cell<E>> cellStream() {
        List<Cell<E>> cells = new ArrayList<>();
        int l = 1;
        for (int i = 0; i < mat.size(); i++) {
            if (i != 0 && i % width == 0) {
                l++;
            }
            cells.add(new Cell<>(Position.of(l,(i % width) + 1), mat.get(i)));
        }

        return cells.stream();
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }


}
