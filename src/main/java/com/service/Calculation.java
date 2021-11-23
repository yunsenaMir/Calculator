package com.service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculation {
    private final String expression;
    private final String[] splitExpression;
    private final String[] operations;
    private String operand1;
    private String operand2;
    private String operator;
    private boolean isDecimal;

    private static final String PERCENT_CHAR = "%";
    private static final String ENTER_CHAR = " ";
    private static final String DOT_CHAR = ".";
    private static final String ERROR_MESSAGE = "error";

    public Calculation(String expression, String[] operations) {
        this.expression = expression;
        this.operations = operations;
        this.splitExpression = expression.split(ENTER_CHAR);
        this.operand1 = null;
        this.operand2 = null;
        this.operator = null;
        this.isDecimal = false;
    }

    public boolean isValidateInput() {
        if (splitExpression.length != 3)
            return false;

        setParametersValues();
        return operand1.length() != 0 && operator.length() != 0 && operand2.length() != 0
                && Arrays.stream(operations).noneMatch(x -> x.equals(operand1))
                && Arrays.stream(operations).noneMatch(x -> x.equals(operand2))
                && !operand1.equals(DOT_CHAR) && !operand2.equals(DOT_CHAR)
                && countChar(expression, PERCENT_CHAR) <= 1;
    }

    public boolean onlyOneOperand() {
        String[] items = expression.split(" ");
        return items.length == 1 && Arrays.stream(operations).noneMatch(x -> x.equals(items[0])) && !items[0].equals(".");
    }

    private void setParametersValues() {
        operand1 = splitExpression[0];
        operand2 = splitExpression[2];
        operator = splitExpression[1];
        isDecimal = operand1.contains(DOT_CHAR) || operand2.contains(DOT_CHAR);
    }

    private int countChar(String word, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(word);
        int n = 0;
        while (matcher.find()) {
            n++;
        }
        return n;
    }


    public String calculate() {
        switch (operator) {
            case "+":
                return isDecimalCalculation() ?
                        setIntegerFormatIfNeeded(calculatePercent(operand1, operand2) + calculatePercent(operand2, operand1)) :
                        String.valueOf(Integer.parseInt(operand1) + Integer.parseInt(operand2));
            case "-":
                return isDecimalCalculation() ?
                        setIntegerFormatIfNeeded(calculatePercent(operand1, operand2) - calculatePercent(operand2, operand1)) :
                        String.valueOf(Integer.parseInt(operand1) - Integer.parseInt(operand2));
            case "*":
                return expression.contains(PERCENT_CHAR) ?
                        setIntegerFormatIfNeeded(multiply()) :
                        isDecimal ? String.valueOf(Float.parseFloat(operand1) * Float.parseFloat(operand2)) :
                                String.valueOf(Integer.parseInt(operand1) * Integer.parseInt(operand2));
            case "÷":
                return calculatePercent(operand2, operand1) != 0 ?
                        setIntegerFormatIfNeeded(calculatePercent(operand1, operand2) / calculatePercent(operand2, operand1)) :
                        ERROR_MESSAGE;
            case "mode":
                return calculatePercent(operand2, operand1) != 0 ?
                        setIntegerFormatIfNeeded(calculatePercent(operand1, operand2) % calculatePercent(operand2, operand1)) :
                        ERROR_MESSAGE;
        }
        return "";
    }

    private boolean isDecimalCalculation() {
        return isDecimal || expression.contains(PERCENT_CHAR);
    }

    private Float calculatePercent(String operand1, String operand2) {
        if (operand1.contains(PERCENT_CHAR))
            return getNumberFromPercent(operand1) * Float.parseFloat(operand2) / 100;
        return Float.parseFloat(operand1);
    }

    private Float multiply() {
        return getNumberFromPercent(operand1) * getNumberFromPercent(operand2) / 100;
    }

    private Float getNumberFromPercent(String percentNumber) {
        if (percentNumber.contains(PERCENT_CHAR))
            return Float.parseFloat(percentNumber.substring(0, percentNumber.indexOf(PERCENT_CHAR)));
        return Float.parseFloat(percentNumber);
    }

    private String setIntegerFormatIfNeeded(float value) {
        String stringValue = String.valueOf(value);
        String decimalPart = stringValue.substring(stringValue.indexOf(DOT_CHAR) + 1);
        return !isDecimal && countChar(decimalPart, "0") == decimalPart.length() ?
                stringValue.substring(0, stringValue.indexOf(DOT_CHAR)) :
                stringValue;
    }
}
