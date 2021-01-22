package ru.varasoft.engineercalculator;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LexicalAnalizerUnitTest {
    @Test
    public void testDigit() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("5");
        lexicalAnalyzer.scanInputString();
        assertArrayEquals(new String[] {"5"}, lexicalAnalyzer.getTokensArray());
    }

    @Test
    public void testSimpleExpression() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("5 + 3");
        lexicalAnalyzer.scanInputString();
        assertArrayEquals(new String[] {"5", "+", "3"}, lexicalAnalyzer.getTokensArray());
    }

    @Test
    public void testComplexExpression() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("5 + (_cos(77.33)/77)");
        lexicalAnalyzer.scanInputString();
        assertArrayEquals(new String[] {"5", "+", "(", "_cos","(", "77.33",")", "/", "77", ")"}, lexicalAnalyzer.getTokensArray());
    }

    @Test(expected = LexicalAnalysisException.class)
    public void testIncorrectNumber() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("5 + (_cos(77.3.3)/77)");
        lexicalAnalyzer.scanInputString();
        lexicalAnalyzer.getTokensArray();
    }

    @Test(expected = LexicalAnalysisException.class)
    public void testWrongToken() {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("5 + (z+_cos(77.3.3)/77)");
        lexicalAnalyzer.scanInputString();
        lexicalAnalyzer.getTokensArray();
    }

}