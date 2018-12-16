package main.java.LinKern.projectGUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import main.java.LinKern.comaList.DoubleList;
import main.java.LinKern.projectGraph.Vertex;
import main.java.LinKern.projectIO.reinraus;
import main.java.LinKern.projectLinKernighan.LinKernighan;


public class GUI extends JFrame
{
/*** data for the application **********************************************************/
	
	
	// panels
	private JPanel panelGesamt,
				   panelNord,
				   panelNordLinks,
				   panelNordRechts,
				   panelSued,
				   panelSuedLinks,
				   panelSuedRechts;
	
	// the menu
	private JMenuBar menueLeiste;
	private JMenu datei,
				  bearbeiten,
				  optionen,
				  hilfe;
	private JMenuItem neu,
					  oeffnen,
					  speichern,
					  speichernUnter,
	                  beenden,
	                  einstellungen,
	                  liste,
	                  kurzanleitung,
	                  about,
					  wegBerechnen,
					  editieren;
	private JSeparator trennungDatei,
					   trennungBearbeiten,
					   trennungOptionen;
	
	// labels
	private JLabel fortschrittLabel, 
				   gueteLabel,
				   rechenzeitLabel,
				   abbruchzeitLabel,
				   wegLabel,
				   untereSchrankeLabel,
				   mouseCoordLabel,
				   punktLabel;
				   
	// text fields
	private JTextField guete,
					   weg,
					   untereSchranke,
					   number,
					   mouseCoordX,
					   mouseCoordY,
					   coordX,
					   coordY;
	protected JTextField abbruchzeit,
	 					 rechenzeit;
	private String dateiname;
	
	// buttons
	private JButton stop,
					start,
					reset;
	
	// scroll pane for painting 
	private JScrollPane platine;
	
	// the run time
	protected int abbruch;
	protected boolean stopNachZeit = false;
	
	// other functions
	private GridBagConstraints gridBagConstraints;
	private JProgressBar fortschritt;
	private JCheckBox schreibschutz;
	protected boolean aktiviert = false;
	private String dateipfad = "",
		    dateiformat = "pnt";
	
    protected MouseClick mouseClick = new MouseClick();
    private boolean gespeichert = false;
    
/*** constructor ***********************************************************************/
    
    /**
     * constructor to create new frame
     */
    public GUI() 
    {					
    	// make the frame
    	initComponents();
    	
    	// activate platine for mouse
        platine.setViewportView(mouseClick);
        
        // declare mouse border for the points to set
		mouseClick.breite = (int)platine.getWidth();   
		mouseClick.hoehe = (int)platine.getHeight();  
    } // GUI ()
    
    
/*** build the window ******************************************************************/
    
    
    private void initComponents() 
    {
      // frame outfit and settings
	  // --------------------------------------------------------------------------------
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setTitle( "Bohr-Profi 1.0" );
    	this.setLocation(170, 140);
		this.setResizable(false);
    	
	  // ================================================================================
    	
    	
      // create menu
      // --------------------------------------------------------------------------------
    	
    	// set JMenuBar
		menueLeiste = new JMenuBar();
		
		// set JMenu
		datei = new JMenu( "Datei" );
		datei.setMnemonic(KeyEvent.VK_D);
		datei.setToolTipText( "Dateien neu erstellen, �ffnen, speichern oder Programm" +
							  " beenden." );
		datei.setAutoscrolls(true);
		datei.setCursor( new Cursor(JFrame.HAND_CURSOR) );
    	
		bearbeiten = new JMenu( "Bearbeiten" );
		bearbeiten.setMnemonic(KeyEvent.VK_E);
		bearbeiten.setToolTipText( "Weg berechnen oder PCB editieren." );
	    bearbeiten.setAutoscrolls(true);
		bearbeiten.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		optionen = new JMenu("Optionen");
		optionen.setMnemonic(KeyEvent.VK_I);
		optionen.setToolTipText( "Zus�tzliche Funktionen" );
		optionen.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		hilfe = new JMenu("Hilfe");
		hilfe.setMnemonic(KeyEvent.VK_H);
		hilfe.setToolTipText( "Wenn man mal nicht weiter wei�." );
		hilfe.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		// add JMenu to JMenuBar
	    menueLeiste.add(datei);
		menueLeiste.add(bearbeiten);
		menueLeiste.add(optionen);
		menueLeiste.add(hilfe);
		
		// set JSeparators
	    trennungDatei = new JSeparator();
		trennungBearbeiten = new JSeparator();
		trennungOptionen = new JSeparator();
		
		// set JMenuItem for 'datei'
	    neu = new JMenuItem( "Neues PCB", KeyEvent.VK_N );
		neu.setToolTipText( "Ein neues PCB erstellen." );
		neu.addActionListener( actionJMI("neu") );
		neu.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		oeffnen = new JMenuItem( "�ffnen", KeyEvent.VK_F );
		oeffnen.setToolTipText( "�ffnet eine bestehende Datei." );
		oeffnen.addActionListener( actionJMI("oeffnen") );
		oeffnen.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		speichern = new JMenuItem( "Speichern", KeyEvent.VK_S );
		speichern.setToolTipText( "Speichert die aktuelle Datei." );
		speichern.addActionListener( actionJMI( "speichern" ) );
		speichern.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		speichernUnter = new JMenuItem( "Speichern unter...", KeyEvent.VK_P );
		speichernUnter.setToolTipText( "Die aktuelle Datei unter neuem Namen" +
									   " speichern." );
		speichernUnter.addActionListener( actionJMI( "speichernUnter" ) );
		speichernUnter.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		beenden = new JMenuItem( "Beenden", KeyEvent.VK_B );
		beenden.setToolTipText( "Beendet das Programm." );
		beenden.addActionListener( actionJMI("beenden") );
		beenden.setCursor( new Cursor(JFrame.HAND_CURSOR) );
    	
		// add JMenuItem for 'datei'
	    datei.add(neu);
		datei.add(oeffnen);
		datei.add(speichern);
		datei.add(speichernUnter);
		datei.add(trennungDatei);
		datei.add(beenden);
        
		// set JMenuItem for 'bearbeiten'
		wegBerechnen = new JMenuItem( "Weg berechnen", KeyEvent.VK_W );
		wegBerechnen.setToolTipText( "Berechnet einen optimalen Bohrweg." );
		wegBerechnen.addActionListener( actionJMI("wegBerechnen") );
		wegBerechnen.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		editieren = new JMenuItem( "PCB editieren", KeyEvent.VK_T );
		editieren.setToolTipText( "Erm�glicht Bearbeitung des PCB." );
		editieren.addActionListener( actionJMI("editieren") );
		editieren.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		// add JMenuItem for 'bearbeiten'
		bearbeiten.add(wegBerechnen);
		bearbeiten.add(trennungBearbeiten);
		bearbeiten.add(editieren);
		
		// set JMenuItem for 'optionen'
		liste = new JMenuItem("Punkteliste", KeyEvent.VK_T);
		liste.setToolTipText( "Zeigt eine Liste der vorhandenen Punkte." );
		liste.addActionListener( actionJMI( "liste" ) );
		liste.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		einstellungen = new JMenuItem("Feineinstellungen", KeyEvent.VK_G);
		einstellungen.setToolTipText( "Abbruchzeit setzen, Laufzeitkonfigurationen und Farben w�hlen" );
		einstellungen.addActionListener( actionJMI( "einstellungen" ) );
		einstellungen.setCursor( new Cursor(JFrame.HAND_CURSOR) );
				
		// add JMenuItem for 'optionen'
		optionen.add(liste);
		optionen.add(trennungOptionen);
		optionen.add(einstellungen);
		
		// set JMenuItem for 'hilfe'
		kurzanleitung = new JMenuItem("Kurzanleitung", KeyEvent.VK_K);
		kurzanleitung.setToolTipText( "�bersicht der wichtigsten Funktionen." );
		kurzanleitung.addActionListener( actionJMI( "kurzanleitung" ) );
		kurzanleitung.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		about = new JMenuItem("About Bohr-Profi", KeyEvent.VK_A);
		about.setToolTipText( "�bersicht der wichtigsten Funktionen." );
		about.addActionListener( actionJMI( "about" ) );
		about.setCursor( new Cursor(JFrame.HAND_CURSOR) );
		
		// add JMenuItem for 'optionen'
		hilfe.add(kurzanleitung);
		hilfe.add(about);
		
	  // ================================================================================
		
		
	  // starting layout
	  // --------------------------------------------------------------------------------
		
		// set main panels
		
		// panelGesamt
		panelGesamt = new JPanel();
		panelGesamt.setLayout(new BorderLayout());
				
		// panelNord and sub panels
		panelNord = new JPanel();
		panelNord.setLayout(new BorderLayout());
		panelGesamt.add(panelNord, BorderLayout.NORTH);
		
		panelNordLinks = new JPanel();
		panelNordLinks.setLayout(new GridBagLayout());
		panelNord.add(panelNordLinks, BorderLayout.WEST);
		
		panelNordRechts = new JPanel();
		panelNordRechts.setLayout(new GridBagLayout());
		panelNord.add(panelNordRechts, BorderLayout.EAST);
		
		// panelSued and subpanels
		panelSued = new JPanel();
		panelSued.setLayout(new BorderLayout());
		panelGesamt.add(panelSued, BorderLayout.SOUTH);
		
		panelSuedLinks = new JPanel();
		panelSuedLinks.setLayout(new GridBagLayout());
		panelSued.add(panelSuedLinks, BorderLayout.WEST);
		
		panelSuedRechts = new JPanel();
		panelSuedRechts.setLayout(new GridBagLayout());
		panelSued.add(panelSuedRechts, BorderLayout.EAST);
		
		// platine
		platine = new JScrollPane();
		platine.setCursor(new Cursor(JFrame.DEFAULT_CURSOR));
		platine.setPreferredSize(new Dimension(900, 600));
		panelGesamt.add(platine, BorderLayout.CENTER);
		platine.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				if ( aktiviert )
					mouseClickOnPlatine(e);
			}
		
		});
		platine.addMouseMotionListener(new MouseMotionAdapter() 
		{	 
			public void mouseMoved(MouseEvent e) 
			{
				if ( aktiviert )
					switch ( e.getID() )
					{
			
			    		case Event.MOUSE_MOVE :
							mouseCoordX.setText("" + (double)Math.round(
													 (((e.getX())*mouseClick.maxX)/(mouseClick.breite - 10) + mouseClick.minX)
													 *1000)/1000 );
							mouseCoordY.setText("" + (double)Math.round(
									 				 (((e.getY())*mouseClick.maxY)/(mouseClick.hoehe - 10) + mouseClick.minY)
									 				 *1000)/1000 );
							break;
						case Event.MOUSE_EXIT :
							mouseCoordX.setText("- - -");
							mouseCoordY.setText("- - -");
							break;
					}
			}
		});
		
		// disabled functions
		speichern.setEnabled(false);
		speichernUnter.setEnabled(false);
		bearbeiten.setEnabled(false);
		optionen.setEnabled(false);
		
	  // ================================================================================
		
		
		// add main components
        this.setJMenuBar(menueLeiste);
		this.getContentPane().add(panelGesamt, BorderLayout.CENTER);

        this.pack();
    } // initComponents ()


/*** methods for conversation **********************************************************/
	
	//neues Teil
	/*
	 * method to set the fortschritt
	 * @param fortschrittswert the actual value to set
	 * 
	 */
	public void setFortschritt(int fortschrittswert)
	{
		// set fortschritt 
			fortschritt.setValue( fortschrittswert );	
	}
	
	public void setValues ( Vertex[] array, double schranke, double weglaenge )
	{
		// set lower bound
		untereSchranke.setText("" + schranke );
		
		// set length of actual way
		weg.setText("" + weglaenge );
		
		// compute guete
		double good = ((double)Math.round( ((weglaenge/schranke)*100)*100 ))/100;
		guete.setText( "" + good );
		
		// save as sol
		solSpeichernAction(array, weglaenge, good);
		//System.out.println("gespeichert");
		
		// set value to activate paint with array
		mouseClick.alsArray = true;
		
		// repaint platine
		mouseClick.copyToMCArray( array );
		mouseClick.repaint();
		//System.out.println("repaint");
	} // setValues()
	
	
	public GridBagConstraints setGridBagConstraints ( int x, int y )
	{
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		
		return gridBagConstraints;
	} // setGridBagConstraints ()
	
	
	private void activatePlatine ()
	{		
		aktiviert = true;
		mouseClick.platineAktiv = true;
		mouseClick.repaint();
	} // actuvatePlatine ()
	
	
	private void editierAnzeigen ()
	{
		// activate panelSued
		panelGesamt.add(panelSued, BorderLayout.SOUTH);
		
		// panelSuedLinks
		panelSuedLinks.removeAll();
		reset = new JButton( "Reset" );
		reset.addActionListener( actionJB( "reset" ) );
		reset.setCursor(new Cursor(JFrame.HAND_CURSOR));
    	panelSuedLinks.add(reset, setGridBagConstraints(0, 0));
		
		schreibschutz = new JCheckBox( "Schreibschutz" );
		schreibschutz.addActionListener( actionJB( "schreibschutz" ) );
		schreibschutz.setCursor(new Cursor(JFrame.HAND_CURSOR));
		panelSuedLinks.add(schreibschutz, setGridBagConstraints(1,0));
	
		// panelSuedRechts
		panelSuedRechts.removeAll();
		punktLabel = new JLabel( "Gew�hlter Punkt (Nr., x, y): " );
		panelSuedRechts.add(punktLabel, setGridBagConstraints(0, 0));
		
		number = new JTextField(4);
		number.setEditable(false);
		number.setBackground(Color.white);
		panelSuedRechts.add(number, setGridBagConstraints(1,0));

		coordX = new JTextField(10);
		coordX.setEditable(false);
		coordX.setBackground(Color.white);
		panelSuedRechts.add(coordX, setGridBagConstraints(2,0));

		coordY = new JTextField(10);
		coordY.setEditable(false);
		coordY.setBackground(Color.white);
		panelSuedRechts.add(coordY, setGridBagConstraints(3,0));
		
		mouseCoordLabel = new JLabel( "Aktuelle Cursor-Position (x, y): " );
		panelSuedRechts.add(mouseCoordLabel, setGridBagConstraints(0, 1));
		
		mouseCoordX = new JTextField(10);
		mouseCoordX.setEditable(false);
		mouseCoordX.setBackground(Color.white);
		panelSuedRechts.add(mouseCoordX, setGridBagConstraints(2,1));

		mouseCoordY = new JTextField(10);
		mouseCoordY.setEditable(false);
		mouseCoordY.setBackground(Color.white);
		panelSuedRechts.add(mouseCoordY, setGridBagConstraints(3,1));
	} // editierAnzeigen ()
	
	
	
	private void wegBerechnenAnzeigen ()
	{
		
		// activate panelNord
		panelGesamt.add(panelNord, BorderLayout.NORTH);
		
		// panelNordLinks
		panelNordLinks.removeAll();
		fortschrittLabel = new JLabel( "Fortschritt der Berechnung" );
		panelNordLinks.add(fortschrittLabel, setGridBagConstraints(2, 0));
	
		fortschritt = new JProgressBar();
		fortschritt.setIndeterminate(true);
		//neues Teil prozent hinschreiben 
		fortschritt.setStringPainted(true);
		fortschritt.setMaximum(100);
		fortschritt.setMinimum(0);
		fortschritt.setIndeterminate(false);
		panelNordLinks.add(fortschritt, setGridBagConstraints(3, 0));
	
		rechenzeitLabel = new JLabel( " Rechenzeit ( in Sekunden )" );
		panelNordLinks.add(rechenzeitLabel, setGridBagConstraints(0, 0));
                
		rechenzeit = new JTextField(10);
		rechenzeit.setEditable(false);
		rechenzeit.setBackground(Color.white);
		panelNordLinks.add(rechenzeit, setGridBagConstraints(1, 0));
		
		abbruchzeitLabel = new JLabel( " Abbruchzeit ( in Sekunden ) " );
		panelNordLinks.add(abbruchzeitLabel, setGridBagConstraints(0, 1));
                
		abbruchzeit = new JTextField("   - - - -", 10);
		abbruchzeit.setEditable(false);
		abbruchzeit.setBackground(Color.white);
		panelNordLinks.add(abbruchzeit, setGridBagConstraints(1, 1));
		
		gueteLabel = new JLabel( "  G�te der L�sung ( in Prozent ) " );
		panelNordLinks.add(gueteLabel, setGridBagConstraints(2, 1));
		
		guete = new JTextField(10);
		guete.setEditable(false);
		guete.setBackground(Color.white);
		panelNordLinks.add(guete, setGridBagConstraints(3, 1));
		
		
				
		// panelNordRechts
		panelNordRechts.removeAll();
		untereSchrankeLabel = new JLabel( "Untere Schranke " );
		panelNordRechts.add(untereSchrankeLabel, setGridBagConstraints(0, 0));
	
		untereSchranke = new JTextField( 10 );
		untereSchranke.setEditable(false);
		untereSchranke.setBackground(Color.white);
		panelNordRechts.add(untereSchranke, setGridBagConstraints(1, 0));
	
		wegLabel = new JLabel( "Aktuelle Wegl�nge " );
		panelNordRechts.add(wegLabel, setGridBagConstraints(0, 1));
	
		weg = new JTextField( 10 );
		weg.setEditable(false);
		weg.setBackground(Color.white);
		panelNordRechts.add(weg, setGridBagConstraints(1, 1)); 
		
		// activate panelSued
		panelGesamt.add(panelSued, BorderLayout.SOUTH);
		
		// panelSuedLinks
		panelSuedLinks.removeAll();
		start = new JButton( "Start" );
		start.setCursor(new Cursor(JFrame.HAND_CURSOR));
		start.addActionListener( actionJB( "start" ) );
		panelSuedLinks.add(start);
				
		// panelSuedRechts
		panelSuedRechts.removeAll();
		stop = new JButton( "Stopp" );
		stop.setCursor(new Cursor(JFrame.HAND_CURSOR));
		stop.addActionListener( actionJB( "stop" ) );
		panelSuedRechts.add(stop);
	} // wegBerechnenAnzeigen ()
	
	
	private void mouseClickOnPlatine(MouseEvent e) 
    {
        // bei l�schen vergleichen alle mit den koordinaten
        Vertex punkt = new Vertex();
		mouseClick.punktLinien = false;
        
        
        if (mouseClick.nurZeigen)
        {    
        	punkt = (Vertex)mouseClick.insertOrRemove( new Vertex(mouseClick.maxNumber, 
        														  e.getPoint().getX(), 
        														  e.getPoint().getY()) );	
        }
        else
        {
			mouseClick.maxNumber++;
			punkt = (Vertex)mouseClick.insertOrRemove( new Vertex(mouseClick.maxNumber, 
															 	  e.getPoint().getX(), 
															 	  e.getPoint().getY()) );
			gespeichert = false;
        }
           
		if (punkt != null)
		{
			coordX.setText("" + ((double)Math.round( (punkt.getX())*1000) )/1000 );
			coordY.setText("" + ((double)Math.round( (punkt.getY())*1000) )/1000 );
			number.setText("" + punkt.getNum());
		}   
	   	mouseClick.repaint(); 
    } // mouseClickOnPlatine ()

	
	private boolean vorherSpeichern() 
	{
		if(!gespeichert)
		{
			// frame for dialog box
			JFrame dialog = new JFrame();
			dialog.setLocation(450, 250);
			Object[] options = {"Ja", "Nein"};
			
			// make dialog box
			int n = JOptionPane.showOptionDialog(dialog,	      // start frame
					"M�chten Sie das aktuelle PCB vorher speichern?", // frame text
					"Vorher speichern",							  	  // frame bar text
			JOptionPane.YES_NO_OPTION,	                  	  // YES button
			JOptionPane.QUESTION_MESSAGE,				      // icon
			null,                                         	  // ???
			options,									      // button text
			null);								  		      // default button		
			// set action on YES button				
			if (n == JOptionPane.YES_OPTION)
				return true;
			// set action on NO button
			else
				return false;
		}
		else 
			return false;
	} // vorherSpeichern ()
	
			
/*** ActionListener ********************************************************************/

	
	/**
	 * ActionListener for the JMenuItems
	 */			
	private ActionListener actionJMI( final String str )
	{
		return new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				// choose the correct action for the given string
				
				// for datei
				if ( str.equals("neu") )
					neuAction();
				if ( str.equals("oeffnen") )
					oeffnenAction();
				if ( str.equals("speichern") )
					speichernAction();					
				if ( str.equals("speichernUnter") )
					speichernUnterAction();	
				if ( str.equals("beenden") )
					beendenAction();
				
				// for bearbeiten
				if ( str.equals("wegBerechnen") )
					wegBerechnenAction();
				if ( str.equals("editieren") )
					editierenAction();
				
				// for opionen
				if ( str.equals("liste") )
					zeigeListeAction();
				if ( str.equals("einstellungen") )
					AbbruchzeitFensterAction();
					
				// for hilfe
				if ( str.equals("kurzanleitung") )
					HilfeFensterAction();
				if ( str.equals("about") )
					aboutAction();
			}
		};
	} // actionJMI ()
	
	
	/**
	 * ActionListener for the JButtons
	 */			
	private ActionListener actionJB( final String str )
	{
		return new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				// choose the correct action for the given string
				// for panelSued
				if ( str.equals("reset") )
				{
					gespeichert = false;
					resetAction();
				}
				if ( str.equals("schreibschutz") )
					schreibschutzAction();
				
				// for panelSued
				if ( str.equals("start") )
					startAction();
				if ( str.equals("stop") )
					stopAction();
			}
		};
	} // actionJB ()


/*** actionJMI () methods ***************************************************************/

	private void AbbruchzeitFensterAction ()
	{
		new AbbruchzeitFenster(this);
	}
	
	private void HilfeFensterAction()
	{
		new HilfeFenster(this, "hilfe.txt");
	}
	
	private void aboutAction()
	{
		new HilfeFenster(this, "about.txt");
	}
	
	private void neuAction() 
	{
		// if a PCB is open and ending is .pnt
		if ( speichern.isEnabled() &&  dateiformat.equals("pnt") )
			if ( vorherSpeichern() )
				speichernAction();
		
		dateipfad = "";
		
		// enable functions
		speichern.setEnabled(true);
		speichernUnter.setEnabled(true);
		bearbeiten.setEnabled(true);
		wegBerechnen.setEnabled(true);
		optionen.setEnabled(true);
		
		// disable special functions
		editieren.setEnabled(false);
		einstellungen.setEnabled(false);

		
		// set default color for points and lines
		mouseClick.point = Color.BLACK;
		mouseClick.linien = Color.RED;
		LinKernighan.startTouren = 1;
		
		new PCBDimensionFenster(this);
		
		activatePlatine();
		resetAction();
				
		// set dateiformat
		dateiformat = "pnt";
		
		// set layout for pcb editing
		this.setTitle( "Bohr-Profi 1.0                              " + 
					   "neuesPCB.pnt" );
		editierenAction();
		
		// set gespeichert false
		gespeichert = false;
		
		this.repaint();
	} // neuAction ()
		
	
	private void showingSolAnzeigen ()
	{
		// disable some functions
		bearbeiten.setEnabled(false);
		speichern.setEnabled(false);
		speichernUnter.setEnabled(false);
		einstellungen.setEnabled(false);
		
		// enable some functions
		optionen.setEnabled(true);
		
		// show special functions
		panelSuedLinks.removeAll();
		panelSuedRechts.removeAll();
		panelGesamt.remove(panelSued);
		panelNordLinks.removeAll();
		panelNordRechts.removeAll();
		//panelGesamt.remove(panelNord);
		
		gueteLabel = new JLabel( "G�te der L�sung ( in Prozent ) " );
		panelNordLinks.add(gueteLabel, setGridBagConstraints(0, 0));
		
		guete = new JTextField(10);
		guete.setEditable(false);
		guete.setBackground(Color.white);
		panelNordLinks.add(guete, setGridBagConstraints(1, 0));
		
		wegLabel = new JLabel( "Wegl�nge " );
		panelNordRechts.add(wegLabel, setGridBagConstraints(0, 0));
	
		weg = new JTextField( 10 );
		weg.setEditable(false);
		weg.setBackground(Color.white);
		panelNordRechts.add(weg, setGridBagConstraints(1, 0));
		
		panelGesamt.add(panelNord, BorderLayout.NORTH);
		
		this.pack();
		
		platine.setCursor( new Cursor(JFrame.DEFAULT_CURSOR) );
		
		// set value to activate paint with array
		mouseClick.alsArray = true;
		// set value to deactivate MouseListener
		aktiviert = false;
	} // showingSolAnzeigen ()
	
	
	/**
	 * creates an open-dialog to open an existing pnt, nod or sol file
	 */	
	private void oeffnenAction() 
	{
		// ask for saving if a PCB is open and ending is .pnt
		if ( speichern.isEnabled() &&  dateiformat.equals("pnt") )
			if ( vorherSpeichern() )
				speichernAction();
		
		// make open dialog
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Oeffnen");
		chooser.setLocation(450, 250);
		// get file filter for all and remove it, to show only pnt and sol
		FileFilter all = chooser.getFileFilter();
		chooser.removeChoosableFileFilter(all);
		// file chooser for sol
		chooser.setFileFilter( new FileFilter() 
		{
			public boolean accept( File f ) 
			{
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".sol");
	        }
	        public String getDescription() 
			{
	            return ".sol";
	        }
		} );
		// file chooser for pnt
		chooser.setFileFilter( new FileFilter() 
		{
			public boolean accept( File f ) 
			{
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".pnt");
	        }
	        public String getDescription() 
			{
	            return ".pnt";
	        }
		} );
		// initial dialog
		int returnVal = chooser.showOpenDialog(null);
		
		// set default color for points and lines
		mouseClick.point = Color.BLACK;
		mouseClick.linien = Color.RED;
		LinKernighan.startTouren = 1;
		
		// action for open button is pressed
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			// actiavte platine
			activatePlatine();
			
			// enable functions for calling open dialog
			speichern.setEnabled(true);
			speichernUnter.setEnabled(true);
			bearbeiten.setEnabled(true);
			optionen.setEnabled(true);
			
			// clear memeory
			resetAction();
			// deactivate save dialog for open or make new pcb after these action
			gespeichert = true;

			
			// open the file:
			
			// get the whole file path
			dateipfad = chooser.getSelectedFile().getAbsolutePath();
			// reads out the ending
			dateiformat = reinraus.datenformat(dateipfad);
			// get file name
			dateiname = chooser.getSelectedFile().getName();
			
			// action for pnt ending
			if ( dateiformat.equals("pnt") )
			{
				// open file
				mouseClick.punkte = reinraus.einlesen( dateipfad );
				// switch to editor mode
				editierenAction();
			}
			
            // action for nod ending
			if ( dateiformat.equals("nod") )
			{
				// open file
				mouseClick.punkte = reinraus.einlesen( dateipfad );
				// switch to editor mode
				editierenAction();
			}
			
			 // action for sol ending
			if ( dateiformat.equals("sol") )
			{	
				// switch to showing sol mode
				showingSolAnzeigen();
				
				// open file
				DoubleList hilfsListe = reinraus.einlesen( dateipfad );
				
				hilfsListe.resetToHead();
				// set length of the way
				weg.setText("" + hilfsListe.currentData());
				hilfsListe.increment();
				// set goodness
				guete.setText("" + hilfsListe.currentData());
				hilfsListe.increment();
				
				// get list of points
				mouseClick.punkte = (DoubleList)hilfsListe.currentData();
				mouseClick.copyToMCArray(
				mouseClick.doubleListToArray(mouseClick.punkte) );
				
				// set nurZeigen false
				mouseClick.nurZeigen = false;
			}
			
			// convert the coordinate system and repaint
			mouseClick.convertThis();
			mouseClick.repaint();
			
			// cut the file ending of dateipfad
			dateipfad = dateipfad.substring(0, dateipfad.lastIndexOf('.'));
			
			// set new window title with the file name
			this.setTitle( "Bohr-Profi 1.0                              " + 
			    		   dateiname );
		}
		
		this.repaint();
	} // oeffnenAction ()

	
	/**
	 * saves the current pcb
	 */
	private void speichernAction()
	{
		// new enumeration for the points
		mouseClick.newEnumeration();
		
		// file path exists: save the file
		if ( !dateipfad.equals("") )
		{
			reinraus.schreibePNT( mouseClick.punkte, dateipfad );
			gespeichert = true;
		}
		// file path doesn't exist: call save-as-dialog
		else
			speichernUnterAction();
	} // speichernAction()
	
	
	/**
	 * creates a save-as-dialog to save the current pcb as a pnt file
	 */
	private void speichernUnterAction() 
	{
		// make save dialog
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Speichern als .pnt"); 
		chooser.setLocation(450, 250);
		int returnVal = chooser.showSaveDialog(null);
		
		// action for save button prssed
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			// new enumeration for the points
			mouseClick.newEnumeration();
			
			// save file
			reinraus.schreibePNT( mouseClick.punkte,
							      chooser.getSelectedFile().getAbsolutePath() );
			// set saved
			gespeichert = true;
			
			// get file name
			dateiname = chooser.getSelectedFile().getName()+ ".pnt";
			
			// set new window title with the file name				        
			this.setTitle( "Bohr-Profi 1.0                              " + 
						   dateiname );
		}
	} // speichernUnterAction ()


	/**
	 * saves the solution of a computed optimal way as a sol file
	 * the file name is the name of the belonging pnt file
	 * 
	 * @param vertexArray the Vertex array to save
	 * @param length the computed length of the way
	 * @param good the ggoodness of the solution
	 */
	private void solSpeichernAction( Vertex[] vertexArray, double length, double good )
	{
		// do only if the belonging file is saved
		if(gespeichert)
		{
			reinraus.schreibeSOL(vertexArray, dateiname, 
						         dateipfad, length, good );
		}
	} // solSpeichernAction()
	
	
	private void beendenAction() 
	{
		// frame for dialog box
		JFrame dialog = new JFrame();
				
		Object[] options = {"Ja", "Nein"};
				
		// make dialog box
		int n = JOptionPane.showOptionDialog(dialog,		  	  // start frame
				"M�chten Sie das Programm wirklich beenden? " +
				"Nicht gespeicherte Daten gehen dabei verloren!", // frame text
				"Programm beenden",							  	  // frame bar text
				JOptionPane.YES_NO_OPTION,	                  	  // YES button
				JOptionPane.QUESTION_MESSAGE,				  	  // icon
				null,                                         	  // ???
				options,									  	  // button text
				null);								  		  	  // default button
					
		// set action on YES button				
		if (n == JOptionPane.YES_OPTION)
			System.exit(0);
	} // beendenAction ()
	
	
	/**
	 * creates the way computing mode
	 */
	private void wegBerechnenAction ()
	{	
		if(!gespeichert)
			vorherSpeichern();
		
		mouseClick.newEnumeration();
		
		platine.setCursor(new Cursor(JFrame.DEFAULT_CURSOR));

		// show specific functions
		panelGesamt.remove(panelSued);
		wegBerechnenAnzeigen();
		
		this.pack();
		
		// disable special functions
		wegBerechnen.setEnabled(false);
		speichern.setEnabled(false);
		speichernUnter.setEnabled(false);
		// enable special functions
		editieren.setEnabled(true);
		einstellungen.setEnabled(true);
		
		// set value to disable editing points
		mouseClick.wegberechnung = true;
		// set value to activate paint with array
		mouseClick.alsArray = true;
		// set value to deactivate MouseListener
		aktiviert = false;
		// set nurZeigen false
		mouseClick.nurZeigen = false;
		
		this.repaint();
	} // wegBerechnenAction ()
	

	/**
	 * creates the editor mode
	 */
	private void editierenAction ()
	{	
		platine.setCursor(new Cursor(JFrame.CROSSHAIR_CURSOR));
		
		// show specific functions
		editierAnzeigen();
		panelGesamt.remove(panelNord);
		
		this.pack();
		
		// clear the output array
		mouseClick.punkteArray = null;
		
		// enable special functions
		wegBerechnen.setEnabled(true);
		speichern.setEnabled(true);
		speichernUnter.setEnabled(true);
		// disable special functions
		editieren.setEnabled(false);
		einstellungen.setEnabled(false);
		
		// set default color for points and lines
		mouseClick.point = Color.BLACK;
		mouseClick.linien = Color.RED;
		
		// set value to make points editable
		mouseClick.wegberechnung = false;
		// set value to activate paint the points and lines
	    mouseClick.punktLinien = false; 				// GUCKEN, OB NOCH NOTWENDIG
		// set value to activate paint with DoubleList
		mouseClick.alsArray = false;
		// set value to activate MouseListener
		aktiviert = true;
		
		this.repaint();
	} // editierenAction ()
	
	
/*** actionJB () methods ****************************************************************/
	
	
	private void zeigeListeAction() 
    {   
       new  ListOfPoints(this).show();
    } // showListAction
	
	
	private void resetAction() 
    {
		mouseClick.punkte = new DoubleList();
		mouseClick.convert = false;
		mouseClick.maxNumber = 0;
		mouseClick.repaint();
    } // resetAction ()
	
	
	private void schreibschutzAction() 
	{
		// set so you can add and remove points
		if (mouseClick.nurZeigen)
			mouseClick.nurZeigen = false;
		// set so you can't add and remove points
		else 
			mouseClick.nurZeigen = true;
		
		mouseClick.repaint();
	} // removeOrshowAction ()
    

	private LinKernighan lkThread;
	private RunTimeThread rtThread;


	private void startAction()
	{	
		if ( !mouseClick.punkte.isEmpty() )
		{
			mouseClick.alsArray = false;
			mouseClick.repaint();
			mouseClick.alsArray = true;
		
			platine.setCursor(new Cursor(JFrame.WAIT_CURSOR));
		
			// disable other functions
			start.setEnabled(false);
			bearbeiten.setEnabled(false);
			datei.setEnabled(false);
			optionen.setEnabled(false);
			hilfe.setEnabled(false);
		
			// create the algorithm and run time Thread
			//System.out.println(mouseClick.punkte);
			lkThread = new LinKernighan(this, mouseClick.punkte);
			rtThread = new RunTimeThread(this, stopNachZeit, abbruch);
		
			// start the Threads
			rtThread.start();
			lkThread.start();
		}
	} // startAction()
        
     
	public void stopAction()
	{
		platine.setCursor(new Cursor(JFrame.DEFAULT_CURSOR));
		
		// enable other functions
		start.setEnabled(true);
		bearbeiten.setEnabled(true);
		datei.setEnabled(true);
		optionen.setEnabled(true);
		hilfe.setEnabled(true);
		
		// stop the Threads
		rtThread.stop = true;
		lkThread.STOP = true;
	} // stopAction()
        

/*** main method ***********************************************************************/
      
      
    /**
     * method for running the aplication
     */
    public static void main(String args[]) 
    {
		try 
		{   
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} 
		catch (Exception e) 
		{ 
		}

        new GUI().show();
    }
    
} // class GUI
