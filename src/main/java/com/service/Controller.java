package com.service;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Arrays;


public class Controller {
    @FXML
    private Button answer;

    @FXML
    private Button percent;

    @FXML
    private Button mode;

    @FXML
    private Button clear;

    @FXML
    private Button division;

    @FXML
    private Button eight;

    @FXML
    private Button five;

    @FXML
    private Button four;

    @FXML
    private Button minus;

    @FXML
    private Button multiply;

    @FXML
    private Button nine;

    @FXML
    private Button one;

    @FXML
    private TextField output;

    @FXML
    private Button plus;

    @FXML
    private Button point;

    @FXML
    private Button seven;

    @FXML
    private Button six;

    @FXML
    private Button three;

    @FXML
    private Button two;

    @FXML
    private Button zero;

    private static final String PERCENT_CHAR = "%";
    private static final String MINUS_CHAR = "-";
    private static final String ENTER_CHAR = " ";
    private static final String DOT_CHAR = ".";
    private static final String ERROR_MESSAGE = "error";

    @FXML
    public void initialize() {
        Button[] operands = {zero, one, two, three, four, five, six, seven, eight, nine, percent};
        String[] operandNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "%"};
        String[] operations = {"+", "*", "÷", "mode"};
        Button[] operationButtons = {plus, multiply, division, mode};

        StringBuilder expression = new StringBuilder();

        /*
          Action when clicking on operands button
         */
        for (int i = 0; i < operands.length; i++) {
            int finalI = i;
            operands[i].setOnAction(e -> {
                expression.append(finalI == 10 ? PERCENT_CHAR : finalI);
                String[] parts = expression.toString().split(ENTER_CHAR);
                output.setText((parts[parts.length - 1]));
            });
        }

        /*
          Action when clicking on operation buttons
         */
        for (int i = 0; i < operationButtons.length; i++) {
            int finalI = i;
            operationButtons[i].setOnAction(e -> {
                output.setText(operations[finalI]);
                expression.append(ENTER_CHAR).append(output.getText()).append(ENTER_CHAR);
            });
        }

        /*
          Action when the minus button is pressed
         */
        minus.setOnAction(e -> {
                    String expressionValue = expression.toString();
                    String[] parts = expressionValue.split(ENTER_CHAR);
                    String lastPart = parts[parts.length - 1];
                    if (lastPart.equals(MINUS_CHAR))
                        return;
                    if (expressionValue.length() == 0) {
                        expression.append(MINUS_CHAR);
                        output.setText(MINUS_CHAR);
                        return;
                    }
                    if (Arrays.asList(operandNumbers).contains(lastPart.substring(lastPart.indexOf("-") + 1)))
                        expression.append(ENTER_CHAR).append(MINUS_CHAR).append(ENTER_CHAR);
                    parts = expression.toString().split(ENTER_CHAR);
                    output.setText(parts[parts.length - 1]);
                }
        );

        /*
          Action when the dot button is pressed
         */
        point.setOnAction(e -> {
            expression.append(DOT_CHAR);
            String[] parts = expression.toString().split(ENTER_CHAR);
            output.setText((parts[parts.length - 1]));
        });

        /*
          Action when the answer button is pressed
         */
        answer.setOnAction(e -> {
            Calculation calculation = new Calculation(expression.toString(), operations);
            if (calculation.isValidateInput()) {
                output.setText(calculation.calculate());
                expression.delete(0, expression.length());
                expression.append(output.getText());
            } else {
                if (calculation.onlyOneOperandIsEntered())
                    return;
                output.setText(ERROR_MESSAGE);
                expression.delete(0, expression.length());
            }
        });

        /*
          Action when the cleaning button is pressed
         */
        clear.setOnAction(e -> {
            expression.delete(0, expression.length());
            output.setText("");
        });
    }
}