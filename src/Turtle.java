import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

import javax.imageio.ImageIO;

// Need a separate class for Turtle to separate the logic/data from graphics and animation

public class Turtle extends  JComponent{
private String name; 
private int LocX1, LocY1, LocX2, LocY2;
boolean writing;
boolean draw;
private Color color;
private double heading;
double angle;  // rotation angle  
private int delta;
private  BufferedImage bimg = null; // our PNG image of turtle


public Turtle(String name, int x, int y) {
	this.name= name;
	LocX1 = 0;
	LocY1 = 0;
	LocX2 = 0;
	LocY2 = 0;
	angle = 0; // rotation angle 
	writing = true; // default pen down
	color = Color.BLACK;     // default color
	heading = 90;           // default facing north, 90 degrees
	delta = 0;
	draw = true;
	try {
        bimg =  ImageIO.read(new File("./Images/TurtleBlue.png"));
         } catch (Exception e) {
      e.printStackTrace();
    }
}


public String getName()
{
	return name;
}

public boolean getdraw()
{
	return draw;
}

public void setDraw(boolean b)
{
	draw = b;
}

public  BufferedImage getImage()
{
	return bimg;
}



public void setDelta(int d)
{
	delta = d;
}

public int getDelta()
{
	return delta;
}

void rotateTurtle()
{
	AffineTransformOp op;
    AffineTransform tx;
    
    int w= bimg.getWidth();
    int h= bimg.getHeight();
    
	tx = new AffineTransform();
    tx.rotate(Math.toRadians(angle), w/2, h/2);//

    op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
    bimg=op.filter(bimg,null); //(sourse,destination)	
}

public boolean  getWriting() {
	
	return writing;
}

public void setLocX1(int x) {
	LocX1 = x;
}

public int getLocX1()
{
	return LocX1;
}


public void setLocY1(int y) {
	LocY1 = y;
}

public int getLocY1()
{
	return LocY1;
}

public void setLocX2(int x) {
	LocX2 = x;
}

public int getLocX2()
{
	return LocX2;
}


public void setLocY2(int y) {
	LocY2 = y;
}

public int getLocY2()
{
	return LocY2;
}

public void TurnLeft(double angle) {
	this.angle = -angle;   
	heading += angle;
	heading = heading % 360;
}

public void TurnRight(double angle) {
	this.angle = angle;
	heading -= angle;
	if (heading < 0) heading = heading + 360;	
}

public void MoveForward(int delta, int FrameW, int FrameH) {
	LocX2 = LocX1 + (int) (delta * Math.cos(Math.toRadians(heading)));
    LocY2 = LocY1 + (int) (delta * Math.sin(Math.toRadians(heading)));
    
    if (LocX2 > FrameW)    // Boundary checks and clipping
    	LocX2 = FrameW;
    if (LocX2 < -FrameW)
    	LocX2 = -FrameW;
    
    if (LocY2 > FrameH)    // Boundary checks and clipping
    	LocY2 = FrameH;
    if (LocY2 < -FrameH)
    	LocY2 = -FrameH;
    
}

public void penDown()
{
	writing = true;
}

public void penUp() 
{
	writing = false;
}

public void setColor(Color c)
{
	color = c;
}

public Color getColor()
{
return color;
}

}