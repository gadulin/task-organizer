package org.btb.timer.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.btb.timer.data.TaskO;
import org.btb.timer.gui.TaskPanelV;
import org.btb.timer.util.DataStore;
import org.btb.timer.util.GUIThread;
import org.btb.timer.util.IConstants;

/**
 * Main class for the "Timer D" application. Controller.
 * @author Stanislav Milev
 * @date 13.09.2008
 */
public class TimerC implements ActionListener, WindowListener, DocumentListener {

	//TODO delete if not used
	
	private TaskPanelV view;
	private Timer timer;
	
	/**
	 * Constructor for first time start.
	 */
	public TimerC() {
		view = new TaskPanelV();
//		GUIThread gtView = new GUIThread(view);
		timer = new Timer(IConstants.SECONDS, this);
//		gtView.start();
		
//		setListeners(gtView);
		view.setComponentsState(true);
	}

	/**
	 * Constructor.
	 * @param timerO
	 */
	public TimerC(TaskO timerO) {
		this();
		view.initFields(timerO);
	}

	/**
	 * Initializes listeners.
	 * @param gtView
	 */
	private void setListeners(GUIThread gtView) {
		while(gtView.isNotReady()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		view.getBtnReset().addActionListener(this);
		view.getBtnStart().addActionListener(this);
		view.getBtnStop().addActionListener(this);
		view.getTxfTaskName().getDocument().addDocumentListener(this);
//		view.addWindowListener(this);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object actionEventSource = e.getSource();
		if (actionEventSource.equals(view.getBtnReset())) {
			view.setTime(0, 0, 0, 0);
		} else
			
		if (actionEventSource.equals(view.getBtnStart())) {
			view.setComponentsState(false);
			DataStore.saveObject(IConstants.DEFAULT_FILE_PATH, view.getTimerO());
			timer.start();
		} else
			
		if (actionEventSource.equals(view.getBtnStop())) {
			timer.stop();
			view.setComponentsState(true);
			DataStore.saveObject(IConstants.DEFAULT_FILE_PATH, view.getTimerO());
		} else
			
		if (actionEventSource.equals(timer)) {
			adjustTime();
		}
	}

	/**
	 * Calculates the current state of timer.
	 */
	private void adjustTime() {
		int minutes = view.getMinutes();
		int hours = view.getHours();
		int days = view.getDays();
		int seconds = view.getSeconds();

		seconds++;
		if (seconds == IConstants.MAX_SECONDS) {
			seconds = 0;
			minutes++;
			if ((minutes % IConstants.DEFAULT_AUTO_SAVE_INTERVAL) == 0 || minutes == 0) {
				DataStore.saveObject(IConstants.DEFAULT_FILE_PATH, view.getTimerO());
			}
			if (minutes == IConstants.MAX_MINUTES) {
				minutes = 0;
				hours++;
				if (hours == IConstants.MAX_HOURS) {
					hours = 0;
					days++;
				}
			}
		}
			
		view.setTime(minutes, hours, days, seconds);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		timer.stop();
		view.setComponentsState(true);
		DataStore.saveObject(IConstants.DEFAULT_FILE_PATH, view.getTimerO());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.DocumentListener#insertUpdate(java.awt.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
//		view.setTitle(view.getTaskName() + " - Task Organizer");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.DocumentListener#removeUpdate(java.awt.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
//		view.setTitle(view.getTaskName() + " - Task Organizer");
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void changedUpdate(DocumentEvent arg0) {}

}
