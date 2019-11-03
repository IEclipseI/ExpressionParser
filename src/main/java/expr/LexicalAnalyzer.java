package expr;

import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzer {
    private String input;
    private char cur;
    private List<Token> tokens;
    private int pos;
    private Token curToken;

    public LexicalAnalyzer(String input) {
        this.input = input + "\0";
        this.tokens = new ArrayList<>();
        this.pos = 0;
        this.curToken = null;
        this.cur = this.input.charAt(0);

    }

    public Token getCurToken() {
        return curToken;
    }

    public List<Token> analyze() {
        while (nextToken() != Token.END) {
            tokens.add(curToken);
        }
        tokens.add(Token.END);
        return tokens;
    }

    public Token nextToken() {
        skipWhitespaces();
        if (cur == '\0')
            return curToken = Token.END;
        switch (cur) {
            case '*':
                curToken = Token.ASTERISK;
                break;
            case '+':
                curToken = Token.PLUS;
                break;
            case '-':
                try {
                    nextChar();
                    getNum();
                    curToken = Token.NUM;
                } catch (LexerException e) {
                    prevChar();
                    curToken = Token.MINUS;
                }
                break;
            case '(':
                curToken = Token.LPAREN;
                break;
            case ')':
                curToken = Token.RPAREN;
                break;
            default:
                getNum();
                curToken = Token.NUM;
                break;
        }
        nextChar();
        return curToken;
    }

    private void getNum() {
        int start = pos;
        while (Character.isDigit(input.charAt(pos)))
            pos++;
        if (pos - start == 0)
            throw new LexerException("Unexpected symbol: " + input.charAt(pos));
        pos--;
    }

    private void skipWhitespaces() {
        while (Character.isWhitespace(input.charAt(pos))) {
            nextChar();
        }
    }

    private void prevChar() {
        cur = input.charAt(--pos);
    }

    private void nextChar() {
        cur = input.charAt(++pos);
    }

    public static class LexerException extends RuntimeException {
        public LexerException(String message) {
            super(message);
        }
    }
}
