package main.java.LinKern.projectGUI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * creates a window to set the dimension of a new PCB
 */
public class PCBDimensionFenster {
	// data
	private GridBagConstraints gridBagConstraints;

	/**
	 * constructor
	 * 
	 * @param gui
	 *            the GUI to work on
	 */
	public PCBDimensionFenster(GUI gui) {
		// disable main window
		gui.setEnabled(false);

		// set the frame
		JFrame fenster = new JFrame("Groesse des PCB festlegen");
		fenster.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fenster.setResizable(false);
		fenster.setLocation(450, 250);
		fenster.getContentPane().setLayout(new BorderLayout());

		// set panels with components
		JPanel oben = new JPanel(new GridBagLayout());
		JLabel eingabeXLabel = new JLabel("Gr��ten x-Wert eingeben: ");
		JTextField eingabeX = new JTextField(10);
		JLabel eingabeYLabel = new JLabel("Gr��ten y-Wert eingeben: ");
		JTextField eingabeY = new JTextField(10);
		oben.add(eingabeXLabel, setGridBagConstraints(0, 0));
		oben.add(eingabeX, setGridBagConstraints(1, 0));
		oben.add(eingabeYLabel, setGridBagConstraints(0, 1));
		oben.add(eingabeY, setGridBagConstraints(1, 1));

		JPanel unten = new JPanel(new GridBagLayout());
		JButton ok = new JButton("OK");
		ok.setCursor(new Cursor(JFrame.HAND_CURSOR));
		JLabel defaultLabel = new JLabel("(Defaultwerte: x: 900, y: 600)");
		unten.add(ok);
		unten.add(defaultLabel, setGridBagConstraints(0, 1));

		// add panels
		fenster.getContentPane().add(oben, BorderLayout.NORTH);
		fenster.getContentPane().add(unten, BorderLayout.SOUTH);
		fenster.pack();

		// show window
		fenster.show();

		// set action
		final JFrame FENSTER = fenster;
		final JTextField EINGABE_X = eingabeX;
		final JTextField EINGABE_Y = eingabeY;
		final GUI GUI = gui;
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// default = not set
				double x = -1;
				double y = -1;

				// look for stupid input
				try {
					x = Double.parseDouble(EINGABE_X.getText());
					y = Double.parseDouble(EINGABE_Y.getText());
				} catch (Exception exc) {
					GUI.mouseClick.convert = false;
				}

				// set maxX and maxY if input is ok
				if ((x > -1) && (y > -1)) {
					GUI.mouseClick.maxX = x;
					GUI.mouseClick.maxY = y;

					GUI.mouseClick.convert = true;
				}
				// set maxX and maxY default
				else {
					GUI.mouseClick.maxX = GUI.mouseClick.breite;
					GUI.mouseClick.maxY = GUI.mouseClick.hoehe;

					GUI.mouseClick.convert = true;
				}

				GUI.mouseClick.minX = 0;
				GUI.mouseClick.minY = 0;

				// enable main window and close dialog
				GUI.setEnabled(true);
				FENSTER.dispose();
			}
		});
	} // PCBDimensionFenster ()

	/**
	 * creates a specific GridBagLayout position to add a component
	 * 
	 * @param x
	 *            the column number
	 * @param y
	 *            the line number
	 * @return the position to add the component
	 */
	private GridBagConstraints setGridBagConstraints(int x, int y) {
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;

		return gridBagConstraints;
	} // setGridBagConstraints ()
} // class PCBDimensionFenster
