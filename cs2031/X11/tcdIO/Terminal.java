/*
 * tcd.io Package
 * Copyright (c) 1997 Trinity College Dublin
 * All Rights Reserved.
 */
package tcdIO;

/*
 * $Id: Terminal.java,v 1.1.1.1 1999/01/21 19:48:29 sweber Exp $
 *
 * @author Stefan Weber
 * @version 0.1 $Revision: 1.1.1.1 $
 */

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.AWTEvent;
import java.awt.Font;
import java.awt.Frame;

/**
 * Provides a window with in/output methods
 *
 * @see     java.awt.Frame
 * @see     tcd.io.TerminalPanel
 */
public class Terminal extends Frame {

  protected TerminalPanel thePanel;

  /**
   * Creates a terminal window with the title 'Terminal'
   *
   */
  public Terminal() {
    this("Terminal");
  }

  /**
   * Creates a terminal window with
   * with a given title and
   * a default size
   *
   * @param     title Title of the terminal window
   */
  public Terminal(String title) {
    this(title, 40, 20);
  }

  /**
   * Creates a terminal window with
   * with a given title and size
   *
   * @param     title  the title
   * @param     width  the width of the frame
   * @param     height the height of the frame
   */
  public Terminal(String title, int width, int height) {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);	disableEvents(AWTEvent.MOUSE_EVENT_MASK);				  
    thePanel= new TerminalPanel(width, height);
    add(thePanel);
    validate();

    setTitle(title);
    pack();
    show();    
    System.runFinalizersOnExit(true);
  }


  //======================
  // closing method
  //======================

  protected void processWindowEvent(WindowEvent e) {
    /* If the window is closed end the program */
    if (e.getID()==WindowEvent.WINDOW_CLOSING) {
      thePanel= null;
      System.runFinalization();
      System.gc();
      System.exit(0);
    }
  }

  //======================
  // read methods
  //======================


  /**
   * Reads a character from the terminal
   *
   * @param s String that is shown before the input is accepted
   * @return Character that was entered
   */
  public synchronized char readChar(String s) {
    return thePanel.readChar(s);
  }

  /**
   * Reads a character from the terminal
   *
   * @return Character that was entered
   */
  public char readChar() {
    return thePanel.readChar();
  }

  /**
   * Reads a String from the terminal
   *
   * @param s String that is shown before the input is accepted
   * @return String that was entered
   */
  public String readString(String s) {
    return thePanel.readString(s);
  }

  /**
   * Reads a string from the terminal
   *
   * @return String that was entered
   */
  public String readString() {
    return thePanel.read();
  }

  /**
   * Reads an integer value from the terminal
   *
   * @param s String that is shown before the input is accepted
   * @return integer that was entered
   */
  public synchronized int readInt(String s) {
    return thePanel.readInt(s);
  }

  /**
   * Reads a integer value from the terminal
   *
   * @return integer that was entered
   */
  public int readInt() {
    return  thePanel.readInt();
  }

  /**
   * Reads a short value from the terminal
   *
   * @param s String that is shown before the input is accepted
   * @return value that was entered
   */
  public synchronized short readShort(String s) {
    return thePanel.readShort(s);
  }

  /**
   * Reads a short value from the terminal
   *
   * @return value that was entered
   */
  public short readShort() {
    return thePanel.readShort();
  }

  /**
   * Reads a byte value from the terminal
   *
   * @param s String that is shown before the input is accepted
   * @return value that was entered
   */
  public synchronized byte readByte(String s) {
    return thePanel.readByte(s);
  }

  /**
   * Reads a byte value from the terminal
   *
   * @return value that was entered
   */
  public byte readByte() {
    return  thePanel.readByte();
  }


  /**
   * Reads a long value from the terminal
   *
   * @return value that was entered
   */
  public synchronized long readLong(String s) {
    return thePanel.readLong(s);
  }

  /**
   * Reads a long value from the terminal
   *
   * @return value that was entered
   */
  public long readLong() {
    return  thePanel.readLong();
  }


  /**
   * Reads a double value from the terminal
   *
   * @param s String that is shown before the input is accepted
   * @return value that was entered
   */
  public synchronized double readDouble(String s) {
    return thePanel.readDouble(s);
  }

  /**
   * Reads a double value from the terminal
   *
   * @return value that was entered
   */
  public double readDouble() {
    return  thePanel.readDouble();
  }


  /**
   * Reads a floating point number from the terminal
   *
   * @param s String that is shown before the input is accepted
   * @return value that was entered
   */
  public synchronized float readFloat(String s) {
    return thePanel.readFloat(s);
  }

  /**
   * Reads a floating point number from the terminal
   *
   * @return value that was entered
   */
  public float readFloat() {
    return  thePanel.readFloat();
  }


  /**
   * Reads a boolean from the terminal
   *
   * @param s String that is shown before the input is accepted
   * @return Boolean that was entered
   */
  public synchronized boolean readBoolean(String s) {
    return thePanel.readBoolean(s);
  }

  /**
   * Reads a boolean from the terminal
   *
   * @return Boolean that was entered
   */
  public boolean readBoolean() {
    return  readBoolean("");
  }


  //======================
  // print methods
  //======================

  /**
   * Prints a String to the terminal followed
   * by a new line
   *
   * @param s String to print
   */
  public void println(String s) {
    thePanel.println(s);
  }

  /**
   * Prints a new line to the terminal
   */
  public void println() {
    thePanel.println();
  }

  /**
   * Prints a String to the terminal followed
   * by a new line
   *
   * @param s String to print out
   */
  public void print(String s) {
    thePanel.print(s);
  }

  /**
   * Print an integer value to the terminal
   *
   * @param value integer to print out
   */
  public void writeInt(int value) {
    thePanel.writeInt(value);
  }

  /**
   * Print a short value to the terminal
   *
   * @param value short to print out
   */
  public void writeShort(short value) {
    thePanel.writeShort(value);
  }

  /**
   * Print a byte value to the terminal
   *
   * @param value byte to print out
   */
  public void writeByte(byte value) {
    thePanel.writeByte(value);
  }

  /**
   * Print a long value to the terminal
   *
   * @param value long to print out
   */
  public void writeLong(long value) {
    thePanel.writeLong(value);
  }

  /**
   * Print a double value to the terminal
   *
   * @param value double to print out
   */
  public void writeDouble(double value) {
    thePanel.writeDouble(value);
  }

  /**
   * Print a floating point value to the terminal
   *
   * @param value float to print out
   */
  public void writeFloat(float value) {
    thePanel.writeFloat(value);
  }

  /**
   * Print a character to the terminal
   *
   * @param value char to print out
   */
  public void writeChar(char value) {
    thePanel.writeChar(value);
  }

  /**
   * Print a boolean value to the terminal
   *
   * @param value boolean to print out
   */
  public void writeBoolean(boolean value) {
    thePanel.writeBoolean(value);
  }

} // Class Terminal



