//I, Shamik Bhesaniya, student number 000770928, certify that this material is my original work.
//No other person's work has been used without due acknowledgement and I have not made my work available to anyone else.”

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class holds the mathematical operation and value(ie. + 5).
 * Two attributes are read-only members of the class.
 * @author Shamik Bhesaniya
 */
public class Command {
    
    private final char operation;
    private final double value;

    public Command(char operation, double value) {
        this.operation = operation;
        this.value = value;
    }

    public char getOperation() {
        return operation;
    }

    public double getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.format("%c %s", operation, value);
    }
    
}
