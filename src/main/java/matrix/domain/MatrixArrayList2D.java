package matrix.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MatrixArrayList2D<E> implements Matrix<E> {

    private ArrayList<ArrayList<E>> mat;

    public MatrixArrayList2D(int width, int height, Supplier<E> elementSupplier) {
        mat = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            ArrayList<E> line = new ArrayList<>(width);
            for (int l = 0; l < height; l++) {
                line.add(elementSupplier.get());
            }
            mat.add(line);
        }
    }

    public List<E> adjacentElementOf(Position position) {
        List<E> list = new ArrayList<>();
        List<Position> mask = adjacentPositionOf(position);
        for (Position position1 : mask) {
            list.add(mat.get(position1.line() - 1).get(position1.column() - 1));
        }
        return list;
    }


    public List<Position> adjacentPositionOf(Position position) {
        return Mask.adjacent(position).applyOn(this);
    }


    public void put(E element, Position position) {
        mat.get(position.line() - 1).set(position.column() - 1, element);
    }

    public E get(Position position) {
        return mat.get(position.line() - 1).get(position.column() - 1);
    }

    @Override
    public String toString() {
        String res = "";
        for (ArrayList<E> lines : mat) {
            for (E cell : lines) {
                res += cell + " ";
            }
            res += "\n";
        }
        return res;
    }

    public Stream<Cell<E>> cellStream() {
        List<Cell<E>> cells = new ArrayList<>();
        int l = 0;
        int c = 0;
        for (ArrayList<E> lines : mat) {
            for (E e : lines) {
                cells.add(new Cell<>(Position.of(l + 1, c + 1), e));
                c++;
            }
            l++;
            c = 0;
        }
        return cells.stream();
    }

    @Override
    public int width() {
        return mat.size();
    }

    @Override
    public int height() {
        return mat.get(0).size();
    }


}
