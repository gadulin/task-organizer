package org.btb.timer.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.btb.timer.util.Configuration;
import org.btb.timer.util.Util;

/**
 * @author Stanislav Milev
 * @created on Oct 2, 2013
 */
public class TaskOrganizerV extends JFrame {

	private static final long serialVersionUID = -4124243000720837663L;

	private JButton btnNewTask;
	private JPanel pnlTasks;
	private JScrollPane spnTasks;
	
	private int maxDisplayedTasks;
	
	/**
	 * Constructor - Builds the GUI.
	 */
	public TaskOrganizerV() {
		Configuration cfg = Configuration.getInstance();
		maxDisplayedTasks = cfg.getMaxDisplayedTasks();
		this.setTitle("Task Organizer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		Container fcp = this.getContentPane();
		fcp.setLayout(new BorderLayout());

		// New Task button
		if (cfg.getButtonsWithIcons()) {
			btnNewTask = new JButton();
			Image img = Util.getImageFromFile("new icon.png");
			img = img.getScaledInstance(24, 24,  java.awt.Image.SCALE_SMOOTH); 
			ImageIcon icon = new ImageIcon(img);
			btnNewTask.setIcon(icon);
			btnNewTask.setPreferredSize(new Dimension(25, 25));
		} else {
			btnNewTask = new JButton("New");
			btnNewTask.setPreferredSize(new Dimension(70, 25));
		}
		fcp.add(btnNewTask, BorderLayout.LINE_END);
		
		pnlTasks = new JPanel();
		pnlTasks.setLayout(new GridLayout(0,1));
		spnTasks = new JScrollPane(pnlTasks);
		spnTasks.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fcp.add(spnTasks, BorderLayout.PAGE_END);
		this.pack();
		pnlTasks = (JPanel) spnTasks.getViewport().getView();
		
		this.setIconImage(Util.getImageFromFile("icon.png"));
	}

	
	
	/**
	 * Shows the UI.
	 */
	public void showTheUI() {
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth())/2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight())/2;
		this.setLocation(x, y);
		this.setVisible(true);
	}

	/**
	 * Adds a Task Panel to the task list.
	 * @param newTask a new task
	 */
	public void addTask(TaskPanelV newTask) {
		pnlTasks.add(newTask);
		if (pnlTasks.getComponentCount() <= maxDisplayedTasks) {
			spnTasks.setPreferredSize(null);
			this.pack();
			if (pnlTasks.getComponentCount() == maxDisplayedTasks) {
				spnTasks.setPreferredSize(spnTasks.getSize());
			}
		}
		this.pack();
		
		//if this is the second task, enable the remove button of the first one
		if (pnlTasks.getComponentCount() == 2) {
			TaskPanelV lastTask = (TaskPanelV) pnlTasks.getComponent(0);
			lastTask.getBtnDelete().setEnabled(true);
		}
	}

	/**
	 * Removes the given task.
	 * @param task for removal
	 */
	public void removeTask(TaskPanelV task) {
		pnlTasks.remove(task);
		if (pnlTasks.getComponentCount() < maxDisplayedTasks) {
			spnTasks.setPreferredSize(null);
			this.pack();
			spnTasks.setPreferredSize(spnTasks.getSize());
		}
		this.pack();
		
		//if one element has left, disable it's remove button
		if (pnlTasks.getComponentCount() == 1) {
			TaskPanelV lastTask = (TaskPanelV) pnlTasks.getComponent(0);
			lastTask.getBtnDelete().setEnabled(false);
		}
	}

	/**
	 * @return the btnNewTask
	 */
	public JButton getBtnNewTask() {
		return btnNewTask;
	}


	/**
	 * Moves the task to the given position.
	 * @param index new index
	 * @param task for moving
	 */
	public void moveTask(int index, TaskPanelV task) {
		pnlTasks.remove(task);
		pnlTasks.add(task, index);
		pnlTasks.revalidate();
	}

}
