package gameoflife;

import matrix.Matrix;
import matrix.MatrixMap;
import matrix.MatrixSupplier;

import static gameoflife.Cell.ALIVE;
import static gameoflife.Cell.DEAD;

public class GoL {

    private final Matrix<Cell> matrix;

    public GoL(Matrix<Cell> matrix) {
        this.matrix = matrix;
    }

    public GoL nextGeneration() {
        Matrix<Cell> nextGeneration = new MatrixSupplier<Cell>().getInstance(matrix.width(), matrix.height(), () -> DEAD);

        matrix.cellStream().forEach(c -> {
            long aliveAdjacentCell = matrix.adjacentElementOf(c.position()).stream().filter(cell -> cell.equals(ALIVE)).count();

            if (c.element().equals(DEAD)) {
                if (aliveAdjacentCell == 3) {
                    nextGeneration.put(ALIVE, c.position());
                }
            } else {
                if (aliveAdjacentCell < 2) {
                    nextGeneration.put(DEAD, c.position());
                } else if (aliveAdjacentCell > 3) {
                    nextGeneration.put(DEAD, c.position());
                } else {
                    nextGeneration.put(ALIVE, c.position());
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
