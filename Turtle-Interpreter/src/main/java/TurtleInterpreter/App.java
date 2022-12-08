package TurtleInterpreter;

import javax.swing.*;

import TurtleInterpreter.domain.Interpreter;

import java.io.IOException;

/**
 * Turtle Interpreter
 */
public class App {

    public static void main(String[] args) throws IOException {
        JFrame f = new JFrame();
        Interpreter turtleInterpreter = new Interpreter();
        JButton b = new JButton("Open File");
        turtleInterpreter.add(b);
        b.addActionListener(turtleInterpreter);
        f.add(turtleInterpreter);
        f.setVisible(true);
        f.setSize(turtleInterpreter.getFrameHeight(), turtleInterpreter.getFrameWidth());
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
