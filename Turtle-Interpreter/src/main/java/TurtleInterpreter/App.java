package TurtleInterpreter;

import java.io.IOException;
import TurtleInterpreter.domain.Interpreter;

/**
 * Turtle Interpreter
 */
public class App {

    public static void main(String[] args){
        Interpreter  p = new Interpreter();
        try {
            p.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}
