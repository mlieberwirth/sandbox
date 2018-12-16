package main.java.LinKern.projectGUI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.LinKern.projectLinKernighan.LinKernighan;


/**
 * creates a window to set the break time for the algorithm
 */
public class AbbruchzeitFenster 
{
	// data
	private GridBagConstraints gridBagConstraints;
	
	
	/**
	 * constructor
	 * 
	 * @param gui the GUI to work on
	 */
	public AbbruchzeitFenster( GUI gui )
	{
		// disable main window
		gui.setEnabled(false);
		
		// set the frame
		JFrame fenster = new JFrame( "Fein-Einstellungen" );
		fenster.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fenster.setResizable(false);
		fenster.setLocation(450, 150);
		fenster.getContentPane().setLayout( new BorderLayout() );
		
		// set panels with components
		//JLabel leer3 = new JLabel("-----------------------------------------------------------------------------------");
		JPanel ueber = new JPanel(new GridBagLayout());
		//ueber.add(leer3,setGridBagConstraints(0,0) );
		
		JPanel oben = new JPanel( new GridBagLayout() );
		
		oben.setBorder(BorderFactory.createTitledBorder("Bitte Abbruchzeit eingeben - falls gew�nscht "));
		JLabel eingabeLabel = new JLabel( "Gew�nschte Zeit eingeben ( in Sekunden ) " );
		JTextField eingabe = new JTextField(10);
		
		//oben.add(ueber, setGridBagConstraints(0,0));
		oben.add(eingabeLabel, setGridBagConstraints(0,1));
		oben.add(eingabe, setGridBagConstraints(1,1));
		
		JPanel unten = new JPanel( new GridBagLayout() );
		JButton ok = new JButton( "             OK              " );
		ok.setCursor(new Cursor(JFrame.HAND_CURSOR));
		
		//neues Teil sieht aber scheisse aus
		// create a slider for the amount of starting tours
		JPanel mitte = new JPanel( new GridBagLayout());
		//JLabel leer = new JLabel("-----------------------------------------------------------------------------------");
		//JLabel leer1 = new JLabel("-----------------------------------------------------------------------------------");
		//JLabel leer2 = new JLabel("-----------------------------------------------------------------------------------");
	
		JPanel nachbar = new JPanel(new GridBagLayout());
		
		Slider slider = new Slider(this);
		slider.setBorder(BorderFactory.createTitledBorder("Gr��erer Wert --> bessere G�te m�glich --> l�ngere Laufzeit"));
		
		Farbwahl wahl = new Farbwahl(gui);
		
		//unten.add(leer, setGridBagConstraints(0,0));
		unten.add(slider, setGridBagConstraints(0,1));
		//unten.add(leer1, setGridBagConstraints(0,3));
		unten.add(wahl,setGridBagConstraints(0,7));
		unten.add(ok ,setGridBagConstraints(0,8));
		
		// add panels
		fenster.getContentPane().add(oben, BorderLayout.NORTH);
		fenster.getContentPane().add(unten, BorderLayout.SOUTH);
		fenster.pack();
		
		// show window
		fenster.show();
		
		// set action
		final JFrame FENSTER = fenster;
		final JTextField EINGABE = eingabe;
		final GUI GUI = gui;
		final JTextField SLIDER = slider.picture;
		final Farbwahl WAHL = wahl;
		ok.addActionListener(  new ActionListener()
		{ 
			public void actionPerformed(ActionEvent e)
			{
				// default = not set
				int zeit = -1;
				
				// look for stupid input
				try
				{
					LinKernighan.startTouren = Integer.parseInt(SLIDER.getText());
					
					// set the choosen colour
					GUI.mouseClick.linien = WAHL.actual;
					GUI.mouseClick.point = WAHL.current;
					
					// repaint with new color
					if ( GUI.mouseClick.alsArray )
						GUI.mouseClick.repaint();
					else
					{
						GUI.mouseClick.alsArray = false;
						GUI.mouseClick.repaint();
						GUI.mouseClick.alsArray = true;
					}
					
					zeit = Integer.parseInt( EINGABE.getText() );
				}
				catch ( Exception exc )
				{
				}
				
				// set break time if input is ok
				if ( zeit > -1 )
				{
					GUI.stopNachZeit = true;
					GUI.abbruchzeit.setText( "" + zeit );
					GUI.abbruch = zeit;
				}
				else
				{
					GUI.stopNachZeit = false;		
					GUI.abbruch = 0;	
					GUI.abbruchzeit.setText("   - - - -");
				}
				
				// enable main window and close dialog
				GUI.setEnabled(true);
				FENSTER.dispose();
			}
		});
	} // AbbruchzeitFenster()
	
	
	/**
	 * creates a specific GridBagLayout position to add a component
	 * 
	 * @param x the column number
	 * @param y the line number
	 * @return the position to add the component
	 */
	private GridBagConstraints setGridBagConstraints ( int x, int y )
	{
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		
		return gridBagConstraints;
	} // setGridBagConstraints ()
} // class AbbruchzeitFenster
