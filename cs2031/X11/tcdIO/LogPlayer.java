/*
 * tcd.io Package
 * Copyright (c) 1997 Trinity College Dublin
 * All Rights Reserved.
 */
package tcdIO;

/*
 * $Id: LogPlayer.java,v 1.1.1.1 1999/01/21 19:48:29 sweber Exp $
 *
 * @author Stefan Weber
 * @version 0.1 $Revision: 1.1.1.1 $
 */

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * Provides a facility to log incoming key events from
 * a key event source
 *
 */
public class LogPlayer extends Thread {
	ObjectInputStream in;
	FileInputStream fin;
	KeyListener listener;
	TerminalTextArea textArea;

	public LogPlayer(KeyListener listener,
					 TerminalTextArea textArea) throws Throwable {
		String filename;

		this.listener= listener;
		this.textArea= textArea;

		filename= System.getProperty(ConfigNames.LogFileName);
		if (filename!=null) {
			fin= new FileInputStream(filename);
			in= new ObjectInputStream(fin);
		}
	}

	protected void finalize() throws Throwable {
		fin.close();
	}

	public void run() {
		String command;
		KeyEvent event;

		try {
			suspend();
			
			while(true) {
				command= in.readUTF();
				if (command.equals("End")) {
					stop();
				}
				else {
					event= (KeyEvent) in.readObject();
					if (command.equals("keyTyped")) {
						char[] c= {event.getKeyChar()};
						String s= new String(c);
						textArea.append(s);
						listener.keyTyped(event);
					}
					if (command.equals("keyPressed")) {
						listener.keyPressed(event);
					}
					if (command.equals("keyReleased")) {
						listener.keyReleased(event);
					}
				}
			}
		}
		catch(ThreadDeath d) {
		}
		catch(Throwable t) {
			System.out.println("Exception while reading log file");
			t.printStackTrace();
			System.exit(1);
		}
	}
}



