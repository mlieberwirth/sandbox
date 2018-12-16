package main.java.LinKern.projectGUI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

import main.java.LinKern.comaList.DoubleList;
import main.java.LinKern.projectGraph.Vertex;


/**
 * creates a new JComponent:
 * makes a drawing field to set points on and handles the points in a DoubleList 
 */
public class MouseClick extends JComponent
{
/*** data ******************************************************************************/


	// variables to set from inside and outside
	protected boolean platineAktiv = false,
    		  		  punktLinien = false,
			  		  convert = false,
					  nurZeigen = false,
					  alsArray = false,
					  wegberechnung = false;
	protected int breite = 0, 
				  hoehe = 0,
				  maxNumber = 0;
	protected DoubleList punkte = new DoubleList();
    // inside variables
	private double fehler = 5;
	protected double maxX = 0, 
				     maxY = 0,
				     minX = 0,
				     minY = 0;
    private Vertex punkt1 = new Vertex(),
           		   punkt2 = new Vertex();
//  neues Teil farbw�hler
	protected Color linien = Color.RED;
	protected Color point = Color.BLACK;

/*** �nderung 1 *********************************************************************/
    protected Vertex[] punkteArray;

    
/*** constructor ***********************************************************************/ 

	
	/**
	 * default constructor
	 */    
	public MouseClick () 
	{
    }
   
    
/*** method for graphical showing ******************************************************/

	public void repaint()
	{
		update( this.getGraphics());
	}
	
	public void update( Graphics g )
	{
		paint( g );
	}
	
	
	/**
	 * creates the pcb field for adding and removing points
	 * draws the points and lines
	 */
    public void paint (Graphics g)
    {
    	
		// color: green for activated pbc and white for starting the program
    	if (platineAktiv)
    		g.setColor(new Color(100,153,100));
    	else
			g.setColor(Color.WHITE);
        
		// activate the coordinates to set points in
        g.fillRect(0,0, breite, hoehe);
		
		if (!platineAktiv)
        {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Serif", Font.BOLD, 120));
                g.drawString("Bohr", 320, 200);

                g.setColor(Color.GRAY);
                g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 60));
                g.drawString("Profi", 385, 260);

                g.setColor(Color.LIGHT_GRAY);
                g.setFont( new Font("Courier", Font.BOLD, 25));
                g.drawString("Version 1.0", 375, 350);
                g.drawString("� 2005 by PlanB ", 340, 380);
        }

		
		// Vertex[] is basis to show points and lines
        if ( alsArray && (punkteArray != null) )
        {
        	for ( int i = 0; i < punkteArray.length; i++)
        	{
        		punkt1 = convertToShow( punkteArray[i] );
        		
        		if ( i < (punkteArray.length - 1) )
        		{
        			punkt2 = convertToShow( punkteArray[i+1] );
        			
					g.setColor(point);
					g.fillOval((int)punkt1.getX(), (int)punkt1.getY(), 4,4);
						
					g.setColor(linien);
					g.drawLine((int)punkt1.getX(), (int)punkt1.getY(),
							   (int)punkt2.getX(), (int)punkt2.getY() );
					
        		}
				// link the last with the first point
        		else
        		{
					punkt2 = convertToShow( punkteArray[0] );
					
					g.setColor(point);
					g.fillOval((int)punkt1.getX(), (int)punkt1.getY(), 4,4);
					
					g.setColor(linien);
					g.drawLine((int)punkt1.getX(), (int)punkt1.getY(),
							   (int)punkt2.getX(), (int)punkt2.getY() );
        		}
        	}
        }
		// DoubleList is basis to show the points
        else
        {
        	if (!punkte.isEmpty())
        		for(punkte.resetToHead(); !punkte.isAtTail(); punkte.increment())
        		{
        			// draw the points in the field
            		if (!punktLinien)
            		{ 
			   			punkt1 = convertToShow((Vertex)punkte.currentData());
               			g.setColor(point);
               			g.fillOval((int)punkt1.getX(), (int)punkt1.getY(), 4,4);
            		}
        		} // endfor
        }
	} // paint ()
	
	
/*** methods for converting the pcb ****************************************************/
	
	
	/**
	 * converts the pcb coordinate system to the needed values
	 */
   	public void convertThis ()
   	{
   		convert = true;
		findMaxCoord();
		findMaxNumber();
		
		repaint();
   	} // convertIfNeeded ()
    
    
    /**
     * converts a point of the pcb to the original coordinates for the list
     * 
     * @param element the point to convert
     * @return the new converted point for the list
     */
	public Vertex convertToList (Vertex element)
	{
		// convert the point
		if (convert)
		{
			// new value for fehler
			fehler = (5*maxX)/900;
			
			// the new converted point
			return new Vertex( element.getNum(), ((element.getX())*maxX )/(breite - 10) + minX,
									  			 ((element.getY())*maxY ) /(hoehe - 10) + minY);  
		}
		// convert isn't needed
		else 
		{ 
			fehler = 5;
			
			return element;
		}
	} // convertToList ()
    
    
    /**
     * converts a point of the list to the correct coordinates to show on pbc
     * 
     * @param element the point to convert 
     * @return the new converted point to show on pbc
     */
	public Vertex convertToShow (Vertex element)
	{
		// convert the point
		if (convert)
		{
			// new value for fehler
			fehler = (5*maxX)/900;
			 
			return new Vertex( element.getNum(), ((breite - 10) * (element.getX()- minX) )/maxX , 
									  			 ((hoehe - 10)  * (element.getY()- minY) )/maxY );  
		}
		// convert isn't needed
		else
		{ 
			fehler = 5;
			
			return element;
		}
	} // convertToShow()
    
    
    /**
     * find and set maximum values for the x and y value of the pbc
     * maximum x -> maxX
     * maximum y -> maxY
     */
	public void findMaxCoord ()
	{
		maxX = maxY = 0;
		minX = minY = Double.longBitsToDouble(0x7ff0000000000000L);;
		if (!punkte.isEmpty())
			for(punkte.resetToHead(); !punkte.isAtTail(); punkte.increment())
			{
				if ( ((Vertex)punkte.currentData()).getX() > maxX )
					maxX = ((Vertex)punkte.currentData()).getX();
                	
				if ( ((Vertex)punkte.currentData()).getY() > maxY )
					maxY = ((Vertex)punkte.currentData()).getY();
				
				if ( ((Vertex)punkte.currentData()).getX() < minX )
					minX = ((Vertex)punkte.currentData()).getX();
                	
				if ( ((Vertex)punkte.currentData()).getY() < minY )
					minY = ((Vertex)punkte.currentData()).getY();
			}
		maxX = maxX - minX;
		
		maxY = maxY - minY;
		
	} // findMaxCoord ()
    
    
    /**
     * finds the highest point number and set it 
     * highest number -> maxNumber 
     */
	public void findMaxNumber ()
	{
		maxNumber = 0;
		
		if (!punkte.isEmpty())
			for(punkte.resetToHead(); !punkte.isAtTail(); punkte.increment())
				if ( ((Vertex)punkte.currentData()).getNum() > maxNumber )
					maxNumber = ((Vertex)punkte.currentData()).getNum();
	} // findMaxNumber ()
    
    
/*** methods for conversation **********************************************************/
    
    
	/**
	 * transforms a DoubleList of Vertex to a Vertex array
	 * 
	 * @param liste the DoubleList to transform
     * @return the Vertex array of liste
	 */
	public Vertex[] doubleListToArray ( DoubleList liste )
	{
		Vertex[] array = new Vertex[liste.length()];
		liste.resetToHead();
		
		for( int i = 0; i < array.length; i++ )
		{
			array[i] = (Vertex)liste.currentData();
			liste.increment();
		}
		
		return array;
	} // doubleListToArray ()
    
    
	/**
	 * transforms an outside array of Vetex to an inside one
	 * 
	 * @param array the Vertex[] to transform
	 */
	public void copyToMCArray ( Vertex[] array )
	{
			punkteArray = new Vertex[array.length];
		
			for( int i = 0; i < punkteArray.length; i++ )
				punkteArray[i] = array[i];
	} // copyToMCArray ()
    
    
    /**
     * insert a point in the list or removes a point if it exist anyway
     * 
     * @param element the point to insert or remove
     * @return the inserted/removed point, null otherwise
     */
	public Vertex insertOrRemove (Vertex element)
    {
    	// data
    	Vertex e; 
        
        // remove point of list
        if (!punkte.isEmpty())
        	for(punkte.resetToHead(); !punkte.isAtTail(); punkte.increment())
        	{
        		// checks if element is in the list
            	if (ifMustRemoved(convertToList(element),(Vertex)punkte.currentData())) 
            	{
                	// the point to remove
                	e = (Vertex)punkte.currentData();
                	
                	// remove point if allowed by user
                	if ( !nurZeigen && !wegberechnung )
						punkte.remove((Vertex)e);
                 
                	return (Vertex)e;
            	}
            
        	}
        	
        // insert point in list if allowed by user
		if ( !nurZeigen && !wegberechnung )
        {        
			punkte.insertAtTail(convertToList(element));
			
            return (Vertex)convertToList(element);   
       	}
        else 
        	return null;
    } // insertOrRemove()
     
    
    /**
     * checks if an existing point is to remove
     * 
     * @param element the new point to check for
     * @param existPoint the existing point to look at
     * @return true if existPoint is to remove, false if not
     */
	public boolean ifMustRemoved(Vertex element, Vertex existPoint)
	{
		// existPoint to remove
		if ((Math.abs(element.getX() - existPoint.getX()) < fehler) && 
			(Math.abs(element.getY() - existPoint.getY()) < fehler)) 
			return true;
		// existPoint don't remove
		else 
			return false;
	} // ifMustRemoved ()
	
	
	/**
	 * enumerates the list of points new from 1 to length of list
	 */
	public void newEnumeration ()
	{
		int i = 1;
		
		for(punkte.resetToHead(); !punkte.isAtTail(); punkte.increment())
		{
			((Vertex) punkte.currentData()).setNum(i);
			
			i++;
		}
	} // neueNummerierung ()
} // class MouseClick