package main.java.LinKern.projectGUI;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Slider extends JPanel implements	
												ChangeListener
{
	//Set up animation parameters.
	   static final int FPS_MIN = 0;
	   static final int FPS_MAX = 100;
	   static final int FPS_INIT = 1;    //initial frames per second
	 
 	   // TextField  
	   JTextField picture;
	public Slider(AbbruchzeitFenster fenster) 
	{
			
			//Create the label.
			JLabel sliderLabel = new JLabel(" Einstellen der Genauigkeit ", JLabel.CENTER);
			sliderLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

			//Create the slider.
			JSlider slider = new JSlider(JSlider.HORIZONTAL,
												  FPS_MIN, FPS_MAX, FPS_INIT);
			slider.addChangeListener(this);
			slider.setCursor(new Cursor(JFrame.HAND_CURSOR));
			
			//Turn on labels at major tick marks.
			slider.setMajorTickSpacing(20);
			slider.setMinorTickSpacing(5);
			
			// set the underlines
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			
			slider.setBorder(
					   BorderFactory.createEmptyBorder(0,0,10,0));
					   
			// create the textfield for the actual slider position		   
			picture = new JTextField(4);
			picture.setSize(10,10);
			picture.setEditable(false);
			picture.setBackground(Color.WHITE);
			picture.setHorizontalAlignment(JLabel.CENTER);
			
						
			//set the init value			
			picture.setText("" + FPS_INIT );
			
			//Put everything together.
			add(sliderLabel);
			add(slider);
			add(picture);
			setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		}
		/** Listen to the slider. */
			public void stateChanged(ChangeEvent e) 
			{
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) 
				{
					int fps = (int)source.getValue();
					if(fps == 0)
						fps = 1;
					picture.setText("" + fps);
				}
			}
			
}
