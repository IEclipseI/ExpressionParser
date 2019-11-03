package expr;

import lombok.AllArgsConstructor;

import java.util.*;

//FIRST
// F  = {n, -, (}
// T' = {ε, *}
// T  = {n, -, (}
// E' = {ε, -, +}
// E  = {n, -, (}

//FOLLOW
// F  = {*, +, -, $, )}
// T' = {+, -, ), $}
// T  = {+, -, ), $}
// E' = {), $}
// E  = {), $}


public class ExpressionParser {

    private final LexicalAnalyzer analyzer;

    public ExpressionParser(String expr) {
        this.analyzer = new LexicalAnalyzer(expr);
        analyzer.nextToken();
    }

    public Node parse() {
        return E();
    }

    private Node E() {
        Node res = new Node("E");
        Token token = analyzer.getCurToken();
        if (Set.of(Token.NUM, Token.MINUS, Token.LPAREN).contains(token)) {
            //E -> T E'
            Node t = T();
            res.ch.add(t);
            Node e1 = E1();
            res.ch.add(e1);
        } else {
            throw new ParsingException("Unexpected token: " + token);
        }
        return res;
    }


    private Node E1() {
        Node res = new Node("E'");
        Token token = analyzer.getCurToken();
        if (Objects.equals(Token.PLUS, token)) {
            //E' -> + T E'
            Node plus;
            if (token.equals(Token.PLUS)) {
                plus = new Node("+");
            } else {
                throw new ParsingException("Epected: +");
            }
            res.ch.add(plus);
            analyzer.nextToken();
            Node t = T();
            res.ch.add(t);
            Node e1 = E1();
            res.ch.add(e1);
        } else if (Objects.equals(Token.MINUS, token)) {
            //E' -> - T E'
            Node minus;
            if (token.equals(Token.MINUS)) {
                minus = new Node("-");
            } else {
                throw new ParsingException("Epected: -");
            }
            res.ch.add(minus);
            analyzer.nextToken();
            Node t = T();
            res.ch.add(t);
            Node e1 = E1();
            res.ch.add(e1);
        } else if (Set.of(Token.END, Token.RPAREN).contains(token)) {
            //E' -> ε
        } else {
            throw new ParsingException("Unexpected token: " + token);
        }
        return res;
    }

    private Node T() {
        Node res = new Node("T");
        Token token = analyzer.getCurToken();
        if (Set.of(Token.NUM, Token.MINUS, Token.LPAREN).contains(token)) {
            //T -> F T'
            Node f = F();
            res.ch.add(f);
            Node t1 = T1();
            res.ch.add(t1);
        } else {
            throw new ParsingException("Unexpected token: " + token);
        }
        return res;
    }

    private Node T1() {
        Node res = new Node("T'");
        Token token = analyzer.getCurToken();
        if (Objects.equals(Token.ASTERISK, token)) {
            //T' -> * F T'
            Node asterisk;
            if (token.equals(Token.ASTERISK)) {
                asterisk = new Node("*");
            } else {
                throw new ParsingException("Epected: *");
            }
            res.ch.add(asterisk);
            analyzer.nextToken();
            Node f = F();
            res.ch.add(f);
            Node t1 = T1();
            res.ch.add(t1);
        } else if (Set.of(Token.END, Token.RPAREN, Token.PLUS, Token.MINUS).contains(token)) {
            //E -> ε
        } else {
            throw new ParsingException("Unexpected token: " + token);
        }
        return res;
    }

    private Node F() {
        Node res = new Node("F");
        Token token = analyzer.getCurToken();
        if (Objects.equals(Token.NUM, token)) {
            //F -> n
            Node n = new Node("n");
            res.ch.add(n);
            analyzer.nextToken();
        } else if (Objects.equals(Token.LPAREN, token)) {
            //E -> (E)
            res.ch.add(new Node("("));
            analyzer.nextToken();
            Node e = E();
            res.ch.add(e);
            token = analyzer.getCurToken();
            if (token.equals(Token.RPAREN)) {
                res.ch.add(new Node(")"));
            } else {
                throw new ParsingException("expected: )");
            }
            analyzer.nextToken();
        } else if (Objects.equals(Token.MINUS, token)) {
            res.ch.add(new Node("-"));
            analyzer.nextToken();
            Node f = F();
            res.ch.add(f);
        } else {
            throw new ParsingException("Unexpected token: " + token);
        }
        return res;
    }

    @AllArgsConstructor
    public static class Node {
        public String value;
        public List<Node> ch;


        public Node(String value) {
            this.value = value;
            this.ch = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            if (ch.isEmpty()) {
                switch (value) {
                    case "n":
                        return " " + new Random().nextInt();
                    case "*":
                    case "(":
                    case ")":
                    case "+":
                    case "-":
                        return value;
                    default:
                        break;
                }
            }
            for (Node node : ch) {
                res.append(node.toString());
            }
            return res.toString();
        }
    }

    public static class ParsingException extends RuntimeException {
        public ParsingException(String message) {
            super(message);
        }
    }
}
