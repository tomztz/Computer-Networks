/*
 * tcd.io Package
 * Copyright (c) 1997 Trinity College Dublin
 * All Rights Reserved.
 */

package tcdIO;

/*
 * $Id: TerminalCore.java,v 1.1.1.1 1999/01/21 19:48:29 sweber Exp $
 *
 * @author Stefan Weber
 * @version 0.9 $Revision: 1.1.1.1 $
 */

import java.awt.Panel;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Provides core function of a panel behaving like a Terminal.
 * Based on a <code>Panel</code> the user is provided with a 
 * TextArea from which input can be accepted and output can 
 * be written to. To prevent certain key actions the class
 * subscribes as a key listener and filters certain key events.
 *
 * @see     java.awt.Panel
 */
class TerminalCore extends Panel implements KeyListener {

    protected TextArea textArea;  // textarea where in-/output is happening
    protected boolean caps_lock;  // flag to signal the capslock status
    protected boolean reading;    // flag to signal that input is accepted
    protected boolean readingChar;// flag for character input
    protected String input;       // buffer for input while waiting for <enter>
    protected char charInput;	  // buffer for character input
    protected int position;       // caret position


    /**
     * Creates a terminal panel
     */
    public TerminalCore() {
	this(40, 20);
    }


    /**
     * Creates a terminal panel
     *
     * @param     width  the width of the panel
     * @param     height the height of the panel
     */
    public TerminalCore(int width, int height) {
	this(new BorderLayout(), width, height);
    }


    /**
     * Creates a terminal panel with a given layout manager
     *
     * @param lm layout manager to use with this panel
     */
    public TerminalCore(LayoutManager lm) {
	this(lm, 40, 20);
    }


    /**
     * Creates a terminal panel with a given layout manager 
     * and given size
     *
     * @param     width  the width of the panel
     * @param     height the height of the panel
     * @param     lm     layout manager to use with this panel
     */
    public TerminalCore(LayoutManager lm, int width, int height) {
	super(lm);
	textArea = new TerminalTextArea(height, width);
	textArea.addKeyListener(this);
	add(textArea);		
	disableEvents(java.awt.AWTEvent.MOUSE_EVENT_MASK);

	caps_lock= false;
	reading= false;
	readingChar= false;
	input= new String();
	position= textArea.getCaretPosition();		
    }



    //======================
    // non-functional stuff
    //======================
    /**
     * Returns the textarea used by this terminal core.
     *
     * @return The textarea used by this terminal core.
     */
    public TextArea getTextArea() {
	return textArea;
    }


    /**
     * Sets the font used by the textarea of the core.
     * 
     * @param f The font used by the textarea of the core.
     */
    public void setFont(Font f) {
	textArea.setFont(f);
    }



    //=====================
    // key-handling methods
    //=====================

    /**
     * Handles key events
     * The method filters certain key events and adds other
     * events to a buffer which is kept to be read out by
     * the user
     *
     * @param e Event that caused this method to be invoked
     */
    public synchronized void keyTyped(KeyEvent e) {


	if (reading) { // only handle event if 'reading' is activated
	    
	    /* ignore control and meta characters */
	    if (e.isControlDown() || e.isMetaDown() || e.isAltDown()) {
		e.consume();
	    }


	    switch(e.getKeyCode()) { // handle individual key events
	    case KeyEvent.VK_BACK_SPACE:
		e.consume();
		break;
	    }
	}
	else {
	    if (readingChar) { // event is handled 
		               // if reading character is activated
		charInput= e.getKeyChar();
		notify();
		e.consume();
	    }
	}
    }


    /**
     * Handles key events
     * The method filters certain key events and adds other
     * events to a buffer which is kept to be read out by
     * the user
     *
     * @param e Event that caused this method to be invoked
     */
    public synchronized void keyPressed(KeyEvent e) {

	if (reading) { // only handle event if 'reading' is activated

	    /* ignore control and meta characters */
	    if (e.isControlDown() || e.isMetaDown() || e.isAltDown()) {
		e.consume();
	    }

	    switch(e.getKeyCode()) { // handle individual key events
	    case KeyEvent.VK_ENTER:
		e.consume();
		break;
	    case KeyEvent.VK_BACK_SPACE:
		if (input.length()>=1) {
		    input= input.substring(0, input.length()-1);
		}
		else {
		    e.consume();
		}
		break;
	    case KeyEvent.VK_CAPS_LOCK:
		caps_lock= !caps_lock;
		break;
	    default:
		char c;
		c= e.getKeyChar();
		if (c!=KeyEvent.CHAR_UNDEFINED) {
		    if (caps_lock) {
			c= Character.toUpperCase(c);
		    }		
		    position++;	
		    textArea.setCaretPosition(position);
		    input= input.concat((new Character(c)).toString());
		}
	    }
	}
    }


    /**
     * Handles key events.
     * The method filters certain key events and adds other
     * events to a buffer which is kept to be read out by
     * the user.
     *
     * @param e Event that caused this method to be invoked
     */
    public synchronized void keyReleased(KeyEvent e) {

	if (reading) {
	    if (e.isControlDown() || e.isMetaDown() || e.isAltDown()) {
		e.consume();
	    }

	    switch(e.getKeyCode()) {
	    case KeyEvent.VK_ENTER:    // wakeup read if <ENTER> is pressed
		textArea.append("\n");
		e.consume();
		if (reading) {
		    notify();
		}
		break;
	    case KeyEvent.VK_BACK_SPACE:
		e.consume();
		break;
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

	/* A flag (reading) is set and this method is stopped. 
	 * The flag is tested by event handling methods. If 
	 * the flag is set the incoming key events are added
	 * to a buffer. If <enter> is pressed the flag is reset
	 * and this method is continued. The method returns the
	 * contents of the buffer that was build while the 
	 * method waited for <enter>
	 */
	textArea.setCaretPosition(position);
	input= new String();
	reading= true;
	textArea.setEditable(true);

	try {wait();} catch(Exception e){}  // wait until <ENTER> was pressed
		
	textArea.setEditable(false);
	reading= false;
	position++;
	textArea.setCaretPosition(position);		
	return input;
    }

    /**
     * Reads a single character from the panel
     *
     * @return character entered at the panel
     */
    public synchronized char readKey() {
	/* Reads one character*/
	charInput= (char) 0;
	readingChar= true;
		
	try {wait();} catch(Exception e){}  // wait until a key was pressed
	                                     
	readingChar= false;
	return charInput;
    }


    //======================
    // print methods
    //======================

    /**
     * Prints a string at the panel
     *
     * @param text Text to be printed out
     */
    public synchronized void print(String text) {
	/* print text to TextArea  and increases 
	 *  the position for the caret 
	 */
	position= textArea.getCaretPosition();
	textArea.append(text);
	position+= text.length();		
	textArea.setCaretPosition(position);
    }

    /**
     * Prints a string at the panel and adds a new line
     *
     * @param text Text to be printed out
     */
    public synchronized void println(String text) {
	/* print text and a 'newline' to TextArea and increases 
	 * the position for the caret 
	 */
	position= textArea.getCaretPosition();
	textArea.append(text + "\n");		
	position+= text.length()+1;
	textArea.setCaretPosition(position);
    }

} // class TerminalCore
