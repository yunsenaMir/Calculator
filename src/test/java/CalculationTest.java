import com.service.Calculation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculationTest {

    private final String[] operations = {"+", "-", "*", "÷", "mode"};
    private final String expression = "120 ÷ 25%";
    private final Calculation calculation = new Calculation(expression, operations);

    @BeforeEach
    private void setValues() {
        calculation.setParametersValues();
    }


    @Test
    public void checkValidateInput() {
        assertTrue(calculation.isValidateInput());
    }

    @Test
    public void onlyOneOperandIsEntered() {
        assertFalse(calculation.onlyOneOperandIsEntered());
    }


    @Test
    public void countChar() {
        assertEquals(1, calculation.countChar(expression, "%"));
    }

    @Test
    public void calculate() {
        assertEquals("4", calculation.calculate());
    }

    @Test
    public void calculatePercent() {
        assertEquals(30, calculation.calculatePercent(false));
    }

    @Test
    public void substringNumberFromPercent() {
        assertEquals(25.0f, calculation.substringNumberFromPercent("25%"));
    }

    @Test
    public void setIntegerFormatIfNeeded() {
        assertEquals("25", calculation.setIntegerFormatIfNeeded(25.00f));
    }
}
