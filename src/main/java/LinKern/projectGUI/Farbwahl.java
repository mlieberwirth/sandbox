/*
 * Created on 03.07.2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package main.java.LinKern.projectGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;


/**
 * @author Nico
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Farbwahl extends JPanel implements ChangeListener, ActionListener
{
	protected JColorChooser tcc;
	// Color for the lines
	protected  Color actual ;
	// Color for the points
	protected Color current;
	
	protected JTextField eins;
	protected JTextField zwei;
	
	static String string1 = "Kantenfarbe";
	static String string2 = "Punktfarbe";
	static String string3 = "beide";
	
	// boolean to locate the option between points and lines
	// true for lines ,false for points 
	protected boolean auswahl = true;
	protected boolean aus = false;
	protected boolean both = false;
	public Farbwahl(GUI gui) 
	{
		super(new BorderLayout());
		
		eins = new JTextField(5);
		eins.setEditable(false);
		
		zwei = new JTextField(5);
		zwei.setEditable(false);
		
		JRadioButton lines = new JRadioButton(string1);
		lines.setActionCommand(string1);
		lines.setSelected(true);
		lines.setFocusPainted(true);
		lines.setCursor(new Cursor(JFrame.HAND_CURSOR));
		
		JRadioButton points = new JRadioButton(string2);
		points.setActionCommand(string2);
		points.setCursor(new Cursor(JFrame.HAND_CURSOR));
		
		
		JRadioButton beide = new JRadioButton(string3);
		beide.setActionCommand(string3);
		beide.setCursor(new Cursor(JFrame.HAND_CURSOR));
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(lines);
	    group.add(points);
	    group.add(beide);
	    
	    //Register a listener for the radio buttons.
	    lines.addActionListener(this);
	    points.addActionListener(this);
	    beide.addActionListener(this);
	    
	    JPanel radioPanel = new JPanel(new GridBagLayout());
	    radioPanel.add(lines, gui.setGridBagConstraints(0,0));
	    radioPanel.add(eins, gui.setGridBagConstraints(0,1));
        radioPanel.add(points, gui.setGridBagConstraints(1,0));
        radioPanel.add(zwei, gui.setGridBagConstraints(1,1));
        radioPanel.add(beide, gui.setGridBagConstraints(2,0));
        add(radioPanel, BorderLayout.LINE_START);
        
       

	    
		actual = Color.RED;
		current = Color.BLACK;
		
		//Set up color chooser for setting text color
		tcc = new JColorChooser(Color.RED);
        tcc.getSelectionModel().addChangeListener(this);
		tcc.setCursor(new Cursor(JFrame.HAND_CURSOR));
		this.setBorder(BorderFactory.createTitledBorder(
												   "Wahl der Farben f�r Kanten und Punkte"));
		
		add(tcc, BorderLayout.PAGE_END);
	}

    /** Listens to the radio buttons. */
    public void actionPerformed(ActionEvent e)
    {
    	String str = e.getActionCommand();
    	
    	if ( str.equals(string3))
    	{	
    		auswahl = false;
    		aus = false;
    		both = true;
    		return;
    	}
    	
    	if (str.equals(string1))
    	{	
    		both = false;
    		aus = false;
    		auswahl = true;
    		return;
    	}
    	if (str.equals(string2))
    	{  
    		both = false;
    		auswahl = false;
    		aus = true;
    	}
    }
	public void stateChanged(ChangeEvent e) 
	{
		Color neu = tcc.getColor();
		
		if (both)
		{	
			actual = neu;
			current = neu;
			eins.setBackground(neu);
			zwei.setBackground(neu);
		}
	    if(auswahl)
	    {
	    	actual = neu;
	    	eins.setBackground(neu);
	    }	
	    if(aus)
	    {
	    	current = neu;
	    	zwei.setBackground(neu);
	    }
	}
   
}