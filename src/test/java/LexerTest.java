import expr.LexicalAnalyzer;
import expr.Token;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LexerTest {
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
        ArrayList<Map.Entry<Token, String>> entries = new ArrayList<>(tokens.entrySet());
        Random random = new Random();
        for (int len = 1; len < 100; len++) {
            for (int tries = 0; tries < 10; tries++) {
                List<Token> expected = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < len; k++) {
                    int ind = random.nextInt(tokens.size());
                    Map.Entry<Token, String> tokenStringEntry = entries.get(ind);
                    String snd;
                    if (tokenStringEntry.getKey() == Token.NUM) {
                        snd = random.nextInt() + "";
                    } else {
                        snd = tokenStringEntry.getValue();
                    }
                    expected.add(tokenStringEntry.getKey());
                    sb.append(" ").append(snd);
                }
                expected.add(Token.END);
                System.out.println(expected.toString());
                System.out.println(sb.toString());
                List<Token> actual = new LexicalAnalyzer(sb.toString()).analyze();
                Assert.assertEquals(expected, actual);
            }
        }
    }

    @Test
    public void cas() {
        System.out.println(- - -2147483647 + 1);
        System.out.println(-3 + -(-2));
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("((1000 --       23))*19-12-3-4");
        Token token;
        while ((token = lexicalAnalyzer.nextToken()) != Token.END) {
            System.out.println(token.toString());
        }
        System.out.println(new LexicalAnalyzer("((1000 --       23))*19-12-3-4").analyze().toString());
    }

    private String spaces() {
        String s = "";
        Random random = new Random();
        random.nextInt(10);
        return "";
    }
}
