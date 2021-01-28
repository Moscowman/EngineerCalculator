package ru.varasoft.engineercalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

class ExpressionHelper<E> extends TreeImpl<E> {
    private StringBuilder expression = new StringBuilder("(456 + 3)/(6555 * 33 - 7) + 88 * 14");
    private int cursor;
    Node root = null;

    static {
    }

    public ExpressionHelper(int MAX_DEPTH) {
        super(MAX_DEPTH);
        setCursorToTheEnd();
    }

    public ExpressionHelper(String expression) {
        super(255);
        this.expression = new StringBuilder(expression);
        parseFormula();
        setCursorToTheEnd();
    }

    public void addString(String stringToAdd) {
        expression.insert(cursor, stringToAdd);
        cursor += stringToAdd.length();
    }

    public void clearFormula() {
        expression.delete(0, expression.length() - 1);
    }

    public void setCursorToTheEnd() {
        cursor = expression.length() - 1;
    }

    void parseFormula() {
        String inputString = expression.toString();
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(inputString);
        lexicalAnalyzer.scanInputString();
        SyntaxisAnalyzer syntaxisAnalyzer = new SyntaxisAnalyzer(inputString, lexicalAnalyzer.getTokensArray());
        try {
            root = syntaxisAnalyzer.parse(lexicalAnalyzer.getTokensArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public BigDecimal calculate() {
        if (root != null) {
            return calculateNode(root);
        }
        return null;
    }

    private BigDecimal calculateNode(Node<Token> node) {
        BigDecimal leftResult = null;
        BigDecimal rightResult = null;
        Node<Token> leftChild = node.getLeftChild();
        if (leftChild != null) {
            leftResult = calculateNode(leftChild);
        }
        Node<Token> rightChild = node.getRightChild();
        if (rightChild != null) {
            rightResult = calculateNode(rightChild);
        }
        return calculateOperator(node, leftResult, rightResult);
    }

    BigDecimal calculateOperator(Node<Token> node, BigDecimal leftResult, BigDecimal rightResult) {
        switch (node.getValue().getPresentation()) {
            case "_sin":
                return BigDecimal.valueOf(sin(leftResult.doubleValue()));
            case "_cos":
                return BigDecimal.valueOf(cos(leftResult.doubleValue()));
            case "_tan":
                return BigDecimal.valueOf(tan(leftResult.doubleValue()));
            case "_sqrt":
                return BigDecimal.valueOf(sqrt(leftResult.doubleValue()));
            case "_neg":
                return leftResult.negate();
            case "_pi":
                return BigDecimal.valueOf(PI);
            case "_e":
                return BigDecimal.valueOf(E);
            case "+":
                return leftResult.add(rightResult);
            case "-":
                return leftResult.subtract(rightResult);
            case "*":
                return leftResult.multiply(rightResult);
            case "/":
                try {
                    return leftResult.divide(rightResult, 10, RoundingMode.HALF_EVEN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            default:
                return new BigDecimal(node.getValue().getPresentation());
        }
    }

    public void tryToInvertSign() {
    }

    public void tryToPlacePoint() {
    }

    public void backspace() {
    }
}
