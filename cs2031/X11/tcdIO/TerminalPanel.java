/*
 * tcd.io Package
 * Copyright (c) 1997 Trinity College Dublin
 * All Rights Reserved.
 */
package tcdIO;

/*
 * $Id: TerminalPanel.java,v 1.1.1.1 1999/01/21 19:48:29 sweber Exp $
 *
 * @author Stefan Weber
 * @version 0.1 $Revision: 1.1.1.1 $
 */
import java.awt.LayoutManager;

/**
 * 
 * Class provides a panel representing a simple terminal.
 * The in-/output methods are based on in/output method
 * provided by the superclass TerminalCore (i.e. this
 * class is a mere wrapper around the core for convinience)
 *
 * @see     java.awt.Panel
 * @see     tcd.io.TerminalCore
 */
class TerminalPanel extends TerminalCore {

	LogPlayer logPlayer;

	/**
	 * Creates a terminal panel
	 */
	public TerminalPanel() {
		super();
		init();
	}

	/**
	 * Creates a terminal panel
	 *
	 * @param     width  the width of the panel
	 * @param     height the height of the panel
	 */
	public TerminalPanel(int width, int height) {
		super(width, height);
		init();
	}

	/**
	 * Creates a terminal panel with a given layout manager
	 *
	 * @param lm layout manager to use with this panel
	 */
	public TerminalPanel(LayoutManager lm) {
		super(lm);
		init();
	}

	/**
	 * Creates a terminal panel with a given layout manager 
	 * and given size
	 *
	 * @param     width  the width of the panel
	 * @param     height the height of the panel
	 * @param     lm     layout manager to use with this panel
	 */
	public TerminalPanel(LayoutManager lm, int width, int height){
		super(lm, width, height);
		init();
	}

	public void init() {
		String type;
		
		disableEvents(java.awt.AWTEvent.MOUSE_EVENT_MASK);				logPlayer= null;

		type= System.getProperty(ConfigNames.TerminalType);
		if (type!=null) {
			if (type.equalsIgnoreCase("record")) {
				try {
					LogRecorder logRecorder;

					logRecorder= new LogRecorder();
					textArea.addKeyListener(logRecorder);
				}
				catch(Throwable t) {
					System.out.println("Exception while creating log recorder");
					t.printStackTrace();
					System.exit(1);
				}
			}
			else
				if (type.equalsIgnoreCase("replay")) {
					try {

						textArea.removeKeyListener(this);
						
						logPlayer= new LogPlayer(this, (TerminalTextArea) textArea);
						logPlayer.start();
					}
					catch(Throwable t) {
						System.out.println("Exception while creating log recorder");
						t.printStackTrace();
						System.exit(1);
					}
				}
		}
	}

	//======================
	// read methods
	//======================


	/**
	 * Reads a string from the panel until <enter> is pressed
	 *
	 * @return String read from the panel
	 */
	public synchronized String read() {
		String s;
		
		if (logPlayer!=null) 
			logPlayer.resume();
		s= super.read();
		if (logPlayer!=null) 
			logPlayer.suspend();    

		return s;
	}

	/**
	 * Read a single character from the panel 
	 * 
	 * @param s String to print out
	 * @return character value read from panel
	 */
	public synchronized char readChar(String s) {
		/* Read Character from TextArea */
		char value;
		boolean getit;
		
		value= 0;
		getit= true;
		do { 
			try {
				value= readKey();
				getit= false;
			}
			catch(Exception e) {
				getit= true;
				value= 0;
			}
		} while(getit); 
		
		return value;
	}

	/**
	 * Read a single character from the panel 
	 * 
	 * @return character value read from panel
	 */
	public char readChar() {
		return readChar("");
	}

	/**
	 * Read a string from the panel 
	 * 
	 * @param s String to print out
	 * @return string read from panel
	 */
	public String readString(String s) {
		print(s);
		return read();
	}

	/**
	 * Read an integer value from the panel 
	 * 
	 * @param s String to print out
	 * @return value read from panel
	 */
	public synchronized int readInt(String s) {
		/* Read String from TextArea and transform into int */
		
		int value;
		boolean getit;
		
		value= 0;
		getit= true;
		do { 
			try {
				if (s.length()>0) print(s);
				value= (new Integer(read())).intValue();
				getit= false;
			}
			catch(Exception e) {
				getit= true;
			}
		} while(getit);
		
		
		return value;
	}
	
	/**
	 * Read an integer value from the panel 
	 * 
	 * @return value read from panel
	 */
	public int readInt() {
		return  readInt("");
	}
	
	/**
	 * Read an integer value from the panel 
	 * 
	 * @param s String to print out
	 * @return value read from panel
	 */
	public synchronized short readShort(String s) {
		/* Read String from TextArea and transform into short */
		
		short value;
		boolean getit;
		
		value= 0;
		getit= true;
		do { 
			try {
				if (s.length()>0) print(s);
				value= (new Short(read())).shortValue();
				getit= false;
			}
			catch(Exception e) {
				getit= true;
			}
		} while(getit);
		
		return value;
	}
	
	/**
	 * Read an integer value from the panel 
	 * 
	 * @return value read from panel
	 */
	public short readShort() {
		return readShort("");
	}
	
	/**
	 * Read an integer value from the panel 
	 * 
	 * @param s String to print out
	 * @return value read from panel
	 */
	public synchronized byte readByte(String s) {
		/* Read String from TextArea and transform into byte */
		
		byte value;
		boolean getit;
		
		value= 0;
		getit= true;
		do { 
			try {
				if (s.length()>0) print(s);
				value= (new Byte(read())).byteValue();
				getit= false;
			}
			catch(Exception e) {
				getit= true;
			}
		} while(getit);
		
		return value;
	}

	/**
	 * Read an integer value from the panel 
	 * 
	 * @return value read from panel
	 */
	public byte readByte() {
		return  readByte("");
	}


	/**
	 * Read an integer value from the panel 
	 * 
	 * @param s String to print out
	 * @return value read from panel
	 */
	public synchronized long readLong(String s) {
		/* Read String from TextArea and transform into long */
		
		long value;
		boolean getit;
		
		value= 0;
		getit= true;
		do { 
			try {
				if (s.length()>0) print(s);
				value= (new Long(read())).longValue();
				getit= false;
			}
			catch(Exception e) {
				getit= true;
			}
		} while(getit);
		
		return value;
	}

	/**
	 * Read an integer value from the panel 
	 * 
	 * @return value read from panel
	 */
	public long readLong() {
		return  readLong("");
	}


	/**
	 * Read an integer value from the panel 
	 * 
	 * @param s String to print out
	 * @return value read from panel
	 */
	public synchronized double readDouble(String s) {
		/* Read String from TextArea and transform into double */

		double value;
		boolean getit;
		
		value= 0;
		getit= true;
		do { 
			try {
				if (s.length()>0) print(s);
				value= (new Double(read())).doubleValue();
				getit= false;
			}
			catch(Exception e) {
				getit= true;
			}
		} while(getit);
		
		return value;
	}

	/**
	 * Read an integer value from the panel 
	 * 
	 * @return value read from panel
	 */
	public double readDouble() {
		return  readDouble("");
	}


	/**
	 * Read an integer value from the panel 
	 * 
	 * @param s String to print out
	 * @return value read from panel
	 */
	public synchronized float readFloat(String s) {
		/* Read String from TextArea and transform into float */

		float value;
		boolean getit;
		
		value= 0;
		getit= true;
		do { 
			try {
				if (s.length()>0) print(s);
				value= (new Float(read())).floatValue();
				getit= false;
			}
			catch(Exception e) {
				getit= true;
			}
		} while(getit);
		
		return value;
	}

	/**
	 * Read an integer value from the panel 
	 * 
	 * @return value read from panel
	 */
	public float readFloat() {
		return  readFloat("");
	}


	/**
	 * Read an integer value from the panel 
	 * 
	 * @param s String to print out
	 * @return value read from panel
	 */
	public synchronized boolean readBoolean(String s) {
		/* Read String from TextArea and transform into boolean */

		boolean value;
		boolean getit;
		
		value= false;
		getit= true;
		do { 
			try {
				if (s.length()>0) print(s);
				value= (new Boolean(read())).booleanValue();
				getit= false;
			}
			catch(Exception e) {
				getit= true;
			}
		} while(getit);
		
		return value;
	}

	/**
	 * Read an integer value from the panel 
	 * 
	 * @return value read from panel
	 */
	public boolean readBoolean() {
		return  readBoolean("");
	}


	//======================
	// print methods
	//======================


	/**
	 * Prints a new line to the terminal
	 */
	public void println() {
		/* print a 'newline' */
		println("");
	}

	/**
	 * Print an integer value to the panel
	 *
	 * @param value integer to print out
	 */
	public void writeInt(int value) {
		println(Integer.toString(value));
	}

	/**
	 * Print a short value to the panel
	 *
	 * @param value short to print out
	 */
	public void writeShort(short value) {
		println(Short.toString(value));
	}

	/**
	 * Print a byte value to the panel
	 *
	 * @param value byte to print out
	 */
	public void writeByte(byte value) {
		println(Byte.toString(value));
	}

	/**
	 * Print a long value to the panel
	 *
	 * @param value long to print out
	 */
	public void writeLong(long value) {
		println(Long.toString(value));
	}

	/**
	 * Print a double value to the panel
	 *
	 * @param value double to print out
	 */
	public void writeDouble(double value) {
		println(Double.toString(value));
	}

	/**
	 * Print a floating point value to the panel
	 *
	 * @param value float to print out
	 */
	public void writeFloat(float value) {
		println(Float.toString(value));
	}

	/**
	 * Print a character to the panel
	 *
	 * @param value char to print out
	 */
	public void writeChar(char value) {
		print((new Character(value)).toString());
	}

	/**
	 * Print a boolean value to the panel
	 *
	 * @param value boolean to print out
	 */
	public void writeBoolean(boolean value) {
		print((new Boolean(value)).toString());
	}

} // Class TerminalPanel