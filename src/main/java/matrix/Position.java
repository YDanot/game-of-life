package matrix;


public class Position {

    private final int line;
    private final int column;

    private Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public static Position of(int line, int column) {
        return new Position(line, column);
    }

    int line() {
        return line;
    }

    int column() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        return line == position.line && column == position.column;
    }

    @Override
    public int hashCode() {
        int result = line;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "{" + line + "," + column + '}';
    }
}
