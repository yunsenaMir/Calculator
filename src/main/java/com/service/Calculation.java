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
    private String decimalPart;

    private static final String PERCENT_CHAR = "%";
    private static final String MINUS_CHAR = "-";
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
        this.decimalPart = null;
    }

    /*
     Checking if input expression is correct
    */
    public boolean isValidateInput() {
        if (splitExpression.length != 3)
            return false;

        setParametersValues();
        return operand1.length() != 0 && operator.length() != 0 && operand2.length() != 0
                && Arrays.stream(operations).noneMatch(x -> x.equals(operand1))
                && Arrays.stream(operations).noneMatch(x -> x.equals(operand2))
                && !operand1.equals(DOT_CHAR) && !operand2.equals(DOT_CHAR)
                && countChar(expression, PERCENT_CHAR) <= 1
                && countChar(expression, MINUS_CHAR) < 3;
    }

    /*
     Sectioning off operands and operator from the expression
    */
    public void setParametersValues() {
        operand1 = splitExpression[0];
        operand2 = splitExpression[2];
        operator = splitExpression[1];
        isDecimal = operand1.contains(DOT_CHAR) || operand2.contains(DOT_CHAR);
    }

    /*
     Checking if only one operand is entered (no operator and second operand)
    */
    public boolean onlyOneOperandIsEntered() {
        String[] items = expression.split(" ");
        return items.length == 1 && Arrays.stream(operations).noneMatch(x -> x.equals(items[0])) && !items[0].equals(".");
    }

    /*
     Counting the amount of occurrences of a regular expression through a text.
    */
    public int countChar(String word, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(word);
        int n = 0;
        while (matcher.find()) {
            n++;
        }
        return n;
    }

    /*
     Calculating the value using two operands and the operator
    */
    public String calculate() {
        switch (operator) {
            case "+":
                return setIntegerFormatIfNeeded(calculatePercent(true) + calculatePercent(false));
            case "-":
                return setIntegerFormatIfNeeded(calculatePercent(true) - calculatePercent(false));
            case "*":
                return expression.contains(PERCENT_CHAR) ?
                        setIntegerFormatIfNeeded(multiplyWithPercent()) :
                        setIntegerFormatIfNeeded(Float.parseFloat(operand1) * Float.parseFloat(operand2));
            case "÷":
                return calculatePercent(false) != 0 ?
                        setIntegerFormatIfNeeded(calculatePercent(true) / calculatePercent(false)) :
                        ERROR_MESSAGE;
            case "mode":
                return calculatePercent(false) != 0 ?
                        setIntegerFormatIfNeeded(calculatePercent(true) % calculatePercent(false)) :
                        ERROR_MESSAGE;
        }
        return "";
    }

    /*
     Calculating some percent from a number
    */
    public Float calculatePercent(boolean isFirstOperand) {
        String firstOperand = isFirstOperand ? operand1 : operand2;
        String secondOperand = isFirstOperand ? operand2 : operand1;
        if (firstOperand.contains(PERCENT_CHAR))
            return substringNumberFromPercent(firstOperand) * Float.parseFloat(secondOperand) / 100;
        return Float.parseFloat(firstOperand);
    }

    /*
     Calculating the multiplication with a percentage
    */
    public Float multiplyWithPercent() {
        return substringNumberFromPercent(operand1) * substringNumberFromPercent(operand2) / 100;
    }

    /*
     Removing percent char from a string
    */
    public Float substringNumberFromPercent(String percentNumber) {
        if (percentNumber.contains(PERCENT_CHAR))
            return Float.parseFloat(percentNumber.substring(0, percentNumber.indexOf(PERCENT_CHAR)));
        return Float.parseFloat(percentNumber);
    }

    /*
     Changing the float value to the integer value, if all decimal places are zero
    */
    public String setIntegerFormatIfNeeded(float value) {
        String stringValue = String.valueOf(value);
        decimalPart = stringValue.substring(stringValue.indexOf(DOT_CHAR) + 1);
        return isIntegerValue() ? stringValue.substring(0, stringValue.indexOf(DOT_CHAR)) :
                stringValue;
    }

    private boolean isIntegerValue() {
        return !isDecimal && countChar(decimalPart, "0") == decimalPart.length();
    }
}
