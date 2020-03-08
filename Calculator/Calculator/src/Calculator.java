//I, Shamik Bhesaniya, student number 000770928, certify that this material is my original work.
//No other person's work has been used without due acknowledgement and I have not made my work available to anyone else.”
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shamik Bhesaniya
 */
public class Calculator {
    
    // define commands queue to store calculator commands
    MQueue<Command> commands;
    /* define undo & redo stacks */
    MStack<Command> undo;
    MStack<Command> redo;
 
    public Calculator() {
        /* initialize all created data structures */
        this.commands = new MQueue<>();
        this.undo = new MStack<>();
        this.redo = new MStack<>();
    }
    
    /**
     * This method evaluate all the operations in the queue.
     * Further, it clears the queue and both undo & redo stacks
     * @return The evaluated final value (total) 
     */
    private double evaluate() {
        double total = 0d;
        int size = commands.size();
        for (int i = 0; i < size; i++) {
            Command cmd = commands.dequeue();
            total = doOperation(cmd, total);
        }
        /* clear the undo stack */
        for (int i = 0; i < undo.size(); i++) {
            undo.pop();
        }
        /* clear the redo stack */
        for (int i = 0; i < redo.size(); i++) {
            redo.pop();
        }
        return total;
    }
    
    /**
     * This method does the operation to the given command
     * @param c The command to be operated
     * @param total The current total 
     * @return the updated total
     */
    private double doOperation(Command c, double total){
        
        char operation = c.getOperation();
        double value = c.getValue();
        switch(operation) {
            case '+':
                total += value;
                break;
            case '-':
                total -= value;
                break;
            case '*':
                total *= value;
                break;
            case '/':
                total /= value;
                break;
            default:
                break;
        }
        return total;
    }
   
    
    /**
     * This method can return the opposite value of given operation
     * @param operation The given operation to be reversed
     * @return The reverse operation
     */
    private char getOppositeOperation(char operation) {
        char opposite = '\0';
        switch(operation) {
            case '+':
                opposite = '-';
                break;
            case '-':
                opposite = '+';
                break;
            case '*':
                opposite = '/';
                break;
            case '/':
                opposite = '*';
                break;
            default:
                break;
        }
        return opposite;
    }
    
    
    public static void main(String[] args) {
        
        // display the welcome note  
        System.out.println("COMP10152 - Lab#5 - Calculator using Queues and Stacks ... by _______________");
        System.out.println("Enter tokens. Legal tokens are integers, +, -, *, /, U[ndo], R[edo], E[valuate] and [e]X[it]\n");
        
        // create a calculator instance 
        Calculator calculator = new Calculator();
        
        // create a scanner instance to scan user inputs
        Scanner scanner = new Scanner(System.in);
        // read the first user input
        String input = scanner.nextLine().trim().toUpperCase();
        // initialize a variable for current total value of the calculator
        double total = 0d;
        
        while (!input.equals("X")) {
            // check whether the input calculator operation is in valid operation
            if(Pattern.matches("[*/+-][\\s]+[\\d]+[.]*[\\d]*", input)) {
                // add to the queue after seperating operation and value
                String[] commandData = input.split("[\\s]+");
                char operation = commandData[0].charAt(0);
                double value = Double.parseDouble(commandData[1]);
                // create a new Command instance
                Command command = new Command(operation, value);
                // add the created command to the operation queue
                calculator.commands.enqueue(command);
                // and, push it to the undo stack
                calculator.undo.push(command);
                // if no any command in the queue, set total to zero
                if(calculator.commands.isEmpty()) 
                    total = 0d;
                // calculate the current total doing the operation
                total = calculator.doOperation(command, total);
                
            } else { // for U[ndo], R[edo], E[valuate]
                switch(input) {
                    case "U":
                        if (calculator.undo.isEmpty()) {
                            System.out.println("ERROR --> Undo is empty - Can't Undo");
                        } else {
                            // get the previous command from undo stack
                            Command prevCmd = calculator.undo.pop(); 
                            char oppositeOpt = calculator.getOppositeOperation(prevCmd.getOperation());
                            total = calculator.doOperation(new Command(oppositeOpt, prevCmd.getValue()), total);
                            calculator.redo.push(prevCmd);
                        }
                        break;
                    case "R":
                        if (calculator.redo.isEmpty()) {
                            System.out.println("ERROR --> Redo is empty - Can't Redo");
                        } else {
                            /* proceed the redo process */
                            Command nextCmd = calculator.redo.pop();
                            total = calculator.doOperation(nextCmd, total);
                            calculator.undo.push(nextCmd);
                        }
                        break;
                    case "E":
                        total = calculator.evaluate();
                        break;
                    default:
                        // display error message
                        System.out.println("ERROR --> Invalid Token - line ignored");
                        break;
                }
            }
            
            // display the total value
            System.out.printf("Total = %s%n", total);
            // scann the next input line from user
            input = scanner.nextLine().trim().toUpperCase();
            
        }
        // when exiting from the calculator evaluate all queue operations
        total = calculator.evaluate();
        // display the total value
        System.out.printf("Total = %s%n", total);
    }
    
}
