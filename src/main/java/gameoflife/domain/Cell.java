package gameoflife.domain;

public enum Cell {
    ALIVE{
        @Override
        public String toString() {
            return "*";
        }
    },DEAD{
        @Override
        public String toString() {
            return ".";
        }
    }


}
