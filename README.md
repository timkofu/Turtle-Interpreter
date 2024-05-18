# Turtle-Interpreter
Simple Turtle Interpreter.

To run: `$ cd Turtle-Interpreter && make run`

A window should appear with button "Open Program File":
- Click on button and a dialog box should appear.
- Select from folder Programs any sample program.

The interpreter will go through the commands in file and move Turtles.

You can select more input files. You can also write your input file using an editor of your choice and see how it behaves.

```
Language commands:
- turtle name -> create a new turtle identified by the given name
- move name x -> moves the named turtle forward by x units
- left name x -> rotate the turtle anticlockwise by x degrees
- right name x -> rotate the turtle clockwise by x degrees
- pen name up -> lift the pen off the “paper”
- pen name down -> put the pen down so that subsequent moves draw of the screen
- color name c -> set the drawing color of the turtle appropriately
   - Possible colors: red, blue, cyan, orange and green. Default is black or argument is unspecified color. 
```