/*
 * tcd.io Package
 * Copyright (c) 1997 Trinity College Dublin
 * All Rights Reserved.
 */

package tcdIO;

/*
 * $Id: TerminalTextArea.java,v 1.1.1.1 1999/01/21 19:48:29 sweber Exp $
 *
 * @author Stefan Weber
 * @version 0.9 $Revision: 1.1.1.1 $
 */

import java.awt.TextArea;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;


/**
 * Wrapper class for TextArea to disable the movement of 
 * the caret through cursor keys or mouse in Terminal windows
 *
 * @see     java.awt.TextArea
 */
class TerminalTextArea 
    extends TextArea {

    int position; // caret position


    /**
     * Creates the TextArea
     * and enables MouseEvents
     *
     */
    public TerminalTextArea() {
	super();
	enableEvents();
    }


    /**
     * Creates the TextArea
     * and enables MouseEvents
     *
     * @param     rows the number of rows
     * @param     columns the number of columns
     */
    public TerminalTextArea(int rows, int columns) {
	super(rows,columns);
	enableEvents();
    }


    /**
     * Enable mouse events.
     */
    protected void enableEvents() {
	position= getCaretPosition();
	super.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }


    /**
     * Consumes certain key events to disable cursor
     * control by key events
     *
     * @param     e the key event
     */
    protected void processKeyEvent(KeyEvent e) {
		
	switch(e.getKeyCode()) {
	case KeyEvent.VK_UP:      // catch cursor movement
	case KeyEvent.VK_DOWN:    // and consume
	case KeyEvent.VK_LEFT: 
	case KeyEvent.VK_RIGHT: 
	    e.consume();      
	    break;	
	default:
	    super.processKeyEvent(e);
	    break;
	}
    }


    /**
     * Consumes certain mouse events to disable cursor
     * control by mouse events
     *
     * @param     e the mouse event
     */
    protected void processMouseEvent(MouseEvent e) {
		
	switch(e.getID()) {
	case MouseEvent.MOUSE_CLICKED:     // catch mouse events 
	case MouseEvent.MOUSE_DRAGGED:     // and consume
	case MouseEvent.MOUSE_PRESSED: 
	case MouseEvent.MOUSE_RELEASED:
	    setCaretPosition(position);
	    e.consume();      
	    break;	
	default:
	    super.processMouseEvent(e);
	    break;
	}
    }


    /**
     * Sets the caret position.
     *
     * @param position The new position of the caret.
     */
    public void setCaretPosition( int position ) {
	this.position= position;
	super.setCaretPosition(position);
    }

} // TerminalTextArea
