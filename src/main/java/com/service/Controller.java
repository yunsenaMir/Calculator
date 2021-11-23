package com.service;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


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

    @FXML
    public void initialize() {
        Button[] operands = {zero, one, two, three, four, five, six, seven, eight, nine, percent};
        String[] operations = {"+", "-", "*", "÷", "mode"};
        Button[] operationButtons = {plus, minus, multiply, division, mode};

        StringBuilder expression = new StringBuilder();


        for (int i = 0; i < operands.length; i++) {
            int finalI = i;
            operands[i].setOnAction(e -> {
                expression.append(finalI == 10 ? PERCENT_CHAR : finalI);
                String[] parts = expression.toString().split(" ");
                output.setText((parts[parts.length - 1]));
            });
        }

        for (int i = 0; i < operationButtons.length; i++) {
            int finalI = i;
            operationButtons[i].setOnAction(e -> {
                output.setText(operations[finalI]);
                expression.append(" ").append(output.getText()).append(" ");
            });
        }

        point.setOnAction(e -> {
            expression.append(".");
            String[] parts = expression.toString().split(" ");
            output.setText((parts[parts.length - 1]));
        });

        answer.setOnAction(e -> {
            Calculation calculation = new Calculation(expression.toString(), operations);
            if (calculation.isValidateInput())
                output.setText(calculation.calculate());
            else {
                if (calculation.onlyOneOperand())
                    return;
                output.setText("error");
                expression.delete(0, expression.length());
            }
        });

        clear.setOnAction(e -> {
            expression.delete(0, expression.length());
            output.setText("");
        });
    }
}