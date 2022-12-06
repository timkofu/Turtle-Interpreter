package name.hamdan.turtleinterpreter;

import javax.swing.*;
import java.io.IOException;

/**
 * Turtle Interpreter
 */
public class App {

    public static void main(String[] args) throws IOException {
        JFrame f = new JFrame();
        TurtleInterpreter turtleInterpreter = new TurtleInterpreter();
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
