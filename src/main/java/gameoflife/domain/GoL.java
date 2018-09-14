package gameoflife.domain;

import matrix.domain.Matrix;
import matrix.domain.MatrixSupplier;

public class GoL {

    private final Matrix<Cell> matrix;

    public GoL(Matrix<Cell> matrix) {
        this.matrix = matrix;
    }

    public GoL nextGeneration() {
        Matrix<Cell> nextGeneration = new MatrixSupplier<Cell>().getInstance(matrix.width(), matrix.height(), () -> Cell.DEAD);

        matrix.cellStream().forEach(c -> {
            long aliveAdjacentCell = matrix.adjacentElementOf(c.position()).stream().filter(cell -> cell.equals(Cell.ALIVE)).count();

            if (c.element().equals(Cell.DEAD)) {
                if (aliveAdjacentCell == 3) {
                    nextGeneration.put(Cell.ALIVE, c.position());
                }
            } else {
                if (aliveAdjacentCell < 2) {
                    nextGeneration.put(Cell.DEAD, c.position());
                } else if (aliveAdjacentCell > 3) {
                    nextGeneration.put(Cell.DEAD, c.position());
                } else {
                    nextGeneration.put(Cell.ALIVE, c.position());
                }
            }
        });

        return new GoL(nextGeneration);
    }


    @Override
    public String toString() {
        return String.valueOf(matrix);
    }

    public Matrix<Cell> matrix() {
        return matrix;
    }
}
