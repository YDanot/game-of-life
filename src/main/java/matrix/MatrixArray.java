package matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MatrixArray<E> implements Matrix<E> {

    private E mat[];
    private final int width;
    private final int height;


    public MatrixArray(int width, int height, Supplier<E> elementSupplier) {
        this.width = width;
        this.height = height;

        ArrayList<E> list = new ArrayList<>();
        for (int c = 0; c < width * height; c++) {
            list.add(elementSupplier.get());
        }
        mat = (E[]) list.toArray();
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


    public void put(E element, Position p) {
        mat[width * (p.line() - 1) + p.column() - 1] = element;
    }

    public E get(Position p) {
        return mat[width * (p.line() - 1) + p.column() - 1];
    }

    @Override
    public String toString() {
        String res = "";

        for (int i = 0; i < mat.length; i++) {
            if (i != 0 && i % width == 0) {
                res += "\n";
            }
            res += mat[i] + " ";
        }
        return res;
    }

    public Stream<Cell<E>> cellStream() {
        List<Cell<E>> cells = new ArrayList<>();
        int l = 1;
        for (int i = 0; i < mat.length; i++) {
            if (i != 0 && i % width == 0) {
                l++;
            }
            cells.add(new Cell<>(Position.of(l, (i % width) + 1), mat[i]));
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
