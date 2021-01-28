package ru.varasoft.engineercalculator;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

class ExpressionHelper extends TreeImpl<String> implements Parcelable {
    private StringBuilder expression = new StringBuilder();

    private int cursor;

    Node<Token> root = null;

    String[] tokensStringArray;

    private BigDecimal result;

    protected ExpressionHelper(Parcel in) {
        super(255);
        cursor = in.readInt();
        expression = new StringBuilder(in.readString());
        String resultString = in.readString();
        if (resultString != null) {
            result = new BigDecimal(resultString);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cursor);
        dest.writeString(expression.toString());
        if (result != null) {
            dest.writeString(result.toString());
        }
    }

    public static final Creator<ExpressionHelper> CREATOR = new Creator<ExpressionHelper>() {
        @Override
        public ExpressionHelper createFromParcel(Parcel in) {
            return new ExpressionHelper(in);
        }

        @Override
        public ExpressionHelper[] newArray(int size) {
            return new ExpressionHelper[size];
        }
    };

    public BigDecimal getResult() {
        return result;
    }

    private void fillTokensStringArray() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(expression.toString());
        lexicalAnalyzer.scanInputString();
        tokensStringArray = lexicalAnalyzer.getTokensStringArray();
    }

    public StringBuilder getExpression() {
        fillTokensStringArray();
        StringBuilder tempBuilder = new StringBuilder();
        for (String token : tokensStringArray) {
            tempBuilder.append(token);
        }
        return tempBuilder;
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
        expression.delete(0, expression.length());
        cursor = 0;
    }

    public void setCursorToTheEnd() {
        cursor = expression.length();
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
        parseFormula();
        if (root != null) {
            return calculateNode(root);
        }
        return null;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
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
        fillTokensStringArray();
        if (tokensStringArray.length > 0) {
            StringBuilder tempBuilder = new StringBuilder();
            for (int i = 0; i < tokensStringArray.length - 1; i++) {
                tempBuilder.append(tokensStringArray[i]);
            }
            String lastToken = tokensStringArray[tokensStringArray.length - 1];
            if (lastToken.length() > 1 && ".0123456789".contains(lastToken.substring(0, 1))) {
                if (tokensStringArray.length == 1 || !tokensStringArray[tokensStringArray.length - 2].equals("-")) {
                    tempBuilder.append("-");
                    tempBuilder.append(lastToken);
                } else {
                    tempBuilder.deleteCharAt(tempBuilder.length() - 1);
                    tempBuilder.append(lastToken);
                }
            } else {
                tempBuilder.append(lastToken);
            }
            expression = tempBuilder;
            cursor = expression.length();
        }
    }

    public void tryToPlacePoint() {
        expression.append(".");
    }

    public void backspace() {
        fillTokensStringArray();
        if (tokensStringArray.length > 0) {
            StringBuilder tempBuilder = new StringBuilder();
            for (int i = 0; i < tokensStringArray.length - 1; i++) {
                tempBuilder.append(tokensStringArray[i]);
            }
            String lastToken = tokensStringArray[tokensStringArray.length - 1];
            if (!"_()*/+-".contains(lastToken.substring(0, 1))) {
                tempBuilder.append(lastToken.substring(0, lastToken.length() - 1));
            }
            expression = tempBuilder;
            cursor = expression.length();
        }
    }


}
