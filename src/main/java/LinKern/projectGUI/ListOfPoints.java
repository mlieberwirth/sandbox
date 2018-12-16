package main.java.LinKern.projectGUI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.LinKern.projectGraph.Vertex;

/**
 * creates a new JFrame whitch includes the list of points of the actual pbc
 */
public class ListOfPoints extends JFrame {
	// data
	private GridBagConstraints gridBagConstraints;
	private JComboBox punktListe;
	private Vertex[] punktArray;
	private GUI fenster;

	/**
	 * constructor
	 * 
	 * @param _mouseClick
	 *            the information to create the list of
	 */
	public ListOfPoints(GUI _fenster) {
		fenster = _fenster;

		// read out DoubleList of mouseClick and transform into a String array
		punktArray = fenster.mouseClick.doubleListToArray(fenster.mouseClick.punkte);

		fenster.setEnabled(false);

		createJFrame(punktArray);
	} // ListeOfPoints()

	/**
	 * initials the output window
	 * 
	 * @param punktArray
	 *            the point array of the actual pbc
	 */
	private void createJFrame(Vertex[] punktArray) {
		// data
		JPanel oben = new JPanel(), unten = new JPanel();
		JButton loeschen = new JButton("L�schen"), ok = new JButton("   OK   ");

		// set frame
		this.setTitle("Punktliste");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this.setLocation(450, 250);

		// set oben
		oben.setLayout(new GridBagLayout());
		punktListe = new JComboBox(punktArray);
		oben.add(punktListe);

		// set unten
		unten.setLayout(new GridBagLayout());
		ok.addActionListener(actionJB("ok"));
		ok.setCursor(new Cursor(JFrame.HAND_CURSOR));
		/*
		 * loeschen.addActionListener( actionJB( "loeschen" ) ); if (
		 * !fenster.aktiviert ) loeschen.setEnabled(false); unten.add(loeschen);
		 */
		unten.add(ok);

		// add panels
		this.getContentPane().add(oben, BorderLayout.NORTH);
		this.getContentPane().add(unten, BorderLayout.SOUTH);
		this.pack();
	} // createJFrame

	/**
	 * ActionListener for the JButtons
	 * 
	 * @param str
	 *            the identifyer String
	 * @return the specific ActionListener with it's action
	 */
	private ActionListener actionJB(final String str) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// choose the correct action for the given string
				if (str.equals("ok"))
					okAction();
				if (str.equals("loeschen"))
					loeschenAction();
			}
		};
	} // actionJB ()

	/**
	 * cancel the dialog
	 */
	private void okAction() {
		fenster.setEnabled(true);

		this.dispose();
	} // okAction ()

	/**
	 * removes the selected point
	 */
	private void loeschenAction() {
		// remove out of array
		for (int i = 0; i < punktArray.length; i++)
			if (punktArray[i].equals(punktListe.getSelectedItem())) {
				fenster.mouseClick.insertOrRemove(punktArray[i]);
				break;
			}

		// remove out of combo box
		punktListe.removeItem(punktListe.getSelectedItem());

		fenster.mouseClick.repaint();
	} // loeschenAction ()
} // class ListeOfPoints