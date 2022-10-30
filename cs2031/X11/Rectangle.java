/* Program 1.1: a class whose instances represent rectangles */
class Rectangle {
  /* declare the instance variables */
  private int length;  // used to store the length of the rectangle
  private int width;   // used to store the width of the rectangle

  /* declare a constructor to initialise */
  /* new instances of class Rectangle */
  public Rectangle(int l, int w) {
    length = l;      // store the value of l into length
    width = w;       // store the value of w into width
  }

  /* declare the other methods */
  /* a method to calculate the area of a rectangle */
  public int calculateArea() {
    return length * width;
  } 
 
  /* a method to calculate the perimeter of a rectangle */
  public int calculatePerimeter() {
    return (2 * length) + (2 * width);
  }
}
