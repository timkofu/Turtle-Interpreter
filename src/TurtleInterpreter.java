/* Simple Turtle Interpreter  
 * @ M. Hamdan (m.hamdan@hw.ac.uk)
 * Feb 2019
 * 
 */

import java.awt.Graphics; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.*;

public class TurtleInterpreter  extends JPanel implements ActionListener {
	
        private static final int FrameWidth = 800;
        private static final int FrameHeight = 800;
        private  List<Turtle> TurtleList;
        private BufferedImage image;
	    Scanner in = null;
	    boolean redraw; 
	 //   private JTextField console = new JTextField();
        
	public TurtleInterpreter() {
		//add(console); 
		TurtleList = new LinkedList<Turtle>();
		setPreferredSize(new Dimension(FrameHeight, FrameWidth));
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
		// render the image on the panel.
        g.drawImage(image, 0, 0, null);	
		   if (TurtleList.size() != 0) {
			for (Turtle t: TurtleList) {		
				g.drawImage(t.getImage(), (t.getLocX2()-15) + FrameWidth/2, -(t.getLocY2()+15) + FrameHeight/2, 30,30, this);
			}		
		  }
		}

  
public void actionPerformed(ActionEvent e)
        {
       String s = e.getActionCommand();
        if (s.equals("Open File")) {    // read input commands from a text file
         JFileChooser chooser = new JFileChooser();
    	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    		File selectedFile = chooser.getSelectedFile();
    		try {
		in = new Scanner(selectedFile);
		} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		} 
	 }   	
    	// start over, clear screen and initialise Turtle List 
    	clear();
    	TurtleList = new LinkedList<Turtle>();
    	processfile(in);
   }
  }
	
public Color ToColor(String s)
	{			// only five colors are allowed 
		if (s.equals("red") == true)  return Color.RED;
		    else if (s.equals("blue") == true)   return Color.BLUE;
		    else if (s.equals("cyan") == true)   return Color.CYAN;
		    else if (s.equals("green") == true)  return Color.GREEN;
		    else if (s.equals("orange") == true)  return Color.ORANGE;	
		return Color.BLACK;  //default 
	}
	       
void processfile(Scanner in) {  	 
	    int lineNumber = 1;
	    boolean terminate = false;
	    boolean done; 
		final int MAX_LINES = 100; // maximum number of command lines			
		while (in.hasNextLine() && lineNumber<MAX_LINES && terminate == false) {	
			String line[] = in.nextLine().split(" ");
			if (line.length <2 || line.length >= 4) {
				JOptionPane.showMessageDialog(this, "Line : " + lineNumber + " : Invalid command, Program Terminated");
		     
		     terminate = true;
			} else {
				Iterator itr = TurtleList.iterator();
				
				switch (line[0]) {
				case "turtle":
                 Turtle turtle = new Turtle(line[1], 0, 0); // New Turtle in Turtle coordinates at center 
                 TurtleList.add(turtle);          
                 redraw = true; 
				 break;	
				 
				case "move":
					done = false;
					while (itr.hasNext() & !done) {
						Turtle t = (Turtle) itr.next();
						 String name = t.getName();
						if (name.equals(line[1])) {
							done = true;
							t.setDelta(Integer.parseInt(line[2]));
							t.MoveForward(t.getDelta(), FrameWidth/2, FrameHeight/2);
							if (t.getWriting() == true) {	
						        drawLine(t, t.getLocX1() + FrameWidth/2, -t.getLocY1() + FrameHeight/2, t.getLocX2() + FrameWidth/2, -t.getLocY2() + FrameHeight/2);
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
						Turtle t = (Turtle) itr.next();
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
						Turtle t = (Turtle) itr.next();
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
						Turtle t = (Turtle) itr.next();
						 String name = t.getName();
						if (name.equals(line[1]) == true) {
							done = true;
							if ((line[2]).equals("up") == true) 
								t.penUp();
							else if ((line[2]).equals("down") ==  true)
								t.penDown();
						}
					}
					redraw = false;
					break;
					
				case "colour":
					done = false;
					while (itr.hasNext() & !done) {				
						Turtle t = (Turtle) itr.next();
						String name = t.getName();
						if (name.equals(line[1])) {
							done = true;
							t.setColor(ToColor(line[2])); // change color	
						}
					}
					redraw = false;
					break;
					
				default:
					JOptionPane.showMessageDialog(this, "Line " + lineNumber + " : Invalid command, Program Terminated");
					terminate = true; 
				}
			}
		
			if (redraw) repaint(); 
			lineNumber++;
		}  
	}	

public static void main(String args[]) throws IOException  {	
		JFrame f = new JFrame();
		TurtleInterpreter  p = new TurtleInterpreter(); 
		JButton b = new JButton("Open File"); 
		p.add(b);	
		b.addActionListener(p);	
		f.add(p);
		f.setVisible(true); 
		f.setSize(FrameHeight,FrameWidth);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
}