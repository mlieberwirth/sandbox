package main.java.fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;


public class KochIsland extends JFrame{

	final AffineTransform FLIP_X_COORDINATE;

	private Random _rand;

	public KochIsland() {
		super("KochIsland");

		_rand = new Random(0);

		setBackground(Color.white);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1300, 1300);
		FLIP_X_COORDINATE = new AffineTransform(1, 0, 0, -1, 0, getHeight() / 2);
		setVisible(true);
	}
	
	public static void creatFractalTOP(List<Double> pointsX, List<Double> pointsY) {

		// 1000 in X
		int size = pointsX.size();
		int count = 0;

		for (int i = 0; i < size - 1; i++) {

			double oldXS = pointsX.get(count);
			double oldXE = pointsX.get(count + 1);

			double oldYS = pointsY.get(count);
			double oldYE = pointsY.get(count + 1);

			// senkrechte
			if(oldXS == oldXE){
									
				double dist = oldYE - oldYS;
												
				double x1 = oldXS - dist / 4;
				double x2 = oldXS + dist / 4;
				
				double y1 = dist / 4 + oldYS;
				double y2 = dist / 2 + oldYS;
				double y3 = 3*dist / 4 + oldYS;				
				
				pointsX.add(count + 1, oldXS);
				pointsX.add(count + 2, x1);
				pointsX.add(count + 3, x1);
				pointsX.add(count + 4, oldXS);
				pointsX.add(count + 5, x2);
				pointsX.add(count + 6, x2);
				pointsX.add(count + 7, oldXS);
				
				pointsY.add(count + 1, y1);
				pointsY.add(count + 2, y1);
				pointsY.add(count + 3, y2);
				pointsY.add(count + 4, y2);
				pointsY.add(count + 5, y2);
				pointsY.add(count + 6, y3);
				pointsY.add(count + 7, y3);

				
				
				
			}else{
				
				double dist = oldXE - oldXS;
	
				double x1 = dist / 4 + oldXS;
				double x2 = dist / 2 + oldXS;
				double x3 = 3*dist / 4 + oldXS;
				
				double y1 = dist / 4 + oldYS;
				double y2 = oldYS - dist / 4;
				
				pointsX.add(count + 1, x1);
				pointsX.add(count + 2, x1);
				pointsX.add(count + 3, x2);
				pointsX.add(count + 4, x2);
				pointsX.add(count + 5, x2);
				pointsX.add(count + 6, x3);
				pointsX.add(count + 7, x3);
				
				
				pointsY.add(count + 1, oldYS);
				pointsY.add(count + 2, y1);
				pointsY.add(count + 3, y1);
				pointsY.add(count + 4, oldYS);
				pointsY.add(count + 5, y2);
				pointsY.add(count + 6, y2);
				pointsY.add(count + 7, oldYE);
			}

			count += 8;
		}

	}

	
	public void creatFractalTOPRand(List<Double> pointsX, List<Double> pointsY) {

		_rand = new Random();
		
		// 1000 in X
		int size = pointsX.size();
		int count = 0;

		for (int i = 0; i < size - 1; i++) {

			double oldXS = pointsX.get(count);
			double oldXE = pointsX.get(count + 1);

			double oldYS = pointsY.get(count);
			double oldYE = pointsY.get(count + 1);

			// senkrechte
			if(oldXS == oldXE){
									
				double dist = oldYE - oldYS;
				
				double distTwo;
				
				if(_rand.nextBoolean()){
					distTwo = dist;
				}else{
					distTwo = -dist;
				}
				
				double x1 = oldXS - distTwo / 4;
				double x2 = oldXS + distTwo / 4;
				
				double y1 = dist / 4 + oldYS;
				double y2 = dist / 2 + oldYS;
				double y3 = 3*dist / 4 + oldYS;				
				
				pointsX.add(count + 1, oldXS);
				pointsX.add(count + 2, x1);
				pointsX.add(count + 3, x1);
				pointsX.add(count + 4, oldXS);
				pointsX.add(count + 5, x2);
				pointsX.add(count + 6, x2);
				pointsX.add(count + 7, oldXS);
				
				pointsY.add(count + 1, y1);
				pointsY.add(count + 2, y1);
				pointsY.add(count + 3, y2);
				pointsY.add(count + 4, y2);
				pointsY.add(count + 5, y2);
				pointsY.add(count + 6, y3);
				pointsY.add(count + 7, y3);
				
			}else{
				
				double dist = oldXE - oldXS;
	
				double x1 = dist / 4 + oldXS;
				double x2 = dist / 2 + oldXS;
				double x3 = 3*dist / 4 + oldXS;
				
				double distTwo;
				
				if(_rand.nextBoolean()){
					distTwo = dist;
				}else{
					distTwo = -dist;
				}
				
				double y1 = oldYS + distTwo / 4;
				double y2 = oldYS - distTwo / 4;
				
				pointsX.add(count + 1, x1);
				pointsX.add(count + 2, x1);
				pointsX.add(count + 3, x2);
				pointsX.add(count + 4, x2);
				pointsX.add(count + 5, x2);
				pointsX.add(count + 6, x3);
				pointsX.add(count + 7, x3);
				
				
				pointsY.add(count + 1, oldYS);
				pointsY.add(count + 2, y1);
				pointsY.add(count + 3, y1);
				pointsY.add(count + 4, oldYS);
				pointsY.add(count + 5, y2);
				pointsY.add(count + 6, y2);
				pointsY.add(count + 7, oldYE);
			}

			count += 8;
		}

	}
	
	private void paintFractal(Graphics2D g, List<Double> pointsX,
			List<Double> pointsY) {

		for (int i = 0; i < pointsX.size() - 1; i++) {

			g.drawLine(pointsX.get(i).intValue(), pointsY.get(i).intValue(),
					pointsX.get(i + 1).intValue(), pointsY.get(i + 1)
							.intValue());
		}

	}

	
	
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setTransform(FLIP_X_COORDINATE);
		
		creatFractal(g, 5);
		
	}

	private void creatFractal(Graphics2D g, int much) {


		//boxes(g);
		
		g.setColor(Color.black);
		
		List<Double> pointsX = new ArrayList<Double>();
		List<Double> pointsY = new ArrayList<Double>();

		pointsX.add(400d);
		pointsY.add(350d);
		
		pointsX.add(700d);	
		pointsY.add(350d);
		
//		pointsX.add(700d);	
//		pointsY.add(50d);
//		
//		pointsX.add(400d);	
//		pointsY.add(50d);
//		
//		pointsX.add(400d);
//		pointsY.add(350d);
		

		for (int i = 0; i < much; i++) {

			creatFractalTOP(pointsX, pointsY);

		}

		paintFractal(g, pointsX, pointsY);
		
		
	}
	
	
	private void boxes(Graphics2D g) {
		
		g.setColor(Color.red);
		
		g.drawRect(400, 312, 75, 75);
		
		g.drawRect(435, 350, 75, 75);
		
		g.drawRect(475, 387, 75, 75);
		
		g.drawRect(510, 350, 75, 75);
		
		g.drawRect(510, 275, 75, 75);
		
		g.drawRect(550, 237, 75, 75);
		
		g.drawRect(585, 275, 75, 75);
		
		g.drawRect(625, 312, 75, 75);
	}

	private void creatFractalRand(Graphics2D g, int much) {

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

			creatFractalTOPRand(pointsX, pointsY);

		}

		paintFractal(g, pointsX, pointsY);

	}
	
	
	
	/**
     * @param args
     */
    public static void main(String[] args) {
      
    	KochIsland coo = new KochIsland();
              
    }
}

