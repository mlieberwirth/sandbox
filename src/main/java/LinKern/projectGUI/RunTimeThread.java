package main.java.LinKern.projectGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.java.LinKern.projectLinKernighan.LinKernighan;

public class RunTimeThread extends Thread
{
	// object variables
	private GUI gui;
	private boolean stopNachZeit = false;
	private int stopZeit = 0;
	// run time variables
	private final int ONE_SECOND = 1000;
	private Timer timer;
	private int timeSec = 0;
	protected boolean stop = false;
	
	
	public RunTimeThread ( GUI _gui, boolean stopAfterTime, int stopTime )
	{
		gui = _gui;
		
		stopNachZeit = stopAfterTime;
		stopZeit = stopTime;
	}
	
	
	private void initialTimer ()
	{
		// activate the timer
		timer = new Timer(ONE_SECOND, new ActionListener()
		{ 
			public void actionPerformed(ActionEvent time)
			{
				if (timeSec == 0)
					gui.rechenzeit.setText("< 1");
				
				startTimer(time);
			}
		});
		
		// start the timer
		timer.start();
	} // initialTimer ()
	
	
	private void startTimer(ActionEvent time)
	{
		// count up the seconds
		timeSec++;
		
		// show run time while not stopped
		if ( !stop )
			gui.rechenzeit.setText("" + timeSec);
	
		// break if break time is reached
		if ( stopNachZeit && (timeSec == stopZeit) )
		{
			LinKernighan.STOP = true;
			
			timer.stop();
			timeSec = 0;
		}
		// break if stop is actiavted
		if (stop)
			timer.stop();
	} // startTimer ()
	
	
	public void run() 
	{
		initialTimer();
	}
} // class RunTimer