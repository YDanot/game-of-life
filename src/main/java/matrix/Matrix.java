package matrix;


import java.util.List;
import java.util.stream.Stream;

public interface  Matrix<E> {

    List<E> adjacentElementOf(Position position);

    List<Position> adjacentPositionOf(Position position);

    void put(E element, Position position) ;

    E get(Position p);

    Stream<Cell<E>> cellStream();

    int width();

    int height();

    class Cell<E> {
        final Position position;
        final E element;

        public Cell(Position position, E element) {
            this.position = position;
            this.element = element;
        }

        public Position position() {
            return position;
        }

        public E element() {
            return element;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "position=" + position +
                    ", element=" + element +
                    '}';
        }
    }

}
