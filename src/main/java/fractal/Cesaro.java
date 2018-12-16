package main.java.fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;


public class Cesaro extends JFrame{

	final AffineTransform FLIP_X_COORDINATE;

	private Random _rand;

	public Cesaro() {
		super("Cesaro");

		_rand = new Random(0);

		setBackground(Color.white);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1300, 1300);
		FLIP_X_COORDINATE = new AffineTransform(1, 0, 0, -1, 0, getHeight() / 2);
		setVisible(true);
	}
	

    public void creatFractalTOP (List<Double> pointsX,List<Double> pointsY){
    	
    	int neigung = 4;
    	
    	// 1000 in X
    	int size = pointsX.size();
    	int count = 0;
    	
    	for (int i = 0; i < size-1; i++) {
				    	
	    	double oldXS = pointsX.get(count);
	    	double oldXE = pointsX.get(count+1);
	    		
	    	double oldYS = pointsY.get(count);
	    	double oldYE = pointsY.get(count+1);
	    	
	    	double dist = oldXE-oldXS;
	    	    	
	    	double x1 = dist/2+oldXS;
	    	
	    	double newX = x1;
	    	
	    	double newY = dist/(2*neigung)*Math.sqrt(3)+oldYS;
	    	
	    	// senkrecht
	    	if(Math.round(oldXE*10000)/10000. == Math.round(oldXS*10000)/10000.){
	    		
	    		dist = oldYE-oldYS;
	    		newX = oldXE -  dist/(2*neigung)*Math.sqrt(3);
	    		newY = dist/2+oldYS;
	    	
	    		// waagerecht
	    	}else if(Math.round(oldYE*10000)/10000. == Math.round(oldYS*10000)/10000.){
	    		
	    		dist = oldXE-oldXS;
	    		newY = dist/(2*neigung)*Math.sqrt(3)+oldYS;
	    		newX = x1;
	    	
	    	}else{
	    				    	
		    	// auf schr�gen
//		    	if(Math.round(oldYE*10000)/10000. != Math.round(oldYS*10000)/10000.)
	    		
		    	double m = -(oldXE-oldXS)/(oldYE-oldYS);
		    	double h =  (oldXE-oldXS)/(2*neigung)*Math.sqrt(3);
		    	
		    	newX = x1 + h/m;
	    		
		    	newY = m*(newX - x1)+(oldYE-oldYS)/(oldXE-oldXS)*(x1-oldXS)+oldYS;
	    	}
	    	
	    	pointsX.add(count+1, newX);
	    	
	    	pointsY.add(count+1, newY);
	    	
	    	count +=2;
    	}
    	
    }
    
    @Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setTransform(FLIP_X_COORDINATE);
		
		creatFractal(g, 6);
		
	}

	private void creatFractal(Graphics2D g, int much) {

		List<Double> pointsX = new ArrayList<Double>();
		List<Double> pointsY = new ArrayList<Double>();

		pointsX.add(400d);
		pointsY.add(350d);
		
		pointsX.add(700d);	
		pointsY.add(350d);
		
		pointsX.add(700d);	
		pointsY.add(50d);
		
		pointsX.add(400d);	
		pointsY.add(50d);
		
		pointsX.add(400d);
		pointsY.add(350d);
		

		for (int i = 0; i < much; i++) {

			creatFractalTOP(pointsX, pointsY);

		}

		paintFractal(g, pointsX, pointsY);

	}
	

	/**
     * @param args
     */
    public static void main(String[] args) {
      
    	Cesaro coo = new Cesaro();
              
    }
    
    private void paintFractal(Graphics2D g, List<Double> pointsX,
			List<Double> pointsY) {

		for (int i = 0; i < pointsX.size() - 1; i++) {

//			try {
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			g.drawLine(pointsX.get(i).intValue(), pointsY.get(i).intValue(),
					pointsX.get(i + 1).intValue(), pointsY.get(i + 1)
							.intValue());

		}

	}
}
