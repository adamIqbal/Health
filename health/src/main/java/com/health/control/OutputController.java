package com.health.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JList;

import com.health.gui.GUImain;
import com.health.gui.output.VOutputPanel;

/**
 * Controller for the output panel.
 * @author lizzy
 *
 */
public final class OutputController {
	private VOutputPanel outputPanel;

	/**
	 * Create new instance of output controller.
	 * @param outputP	output panel
	 */
	public OutputController(final VOutputPanel outputP) {
		outputPanel = outputP;
	}

	/**
	 * Activate controller by adding all the event listeners.
	 */
	public void control() {
		ListenForPrev lprev = new ListenForPrev();
		outputPanel.getPrevButton().addActionListener(lprev);

		outputPanel.getSidebar().getList()
				.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(final MouseEvent e) {
						JList<String> list = outputPanel.getSidebar().getList();
						if (list.getModel().getSize() > 0) {
							String selected = outputPanel.getSidebar()
									.getList().getSelectedValue();
							outputPanel.getMainPanel().setData(
									outputPanel.getSidebar().getAnalysisData(
											selected));
						}
					}

					@Override
					public void mouseEntered(final MouseEvent e) {
					}

					@Override
					public void mouseExited(final MouseEvent e) {
					}

					@Override
					public void mousePressed(final MouseEvent e) {
					}

					@Override
					public void mouseReleased(final MouseEvent e) {
					}

				});

	}

	/**
	 * Action listener for previous button.
	 * @author lizzy
	 *
	 */
	private class ListenForPrev implements ActionListener {

		/**
		 * Handles the button click.
		 *
		 * @param e
		 */
		@Override
		public void actionPerformed(final ActionEvent e) {
			GUImain.selectedTab(2, 1);
			GUImain.goToTab("Step 2: Script");
		}
	}

	/**
	 * Adds an performed analysis to the output panel.
	 *
	 * @param data
	 *            the data to be displayed
	 */
	public void addAnalysis(final Map<String, Object> data) {
		outputPanel.getSidebar().add(data);
		outputPanel.getMainPanel().setData(data);
	}
}
