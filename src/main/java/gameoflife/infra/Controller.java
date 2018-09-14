package gameoflife.infra;

import gameoflife.domain.Cell;
import gameoflife.domain.GoL;
import matrix.domain.Matrix;
import matrix.domain.MatrixSupplier;
import matrix.domain.Position;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

import static gameoflife.domain.Cell.ALIVE;
import static gameoflife.domain.Cell.DEAD;

@RestController
public class Controller {

    private GoL gol;

    public Controller() {
        Matrix<Cell> matrix = new MatrixSupplier<Cell>().getInstance(10, 10, () -> new Random().nextBoolean() ? ALIVE: DEAD);
        gol = new GoL(matrix);
    }

    @RequestMapping(value = "/gol", method = RequestMethod.GET)
    public String ng() {
        gol = gol.nextGeneration();
        return toJson(gol.matrix()).toString();
    }

    private JSONArray toJson(Matrix<Cell> matrix) {
        JSONArray lines = new JSONArray();
        for (int lineIndex = 1; lineIndex <= matrix.width(); lineIndex++) {
            String line ="";
            for (int column = 1; column <= matrix.height(); column++) {
                line+= matrix.get(Position.of(lineIndex,column)) +" ";
            }
            lines.put(line);
        }

        return lines;
    }
}
