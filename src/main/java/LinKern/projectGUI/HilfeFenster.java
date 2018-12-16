package main.java.LinKern.projectGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HilfeFenster 
{
	// data
	private GUI gui;
	private static JFrame fenster;
	private JPanel unten;
	private JScrollPane mitte;
	private JLabel einleitung;
	private JTextArea ausgabe;
	private JButton ok;
	private GridBagConstraints gridBagConstraints;
	
	
	public HilfeFenster (GUI g, String datei)
	{
		// disable GUI
		gui = g;
		gui.setEnabled(false);
		
		// make window
		fenster = new JFrame("Hilfe");
		fenster.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fenster.setResizable(false);
		fenster.setLocation(450, 250);
		fenster.getContentPane().setLayout( new BorderLayout() );
		
		// set components
		unten = new JPanel( new GridBagLayout() );
		
		if ( datei.equals("hilfe.txt") )
			einleitung = new JLabel ("Kurzanleitung Bohr-Profi 1.0");
		else
		{
			fenster.setTitle("About Bohr-Profi 1.0");
			einleitung = new JLabel ("Hier nun einiges Wissenswertes");
		}
		
		ausgabe = new JTextArea();
		ausgabe.setBackground(Color.WHITE);
		ausgabe.setEditable(false);
		
		mitte = new JScrollPane(ausgabe,
		                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mitte.setPreferredSize(new Dimension(405,400));
		
		ok = new JButton( "  OK  " );
		ok.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		// add components
		fenster.getContentPane().add(einleitung, BorderLayout.NORTH);
		fenster.getContentPane().add(mitte, BorderLayout.CENTER);
		fenster.getContentPane().add(unten, BorderLayout.SOUTH);
		unten.add(ok);
		fenster.pack();
		
		// show window
		fenster.show();
		
		// write text
		schreibeTextDatei(datei);
		
		// set scroll bar to head
		ausgabe.setCaretPosition(0);
		
		// action on ok
		ok.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// enable GUI
				gui.setEnabled(true);
				
				// close window
				fenster.dispose();
			}
		});
	} // HilfeFenster ()
	
	
	private void schreibeTextDatei ( String datei )
	{
		try
		{
			// read out datei
			FileInputStream fs = new FileInputStream(datei);
			BufferedReader  bs = new BufferedReader(new InputStreamReader(fs));
			
			// read the line and write in ausgabe
			String zeile;
			while( (zeile = bs.readLine())!= null ) 
			{
				ausgabe.append( zeile + '\n' );
			}
		}
		catch ( Exception exc )
		{
		}
	} // schreibeTextDatei()
} // class HilfeFenster
