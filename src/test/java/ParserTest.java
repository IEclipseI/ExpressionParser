import expr.ExpressionParser;
import expr.Token;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static expr.ExpressionParser.Node;

public class ParserTest {
    Map<Token, String> tokens = Map.of(
            Token.ASTERISK, "*",
            Token.MINUS, "-",
            Token.PLUS, "+",
            Token.LPAREN, "(",
            Token.RPAREN, ")",
            Token.NUM, ""
//            expr.Token.END, ""
    );

    @Test
    public void randomTest() {
        for (int i = 0; i < 10000; i++) {
            Node gen = ExprTreeGenerator.gen();
            System.out.println(gen);
            test(gen.toString(), gen);
        }
    }

    @Test
    public void test1() {
        test("-(2 + 3 * 4)");
    }

    @Test
    public void test2() {
        test("- -3 + 3");
    }

    @Test
    public void num() {
        test("1");
        test("-1");
    }

    @Test
    public void debug() {
        String cur = ExprTreeGenerator.gen().toString();
        System.out.println(cur);
        test(cur);
//        test("- -1325818116+ -684749768-( 267155074)");
    }


    private void test(String expr) {
        Node parse = new ExpressionParser(expr).parse();
//        TreeVisualizator.show(parse);
        System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
    }

    private void test(String expr, Node expected) {
        Node parsed = new ExpressionParser(expr).parse();
//        TreeVisualizator.show(parsed);
        if (!equals(parsed, expected))
            throw new AssertionError();
        System.out.println("––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––");
    }

    private boolean equals(Node n1, Node n2) {
        boolean res = n1.value.equals(n2.value);
        if (n1.ch.size() != n2.ch.size())
            return false;
        for (int i = 0; i < n1.ch.size(); i++) {
            res = res && equals(n1.ch.get(i), n2.ch.get(i));
        }
        return res;
    }


    private String spaces() {
        String s = "";
        Random random = new Random();
        random.nextInt(10);
        return "";
    }
}
