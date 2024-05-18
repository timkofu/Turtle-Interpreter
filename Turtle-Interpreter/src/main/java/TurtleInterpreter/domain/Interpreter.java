package TurtleInterpreter.domain;

import TurtleInterpreter.userInterface.Turtle;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.IOException;

public class Interpreter extends JPanel implements ActionListener {
    private static final int FrameWidth = 800;
    private static final int FrameHeight = 800;
    private LinkedList<Turtle> turtleList;
    private final BufferedImage image;
    private Scanner in = null;
    private boolean redraw;
    private javax.swing.Timer timer;
    private String[] currentCommand;
    private Iterator<String[]> commandIterator;

    public Interpreter() {
        turtleList = new LinkedList<>();
        setPreferredSize(new Dimension(FrameWidth, FrameHeight));
        image = new BufferedImage(FrameWidth, FrameHeight, BufferedImage.TYPE_INT_RGB);
        setMaximumSize(new Dimension(image.getWidth(), image.getHeight()));
        redraw = false;
        clear();
    }

    public void clear() {
        Graphics g = image.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    public void drawLine(Turtle t, int x1, int y1, int x2, int y2) {
        Graphics g = image.getGraphics();
        g.setColor(t.getColor());
        g.drawLine(x1, y1, x2, y2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
        if (!turtleList.isEmpty()) {
            for (Turtle t : turtleList) {
                g.drawImage(t.getImage(), (t.getLocX2() - 15) + FrameWidth / 2, -(t.getLocY2() + 15) + FrameHeight / 2,
                        30, 30, this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String s = event.getActionCommand();
        if (s.equals("Open Program File")) { // read input commands from a text file
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                try {
                    in = new Scanner(selectedFile);
                } catch (FileNotFoundException e1) {
                    System.err.println(e1.getMessage());
                }
            }
            // start over, clear screen and initialize Turtle List
            clear();
            turtleList = new LinkedList<>();
            processFile(in);
        }
    }

    public Color toColor(String s) {
        // Only five colors are allowed
        return switch (s) {
            case "red" -> Color.RED;
            case "blue" -> Color.BLUE;
            case "cyan" -> Color.CYAN;
            case "green" -> Color.GREEN;
            case "orange" -> Color.ORANGE;
            default -> Color.BLACK;
        };
    }

    void processFile(Scanner in) {
        int lineNumber = 1;
        final int MAX_LINES = 100; // maximum number of command lines
        LinkedList<String[]> commands = new LinkedList<>();

        while (in.hasNextLine() && lineNumber < MAX_LINES) {
            String[] line = in.nextLine().split(" ");
            if (line.length < 2 || line.length >= 4) {
                JOptionPane.showMessageDialog(this, "Line : " + lineNumber + " : Invalid command, Program Terminated");
                return;
            } else {
                commands.add(line);
            }
            lineNumber++;
        }

        commandIterator = commands.iterator();
        timer = new javax.swing.Timer(377, new TimerActionListener());
        timer.start();
    }

    private class TimerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (commandIterator.hasNext()) {
                currentCommand = commandIterator.next();
                executeCommand(currentCommand);
                if (redraw) {
                    repaint();
                }
            } else {
                timer.stop();
            }
        }
    }

    private void executeCommand(String[] line) {
        Iterator<Turtle> itr = turtleList.iterator();
        boolean done;
        switch (line[0]) {
            case "turtle":
                Turtle turtle = new Turtle(line[1], 0, 0); // New Turtle in Turtle coordinates at center
                turtleList.add(turtle);
                redraw = true;
                break;

            case "move":
                done = false;
                while (itr.hasNext() & !done) {
                    Turtle t = itr.next();
                    String name = t.getName();
                    if (name.equals(line[1])) {
                        done = true;
                        t.setDelta(Integer.parseInt(line[2]));
                        t.MoveForward(t.getDelta(), FrameWidth / 2, FrameHeight / 2);
                        if (t.getWriting()) {
                            drawLine(t, t.getLocX1() + FrameWidth / 2, -t.getLocY1() + FrameHeight / 2,
                                    t.getLocX2() + FrameWidth / 2, -t.getLocY2() + FrameHeight / 2);
                        }
                        t.setLocX1(t.getLocX2()); // update Turtle position
                        t.setLocY1(t.getLocY2());
                    }
                }
                redraw = true;
                break;

            case "left":
                done = false;
                while (itr.hasNext() & !done) {
                    Turtle t = itr.next();
                    String name = t.getName();
                    if (name.equals(line[1])) {
                        done = true;
                        t.TurnLeft(Math.abs(Double.parseDouble(line[2])));
                        t.rotateTurtle();
                    }
                }
                redraw = true;
                break;

            case "right":
                done = false;
                while (itr.hasNext() & !done) {
                    Turtle t = itr.next();
                    String name = t.getName();
                    if (name.equals(line[1])) {
                        done = true;
                        t.TurnRight(Math.abs(Double.parseDouble(line[2])));
                        t.rotateTurtle();
                    }
                }
                redraw = true;
                break;

            case "pen":
                done = false;
                while (itr.hasNext() & !done) {
                    Turtle t = itr.next();
                    String name = t.getName();
                    if (name.equals(line[1])) {
                        done = true;
                        if ((line[2]).equals("up"))
                            t.penUp();
                        else if ((line[2]).equals("down"))
                            t.penDown();
                    }
                }
                redraw = false;
                break;

            case "colour":
                done = false;
                while (itr.hasNext() & !done) {
                    Turtle t = itr.next();
                    String name = t.getName();
                    if (name.equals(line[1])) {
                        done = true;
                        t.setColor(toColor(line[2])); // change color
                    }
                }
                redraw = false;
                break;

            default:
                JOptionPane.showMessageDialog(this, "Invalid command, Program Terminated");
                timer.stop();
        }
    }

    public void run() throws IOException {
        JFrame f = new JFrame();
        JButton b = new JButton("Open Program File");

        Interpreter p = new Interpreter();

        p.add(b);
        b.addActionListener(p);
        f.add(p);
        f.setSize(FrameWidth, FrameHeight);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
    }
}
