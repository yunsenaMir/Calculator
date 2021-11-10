package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.Arrays;

public class Controller {
    @FXML
    private Button answer;

    @FXML
    private Pane calculator;

    @FXML
    private Button clear;

    @FXML
    private Button clearAll;

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
    private Text output;

    @FXML
    private Button percent;

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

    private final Button[] operands = {one, two, three, four, five, six, seven, eight, nine};
    private final String[] operations = {"+", "-", "*", "÷", "%"};
    private final Button[] operationButtons = {plus, minus, multiply, division, percent};

    @FXML
    public void initialize() {
        StringBuilder expression = new StringBuilder();
        for (int i = 0; i < operands.length; i++) {
            int finalI = i + 1;
            operands[i].setOnAction(e -> {
                output.setText(String.valueOf(finalI));
                expression.append(output.getText());
            });
        }

        point.setOnAction(e -> {
            output.setText(".");
            expression.append(output.getText());
        });

        for (int i = 0; i < operationButtons.length; i++) {
            int finalI = i;
            operationButtons[i].setOnAction(e -> {
                output.setText(" " + operations[finalI] + " ");
                expression.append(output.getText());
            });
        }

        answer.setOnAction(e -> {
            String[] items = expression.toString().split(" ");
            if (isValidate(items)) {
               output.setText(String.valueOf(calculate(items)));
            }
        });
    }

    private boolean isValidate(String[] items) {
        return items.length == 3 && Arrays.stream(operations).anyMatch(x -> x.equals(items[1]))
                && Arrays.stream(operands).anyMatch(x -> x.equals(items[0]))
                && Arrays.stream(operands).anyMatch(x -> x.equals(items[2]));
    }

    private double calculate(String[] items) {
        return 0;
    }
}
