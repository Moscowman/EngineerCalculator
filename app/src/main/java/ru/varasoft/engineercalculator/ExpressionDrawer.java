package ru.varasoft.engineercalculator;

import android.graphics.Canvas;

class ExpressionDrawer {
    private int x = 0;
    private int y = 0;

    ExpressionHelper<String> expression;

    public ExpressionHelper<String> getExpression() {
        return expression;
    }

    public void setExpression(ExpressionHelper<String> expression) {
        this.expression = expression;
    }

    public void setFormula(String expressionString) {
        this.expression = new ExpressionHelper<String>(expressionString);
    }

    public void draw(Canvas canvas) {

    }
}
