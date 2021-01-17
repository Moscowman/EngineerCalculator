package ru.varasoft.engineercalculator;

public class ExpressionTree<E> extends TreeImpl<E> {
    public final int FORMULA_IS_CORRECT = -1;
    private StringBuilder expression = new StringBuilder("(456 + 3)//(6555 * 33 - 7) + 88 * 14");
    private int cursor;

    public ExpressionTree(int MAX_DEPTH) {
        super(MAX_DEPTH);
        setCursorToTheEnd();
    }

    public ExpressionTree(String expression) {
        super(255);
        this.expression = new StringBuilder(expression);
        parseFormula();
        setCursorToTheEnd();
    }
    public void addString(String stringToAdd) {
        expression.insert(cursor,stringToAdd);
        cursor += stringToAdd.length();
    }
    
    public void clearFormula() {
        expression.delete(0, expression.length() - 1);
    }

    public void setCursorToTheEnd() {
        cursor = expression.length() - 1;
    }

    public int validateFormula() {
        return FORMULA_IS_CORRECT;
    }

    void parseFormula() {
        deleteTree();

    }

    public void calculate() {

    }

    public void tryToInvertSign() {
    }

    public void tryToPlacePoint() {
    }

    public void backspace() {
    }
}
