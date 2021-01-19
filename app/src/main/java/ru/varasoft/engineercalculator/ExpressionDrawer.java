package ru.varasoft.engineercalculator;

import android.graphics.Canvas;

public class ExpressionDrawer {
    private int x = 0;
    private int y = 0;

    ExpressionTree<String> expression;

    public ExpressionTree<String> getExpression() {
        return expression;
    }

    public void setExpression(ExpressionTree<String> expression) {
        this.expression = expression;
    }

    public void setFormula(String expressionString) {
        this.expression = new ExpressionTree<String>(expressionString);
    }

    public void draw(Canvas canvas) {

    }
}
