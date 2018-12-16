package main.java.fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;


public class CubeIsland extends JFrame{

	
	final AffineTransform FLIP_X_COORDINATE;

	private Random _rand;

	public CubeIsland() {
		super("CubeIsland");

		_rand = new Random(0);

		setBackground(Color.white);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1300, 1300);
		FLIP_X_COORDINATE = new AffineTransform(1, 0, 0, -1, 0, getHeight() / 2);
		setVisible(true);
	}
	
	private int cubes(List<Double> pointsX, List<Double> pointsY, int count){
		
		
		double oldXS = pointsX.get(count);
		double oldXE = pointsX.get(count + 1);

		double oldYS = pointsY.get(count);
		double oldYE = pointsY.get(count + 1);

		// senkrechte
		if(oldXS == oldXE){
								
			double dist = oldYE - oldYS;
			
			double x1 = oldXS - dist / 3;
			
			double y1 = dist / 3 + oldYS;
			double y2 = 2*dist / 3 + oldYS;
							
			pointsX.add(count + 1, oldXS);
			pointsX.add(count + 2, x1);
			pointsX.add(count + 3, x1);
			pointsX.add(count + 4, oldXS);
			
			pointsY.add(count + 1, y1);
			pointsY.add(count + 2, y1);
			pointsY.add(count + 3, y2);
			pointsY.add(count + 4, y2);
			
			
		}else{
			
			double dist = oldXE - oldXS;

			double x1 = dist / 3 + oldXS;
			double x2 = 2 * dist / 3 + oldXS;

			double y1 = dist / 3 + oldYS;
				
			pointsX.add(count + 1, x1);
			pointsX.add(count + 2, x1);
			pointsX.add(count + 3, x2);
			pointsX.add(count + 4, x2);
			
			pointsY.add(count + 1, oldYS);
			pointsY.add(count + 2, y1);
			pointsY.add(count + 3, y1);
			pointsY.add(count + 4, oldYE);
		}

		count += 5;
		
		return count;
	}
	
	
	private int island(List<Double> pointsX, List<Double> pointsY, int count){
		
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
		
		return count;
	}
	
	public void creatFractalTOP(List<Double> pointsX, List<Double> pointsY) {

		// 1000 in X
		int size = pointsX.size();
		int count = 0;

		_rand = new Random();
		
		for (int i = 0; i < size - 1; i++) {

			if(_rand.nextBoolean()){
				count = island(pointsX, pointsY, count);
			}else{
				count = cubes(pointsX, pointsY, count);
			}
			
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
		
		creatFractal(g, 2);
		
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

	//		creatFractalTOP(pointsX, pointsY);
						
			Cubes.creatFractalTOP(pointsX, pointsY);
			KochIsland.creatFractalTOP(pointsX, pointsY);
		}

		paintFractal(g, pointsX, pointsY);

	}
	
	/**
     * @param args
     */
    public static void main(String[] args) {
      
    	CubeIsland coo = new CubeIsland();
              
    }
	
	
}
