package ru.varasoft.engineercalculator;

import java.util.ArrayList;

class LexicalAnalyzer {
    private final String inputString;

    public String[] getTokensArray() {
        return tokensArrayList.toArray(new String[0]);
    }

    String[] functions = {"_pi","_sin","_cos","_tan","_sqrt","_e"};


    private final ArrayList<String> tokensArrayList = new ArrayList<>();


    public LexicalAnalyzer(String inputString) {
        this.inputString = inputString;
    }

    public void scanInputString() throws LexicalAnalysisException {
        int currentPosition = 0;
        while (currentPosition < inputString.length()) {
            switch (inputString.charAt(currentPosition)) {
                case ' ':
                    currentPosition++;
                    break;
                case '_':
                    boolean found = false;
                    for (String function: functions) {
                        if (inputString.regionMatches(currentPosition, function, 0, function.length())) {
                            tokensArrayList.add(function);
                            found = true;
                            currentPosition+=function.length();
                            break;
                        }
                    }
                    if (!found) throw new LexicalAnalysisException(String.format("Unexpected symbols starting at %d", currentPosition), currentPosition);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                case ')':
                    tokensArrayList.add(inputString.substring(currentPosition,currentPosition + 1));
                    currentPosition++;
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.':
                    String digitsAndPoint = "0123456789.";
                    int pointsCount = 0;
                    int position = currentPosition;
                    while (position < inputString.length()) {
                        String symbolAtPosition = inputString.substring(position, position + 1);
                        if (digitsAndPoint.contains(symbolAtPosition)) {
                            if (symbolAtPosition.equals(".")) {
                                pointsCount++;
                                if (pointsCount > 1) {break;}
                            }
                        } else {break;}
                        position++;
                    }
                    if (pointsCount <= 1) {
                        tokensArrayList.add(inputString.substring(currentPosition, position));
                        currentPosition = position;
                        break;
                    } else {
                        throw new LexicalAnalysisException(String.format("Number can not contains more than one point (at position %d)", position), position);
                    }
                default:
                    throw new LexicalAnalysisException(String.format("Unexpected symbols starting at %d", currentPosition), currentPosition);
            }
        }
    }
}
