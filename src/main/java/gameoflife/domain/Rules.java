package gameoflife.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static gameoflife.domain.Cell.ALIVE;
import static gameoflife.domain.Cell.DEAD;
import static gameoflife.domain.Rules.RuleSupplier.anyCell;

public class Rules {

    static Rules CLASSICS = new Rules(
            anyCell(DEAD).with(livingNeighbours1 -> livingNeighbours1 == 3).evolveTo(ALIVE),
            anyCell(ALIVE).with(livingNeighbours -> livingNeighbours < 2).evolveTo(DEAD),
            anyCell(ALIVE).with(livingNeighbours -> livingNeighbours > 3).evolveTo(DEAD),
            anyCell(ALIVE).with(livingNeighbours -> livingNeighbours == 3).evolveTo(ALIVE)
    );

    private final List<Rule> rules;

    private Rules(Rule... rules) {
        this.rules = Arrays.asList(rules);
    }

    public List<Rule> get() {
        return rules;
    }

    static class Rule {
        private final Predicate<Integer> predicate;
        private final Cell from;
        private final Cell to;

        Rule(Predicate<Integer> predicate, Cell from, Cell to) {
            this.predicate = predicate;
            this.from = from;
            this.to = to;
        }

        Cell apply(Cell c, int livingNeighbours) {
            if (c.equals(from)) {
                if (predicate == null || predicate.test(livingNeighbours)) {
                    return to;
                }
            }
            return c;
        }
    }

    static class RuleSupplier {

        private Predicate<Integer> livingNeighboursPredicate;
        private Cell from;
        private Cell to;

        private RuleSupplier() {
        }

        static RuleSupplier anyCell(Cell from) {
            return new RuleSupplier().withFrom(from);
        }

        RuleSupplier with(Predicate<Integer> livingNeighboursPredicate) {
            this.livingNeighboursPredicate = livingNeighboursPredicate;
            return this;
        }

        Rule evolveTo(Cell to) {
            this.to = to;
            return this.build();
        }

        private RuleSupplier withFrom(Cell from) {
            this.from = from;
            return this;
        }

        private Rule build() {
            return new Rule(livingNeighboursPredicate, from, to);
        }

    }
}
