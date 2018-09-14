package gameoflife.domain;

import gameoflife.domain.Rules.Rule;
import matrix.domain.Matrix;
import matrix.domain.MatrixSupplier;

import static gameoflife.domain.Cell.ALIVE;
import static gameoflife.domain.Cell.DEAD;
import static gameoflife.domain.Rules.CLASSICS;

public class Environment {

    private final Matrix<Cell> matrix;
    private final Rules rules;

    public Environment(Matrix<Cell> matrix) {
        this.matrix = matrix;
        rules = CLASSICS;
    }

    private Environment(Matrix<Cell> matrix, Rules rules) {
        this.matrix = matrix;
        this.rules = rules;
    }

    public Environment nextGeneration() {
        Matrix<Cell> nextGeneration = new MatrixSupplier<Cell>().getInstance(matrix.width(), matrix.height(), () -> DEAD);

        matrix.cellStream().forEach(c -> nextGeneration.put(new Matrix.Cell<>(c.position(), evolve(c.element(), countLivingNeighbours(c)))));

        return new Environment(nextGeneration, rules);
    }

    private int countLivingNeighbours(Matrix.Cell<Cell> c) {
        return (int) matrix.adjacentElementOf(c.position()).stream().filter(cell -> cell.equals(ALIVE)).count();
    }

    private Cell evolve(Cell cell, int livingNeighboursNumber) {

        for (Rule rule : rules.get()) {
            cell = rule.apply(cell, livingNeighboursNumber);
        }

        return cell;
    }

    public Matrix<Cell> matrix() {
        return matrix;
    }

    @Override
    public String toString() {
        return String.valueOf(matrix);
    }

}
