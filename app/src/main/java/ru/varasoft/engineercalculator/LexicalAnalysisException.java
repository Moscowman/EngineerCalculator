package ru.varasoft.engineercalculator;

class LexicalAnalysisException extends RuntimeException{
    public int position;

    public LexicalAnalysisException(String message, int position) {
        super(message);
        this.position = position;
    }
}
