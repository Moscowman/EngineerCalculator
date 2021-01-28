package ru.varasoft.engineercalculator;

import java.util.ArrayDeque;
import java.util.Deque;

class SyntaxisAnalyzer {

    private final Token[] tokens;

    public SyntaxisAnalyzer(String inputString, Token[] tokens) {
        this.tokens = tokens;
    }

    public int checkForParenthesisAfterFunctions() {
        String[] functions = {"_sin", "_cos", "_tan", "_sqrt"};
        for (int i = 0; i < tokens.length; i++) {
            Token token = tokens[i];
            boolean isFunction = false;
            for (String function : functions) {
                if (token.getPresentation().equals(function)) {
                    isFunction = true;
                    break;
                }
            }
            if (isFunction && (i == tokens.length - 1 || !tokens[i + 1].getPresentation().equals("("))) {
                throw new GrammarAnalysisException(String.format("Inconsistent parenthesis at %d position", i), i);
            }
        }
        return -1;
    }

    public int checkForParenthesesConsistency(Token[] tokens) {
        Deque<String> parenthesesStack = new ArrayDeque<>();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].getPresentation().equals("(")) {
                parenthesesStack.push("(");
            } else if (tokens[i].getPresentation().equals(")")) {
                if (parenthesesStack.size() == 0 || !parenthesesStack.pop().equals("(")) {
                    throw new GrammarAnalysisException(String.format("Inconsistent parenthesis at %d position", i), i);
                }
            }
        }
        if (parenthesesStack.size() == 0) {
            return -1;
        } else {
            throw new GrammarAnalysisException(String.format("Inconsistent parenthesis at %d position", tokens.length), tokens.length);
        }
    }

    private Token[] subArray(Token[] tokens, int first, int last) {
        Token[] newTokens = new Token[last - first + 1];
        if (last + 1 - first >= 0)
            System.arraycopy(tokens, first, newTokens, 0, last + 1 - first);
        return newTokens;
    }

    private int getPriority(Token token) {
        String presentation = token.getPresentation();
        if (presentation.equals("+") || presentation.equals("-")) {
            return 3;
        }
        if (presentation.equals("*") || presentation.equals("/")) {
            return 2;
        }
        if (presentation.equals("_sin") || presentation.equals("_cos")
                || presentation.equals("_tan") || presentation.equals("_sqrt")) {
            return 1;
        }
        if (presentation.equals("(")) {
            return 0;
        }
        return -1;
    }

    public Node<Token> parse(Token[] tokens) throws GrammarAnalysisException {
        int result = checkForParenthesisAfterFunctions();
        if (result != -1) return null;

        result = checkForParenthesesConsistency(tokens);
        if (result != -1) return null;
        return parseRecursive(tokens);
    }


    private Node<Token> parseRecursive(Token[] tokens) throws GrammarAnalysisException {

        if (tokens.length == 0) {
            return null;
        }

        if (tokens[0].getPresentation().equals("(") && tokens[tokens.length - 1].getPresentation().equals(")")) {
            Token[] subTokens = subArray(tokens, 1, tokens.length - 2);
            if (checkForParenthesesConsistency(subTokens) == -1)
                return parseRecursive(subTokens);
        }

        int maxPriority = -2;
        int position = -1;
        boolean hasSign = tokens[0].getPresentation().equals("-")
                || tokens[0].getPresentation().equals("+");

        for (int i = hasSign ? 1 : 0; i < tokens.length; i++) {
            int tokenPriority = getPriority(tokens[i]);
            if (tokenPriority > maxPriority) {
                maxPriority = tokenPriority;
                position = i;
            }
            if (tokenPriority == 0) {
                Deque<String> parenthesesStack = new ArrayDeque<>();
                int j;
                for (j = i; j < tokens.length; j++) {
                    if (tokens[j].getPresentation().equals("(")) {
                        parenthesesStack.push("(");
                    } else if (tokens[j].getPresentation().equals(")")) {
                        parenthesesStack.pop();
                        if (parenthesesStack.size() == 0) {
                            break;
                        }
                    }
                }
                i = j;
            }
            if ("*/+-".contains(tokens[i].getPresentation()) && i < tokens.length - 1 && "+-".contains(tokens[i + 1].getPresentation())) {
                i++;
            }
        }

        if (position == -1) {
            if (tokens.length > 1) {
                throw new GrammarAnalysisException("Ошибка в выражении", 0);
            } else {
                Node<Token> node = new Node<>(tokens[0]);
                return node;
            }
        }

        if (hasSign && maxPriority <= 1) {
            if (tokens[0].getPresentation().equals("-")) {
                Node<Token> node = new Node<>(new Token("_neg", tokens[0].getPositionInString()));
                node.setLeftChild(parseRecursive(subArray(tokens, 1, tokens.length - 1)));
                return node;
            } else {
                return parseRecursive(subArray(tokens, 1, tokens.length - 1));
            }
        }

        if (maxPriority == 1) {
            Deque<String> parenthesesStack = new ArrayDeque<>();
            int j;
            for (j = position + 1; j < tokens.length; j++) {
                if (tokens[j].getPresentation().equals("(")) {
                    parenthesesStack.push("(");
                } else if (tokens[j].getPresentation().equals(")")) {
                    parenthesesStack.pop();
                    if (parenthesesStack.size() == 0) {
                        break;
                    }
                }
            }
            Node<Token> node = new Node<>(tokens[position]);
            node.setLeftChild(parseRecursive(subArray(tokens, position + 2, j - 1)));
            return node;
        } else {
            Node<Token> node = new Node<>(tokens[position]);
            if (position > 0 && position < tokens.length - 1) {
                node.setLeftChild(parseRecursive(subArray(tokens, 0, position - 1)));
            }
            if (position < tokens.length - 1) {
                node.setRightChild(parseRecursive(subArray(tokens, position + 1, tokens.length - 1)));
            }
            return node;
        }
    }
}