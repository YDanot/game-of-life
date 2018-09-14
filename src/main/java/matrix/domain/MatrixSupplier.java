package matrix.domain;


import java.util.function.Supplier;

public class MatrixSupplier<E> {

    public Matrix<E> getInstance(int width, int height, Supplier<E> elementSupplier) {
        return new MatrixArray<>(width, height, elementSupplier);
    }
}
