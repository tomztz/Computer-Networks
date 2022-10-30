/*
 * tcd.io Package
 * Copyright (c) 1997 Trinity College Dublin
 * All Rights Reserved.
 */
package tcdIO;

/*
 * $Id: LogRecorder.java,v 1.1.1.1 1999/01/21 19:48:29 sweber Exp $
 *
 * @author Stefan Weber
 * @version 0.1 $Revision: 1.1.1.1 $
 */
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
/**
 * Provides a facility to log incoming key events from
 * a key event source
 *
 */
public class LogRecorder
  implements KeyListener {

  ObjectOutputStream out;
  FileOutputStream fout;

  public LogRecorder() throws Throwable {
    String filename;

    filename= System.getProperty(ConfigNames.LogFileName);
    if (filename!=null) {
      fout= new FileOutputStream(filename);
      out= new ObjectOutputStream(fout);
    }
  }

  protected void finalize() throws Throwable {
    out.writeUTF("End");
    out.flush();
    fout.close();
  }

  public void keyTyped(KeyEvent e) {
    try {
      out.writeUTF("keyTyped");
      out.writeObject(e);
    }
    catch(IOException io) {
      System.out.println("Exception while writing log" );
      io.printStackTrace();
      System.exit(1);
    }
  }

  public void keyPressed(KeyEvent e) {
    try {
      out.writeUTF("keyPressed");
      out.writeObject(e);
    }
    catch(IOException io) {
      System.out.println("Exception while writing log" );
      io.printStackTrace();
      System.exit(1);
    }
  }

  public void keyReleased(KeyEvent e) {
    try {
      out.writeUTF("keyReleased");
      out.writeObject(e);
    }
    catch(IOException io) {
      System.out.println("Exception while writing log" );
      io.printStackTrace();
      System.exit(1);
    }
  }
}



