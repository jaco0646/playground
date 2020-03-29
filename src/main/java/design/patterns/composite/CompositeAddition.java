package design.patterns.composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

    public class CompositeAddition implements Arithmetic {
        private final List<Arithmetic> operations = new ArrayList<>();

        public CompositeAddition(Arithmetic... arithmetics) {
            operations.addAll(Arrays.asList(arithmetics));
        }

        @Override
        public double compute() {
            return operations.stream().mapToDouble(Arithmetic::compute).sum();
        }

        @Override
        public void appendChild(Arithmetic arithmetic) {
            operations.add(arithmetic);
        }

        @Override
        public void removeChild(Arithmetic arithmetic) {
            operations.remove(arithmetic);
        }
    }
