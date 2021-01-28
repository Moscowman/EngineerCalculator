package ru.varasoft.engineercalculator;

class GrammarAnalysisException extends RuntimeException{
    public int position;

    public GrammarAnalysisException(String message, int position) {
        super(message);
        this.position = position;
    }
}
