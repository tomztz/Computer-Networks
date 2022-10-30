/* Program 3.7: a program to illustrate the use of type int */
/* Calculates the area and perimeter of a rectangle */
/* whose dimensions are specifed by the user */

import tcdIO.*; 

class RectangleProgram {
  public static void main (String args[]) {
    Terminal terminal;
    Rectangle shape;
    int area, perimeter;
    int len, wide;                       

    java.util.Properties prop;
    prop= System.getProperties();
    prop.put("tcd.io.terminalType", "record");
    prop.put("tcd.io.log.filename", "testlog");
    System.setProperties(prop);

    /* create an object to represent the terminal */
    terminal = new Terminal("Program 3.7");

    /* ask user for length of rectangle */
    len = terminal.readInt("Enter length of rectangle: ");

    /* ask user for width of rectangle */
    wide = terminal.readInt("Enter width of rectangle: ");

    /* create a new Rectangle with given dimensions */
    shape = new Rectangle(len, wide);
  
    /* ask the rectangle object for its area */ 
    area = shape.calculateArea();
    /* print out the area */
    terminal.println("The area of the rectangle is: " + area + ".");

    /* ask the rectangle obejct for its perimeter */
    perimeter = shape.calculatePerimeter();
    /* print out the perimeter */
    terminal.println("The perimeter of the rectangle is: " + perimeter + ".");
 }
}   
