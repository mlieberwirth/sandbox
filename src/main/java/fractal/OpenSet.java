package main.java.fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;


public class OpenSet extends JFrame{


	final AffineTransform FLIP_X_COORDINATE;

	private Random _rand;

	public OpenSet() {
		super("Cubes");

		_rand = new Random(0);

		setBackground(Color.white);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1300, 1300);
		FLIP_X_COORDINATE = new AffineTransform(1, 0, 0, -1, 0, getHeight() / 2);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setTransform(FLIP_X_COORDINATE);
		
		createTriangleForSnow(g);
		createSmaleTriangleForSnow(g);
		
	}
 	
	 private void createTriangleForSnow(Graphics2D g) {
		
		g.setColor(Color.black);
		
		List<Double> pointsX = new ArrayList<Double>();
		List<Double> pointsY = new ArrayList<Double>();

		pointsX.add(00d);
		pointsY.add(00d);
		
		pointsX.add(600d);	
		pointsY.add(000d);
	
		pointsX.add(300d);	
		pointsY.add(300d*Math.sqrt(3));
		
		pointsX.add(00d);	
		pointsY.add(00d);
		
		paintFractal(g, pointsX, pointsY);
		
		

	}
	
	 private void createSmaleTriangleForSnow(Graphics2D g) {
			
			g.setColor(Color.GREEN);
			
			List<Double> pointsX = new ArrayList<Double>();
			List<Double> pointsY = new ArrayList<Double>();

			pointsX.add(00d);
			pointsY.add(00d);
			
			pointsX.add(600d);	
			pointsY.add(000d);
		
			pointsX.add(500d);	
			pointsY.add(100d*Math.sqrt(3));
			
			pointsX.add(100d);	
			pointsY.add(100d*Math.sqrt(3));
			
			pointsX.add(200d);	
			pointsY.add(000d);
		
			pointsX.add(300d);	
			pointsY.add(100d*Math.sqrt(3));
		
			pointsX.add(400d);	
			pointsY.add(000d);
		
			pointsX.add(500d);	
			pointsY.add(100d*Math.sqrt(3));
		
			pointsX.add(100d);	
			pointsY.add(100d*Math.sqrt(3));
		
			pointsX.add(00d);	
			pointsY.add(00d);
			
			paintFractal(g, pointsX, pointsY);

		}

	 private void createTriangleForCube(Graphics2D g) {
			
			g.setColor(Color.black);
			
			List<Double> pointsX = new ArrayList<Double>();
			List<Double> pointsY = new ArrayList<Double>();

			pointsX.add(00d);
			pointsY.add(00d);
			
			pointsX.add(600d);	
			pointsY.add(000d);
		
			pointsX.add(300d);	
			pointsY.add(300d);
			
			pointsX.add(00d);	
			pointsY.add(00d);
			
			paintFractal(g, pointsX, pointsY);
			
			

		}
	 
	 private void createSmaleTriangleForCube(Graphics2D g) {
			
			g.setColor(Color.GREEN);
			
			List<Double> pointsX = new ArrayList<Double>();
			List<Double> pointsY = new ArrayList<Double>();

			pointsX.add(00d);
			pointsY.add(00d);
			
			pointsX.add(200d);
			pointsY.add(00d);
			
			pointsX.add(100d);
			pointsY.add(100d);
			
			pointsX.add(00d);
			pointsY.add(00d);
			
			pointsX.add(200d);
			pointsY.add(200d);
			
			pointsX.add(200d);
			pointsY.add(00d);
			
			pointsX.add(200d);
			pointsY.add(200d);
			
			pointsX.add(300d);
			pointsY.add(300d);
			
			pointsX.add(400d);
			pointsY.add(200d);
			
			pointsX.add(200d);
			pointsY.add(200d);
		 
			pointsX.add(400d);
			pointsY.add(200d);
			
			pointsX.add(600d);
			pointsY.add(00d);
			
			pointsX.add(400d);
			pointsY.add(00d);
			
			pointsX.add(500d);
			pointsY.add(100d);
			
			pointsX.add(400d);
			pointsY.add(200d);
			
			pointsX.add(400d);
			pointsY.add(00d);
			
			
			
			paintFractal(g, pointsX, pointsY);
			
	 }
	 
	 
	 private void createTriangleForIsland(Graphics2D g) {
			
			g.setColor(Color.black);
			
			List<Double> pointsX = new ArrayList<Double>();
			List<Double> pointsY = new ArrayList<Double>();

			pointsX.add(00d);
			pointsY.add(300d);
			
			pointsX.add(300d);	
			pointsY.add(600d);
		
			pointsX.add(600d);	
			pointsY.add(300d);
			
			pointsX.add(300d);	
			pointsY.add(00d);
			
			pointsX.add(00d);
			pointsY.add(300d);
			
			paintFractal(g, pointsX, pointsY);
			
			

		}
	 
	 private void paintViereck(double x, double y,Graphics2D g){
		 
		List<Double> pointsX = new ArrayList<Double>();
		List<Double> pointsY = new ArrayList<Double>();
		 
		g.setColor(Color.GREEN);
		
	 	pointsX.add(x);
		pointsY.add(y);
		
		pointsX.add(x+75);
		pointsY.add(y-75);
		
		pointsX.add(x+150);
		pointsY.add(y);
		
		pointsX.add(x+75);
		pointsY.add(y+75);
	 
		pointsX.add(x);
		pointsY.add(y);
	 

		paintFractal(g, pointsX, pointsY);
		
		 
	 }
	 
	 private void createSmaleTriangleForIsland(Graphics2D g) {
			
			paintViereck(0, 300, g);
			
			paintViereck(75, 375, g);
			
			paintViereck(150, 450, g);
			
			paintViereck(225, 375, g);
			
			paintViereck(225, 225, g);
			
			paintViereck(300, 150, g);
			
			paintViereck(375, 225, g);
			
			paintViereck(450, 300, g);
			
			
	 }

	private void paintFractal(Graphics2D g, List<Double> pointsX,
			List<Double> pointsY) {

		for (int i = 0; i < pointsX.size() - 1; i++) {

			g.drawLine(pointsX.get(i).intValue(), pointsY.get(i).intValue(),
					pointsX.get(i + 1).intValue(), pointsY.get(i + 1)
							.intValue());

		}

	}
	
	  /**
     * @param args
     */
    public static void main(String[] args) {
      
    	OpenSet coo = new OpenSet();
              
    }

	
}
