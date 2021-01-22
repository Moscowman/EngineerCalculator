package ru.varasoft.engineercalculator;

class Token {
    private String presentation;
    private int length;
    private int positionInString;

    public Token(String presentation, int positionInString) {
        this.presentation = presentation;
        this.positionInString = positionInString;
        length = presentation.length();
    }
}
