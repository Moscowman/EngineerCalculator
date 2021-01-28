package ru.varasoft.engineercalculator;

import android.widget.TextView;

import java.math.BigDecimal;

class ExpressionDrawer {
    private int x = 0;
    private int y = 0;
    TextView textView;
    ExpressionHelper expressionHelper;

    public ExpressionDrawer(TextView textView, ExpressionHelper expressionHelper) {
        this.textView = textView;
        this.expressionHelper = expressionHelper;
    }


    public ExpressionHelper getExpression() {
        return expressionHelper;
    }

    public void setExpresionHelper(ExpressionHelper expression) {
        this.expressionHelper = expression;
    }

    public void setFormula(String expressionString) {
        this.expressionHelper = new ExpressionHelper(expressionString);
    }

    public void draw() {
        StringBuilder stringBuilder = expressionHelper.getExpression();
        String string = stringBuilder.toString();
        string = string.replaceAll("_pi", "π");
        string = string.replaceAll("_sqrt", "√");
        string = string.replaceAll("[_]", "");
        BigDecimal result = expressionHelper.getResult();
        if (result != null) {
            string = String.format("%s\n\n= %s", string, result.toPlainString());
        }

        String error = expressionHelper.getError();

        if (error != null) {
            string = String.format("%s\n\n%s", string, error);
            expressionHelper.setError(null);
        }

        textView.setText(string);
    }
}
