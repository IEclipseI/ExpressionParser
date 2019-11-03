import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static expr.ExpressionParser.Node;

public class ExprTreeGenerator {
    private static Random random = new Random();

    private static Node n() {
        return new Node("n", Collections.emptyList());
    }

    public static Node gen() {
        return E();
    }

    private static Node E() {
        List<Supplier<List<Node>>> suppliers = List.of(
                () -> List.of(
                        T(),
                        E1()
                )
        );
        int i = random.nextInt(suppliers.size());
        return new Node("E", suppliers.get(i).get());
    }

    private static Node T() {
        List<Supplier<List<Node>>> suppliers = List.of(
                () -> List.of(
                        F(),
                        T1()
                )
        );
        int i = random.nextInt(suppliers.size());
        return new Node("T", suppliers.get(i).get());
    }

    private static Node E1() {
        List<Supplier<List<Node>>> suppliers = List.of(
                Collections::emptyList,
                Collections::emptyList,
                () -> List.of(
                        new Node("-", List.of()),
                        T(),
                        E1()
                ),
                () -> List.of(
                        new Node("+", List.of()),
                        T(),
                        E1()
                )
        );
        int i = random.nextInt(suppliers.size());
        return new Node("E'", suppliers.get(i).get());
    }

    private static Node F() {
        List<Supplier<List<Node>>> suppliers = List.of(
                () -> List.of(
                        n()
                ),
                () -> List.of(
                        n()
                ),
                () -> List.of(
                        n()
                ),
                () -> List.of(
                        new Node("-", List.of()),
                        F()
                ),
                () -> List.of(
                        new Node("(", List.of()),
                        E(),
                        new Node(")", List.of())
                )
        );
        int i = random.nextInt(suppliers.size());
        return new Node("F", suppliers.get(i).get());
    }

    private static Node T1() {
        List<Supplier<List<Node>>> suppliers = List.of(
                Collections::emptyList,
                Collections::emptyList,
                () -> List.of(
                        new Node("*", List.of()),
                        F(),
                        T1()
                )
        );
        int i = random.nextInt(suppliers.size());
        return new Node("T'", suppliers.get(i).get());
    }
}
