package main.java.fractal;

 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import javax.swing.JFrame;

public class KochFlake extends JFrame {
 
    final AffineTransform FLIP_X_COORDINATE;
 
    private Random _rand;
    
    private double _C;
    
    public KochFlake() {
        super("KochFlake");
        
        _rand = new Random();
        _C = getNextRandom();
        
        setBackground(Color.white);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 1300);
        FLIP_X_COORDINATE = new AffineTransform(1, 0, 0, -1, 0, getHeight()/2);
        setVisible(true);
        
        paintThis(getGraphics());
    }

    public void creatFractalTOP (List<Double> pointsX,List<Double> pointsY){
    	
    	// 1000 in X
    	int size = pointsX.size();
    	int count = 0;
    	
    	for (int i = 0; i < size-1; i++) {
			
	    	double oldXS = pointsX.get(count);
	    	double oldXE = pointsX.get(count+1);
	    		
	    	double oldYS = pointsY.get(count);
	    	double oldYE = pointsY.get(count+1);
	    	
	    	double dist = oldXE-oldXS;
	    	    	
	    	double x1 = dist/3+oldXS;
	    	double x2 = dist/2+oldXS;
	    	double x3 = 2*dist/3+oldXS;
	    	
	    	double y1 = (oldYE-oldYS)/(oldXE-oldXS)*(x1-oldXS)+oldYS;
	    	
	    	double y2 = (oldYE-oldYS)/(oldXE-oldXS)*(x2-oldXS)+oldYS;;
	    	
	    	double newX2 = x2;
	    	double newY2 = (oldXE-oldXS)/6*Math.sqrt(3)+oldYE;
	    	
	    	if(Math.round(oldYE*10000)/10000. != Math.round(oldYS*10000)/10000.){
	    		
		    	double m = -(oldXE-oldXS)/(oldYE-oldYS);
		    	double h =  (oldXE-oldXS)/6*Math.sqrt(3);
		    	
		    	newX2 = x2 + h/m;
	    		    	
		    	newY2 = m*(newX2 - x2)+y2;
	    	}
	    	
	    	double y3 = (oldYE-oldYS)/(oldXE-oldXS)*(x3-oldXS)+oldYS;
	    	
	    	pointsX.add(count+1, x1);
	    	pointsX.add(count+2, newX2);
	    	pointsX.add(count+3, x3);
	    	
	    	pointsY.add(count+1, y1);
	    	pointsY.add(count+2, newY2);
	    	pointsY.add(count+3, y3);
	    	
	    	count +=4;
    	}
    	
    }
    

    public void creatFractalTOPRandom (List<Double> pointsX,List<Double> pointsY){
    	
    	// 1000 in X
    	int size = pointsX.size();
    	int count = 0;
    	
    	for (int i = 0; i < size-1; i++) {
			
	    	double oldXS = pointsX.get(count);
	    	double oldXE = pointsX.get(count+1);
	    		
	    	double oldYS = pointsY.get(count);
	    	double oldYE = pointsY.get(count+1);
	    	
	    	double dist = oldXE-oldXS;
	    	
	    	// mitte
	    	double x2 = dist/2+oldXS;
	    	
	    	double x1 = x2-.5*_C*dist;
	    	double x3 = x2+.5*_C*dist;
	    	
	    	double y1 = (oldYE-oldYS)/(oldXE-oldXS)*(x1-oldXS)+oldYS;
	    	
	    	double y2 = (oldYE-oldYS)/(oldXE-oldXS)*(x2-oldXS)+oldYS;;
	    	
	    	double newX2 = x2;
	    	double h =  _C*(oldXE-oldXS)/2*Math.sqrt(3);
	    	double newY2 = h+oldYE;
	    	
	    	if(Math.round(oldYE*10000)/10000. != Math.round(oldYS*10000)/10000.){
	    		
		    	double m = -(oldXE-oldXS)/(oldYE-oldYS);
		    			    	
		    	newX2 = x2 + h/m;
	    		    	
		    	newY2 = m*(newX2 - x2)+y2;
	    	}
	    	
	    	double y3 = (oldYE-oldYS)/(oldXE-oldXS)*(x3-oldXS)+oldYS;
	    	
	    	pointsX.add(count+1, x1);
	    	pointsX.add(count+2, newX2);
	    	pointsX.add(count+3, x3);
	    	
	    	pointsY.add(count+1, y1);
	    	pointsY.add(count+2, newY2);
	    	pointsY.add(count+3, y3);
	    	
	    	count +=4;
    	}
    	
    }
        
    public void creatFractalDOWNRandom (List<Double> pointsX,List<Double> pointsY){
    	
    	// 1000 in X
    	int size = pointsX.size();
    	int count = 0;
    	
    	for (int i = 0; i < size-1; i++) {
				    	
	    	double oldXS = pointsX.get(count);
	    	double oldXE = pointsX.get(count+1);
	    		
	    	double oldYS = pointsY.get(count);
	    	double oldYE = pointsY.get(count+1);
	    	
	    	double dist = oldXE-oldXS;
	    	    
	    	// mitte
	    	double x2 = dist/2+oldXS;
	    	
	    	double x1 = x2-.5*_C*dist;
	    	double x3 = x2+.5*_C*dist;
	    	
	    	
	    	double y1 = (oldYE-oldYS)/(oldXE-oldXS)*(x1-oldXS)+oldYS;
	    	
	    	double y2 = (oldYE-oldYS)/(oldXE-oldXS)*(x2-oldXS)+oldYS;;
	    	
	    	double newX2 = x2;

	    	double h =  -_C*(oldXE-oldXS)/2*Math.sqrt(3);
	    	double newY2 = h+oldYE;
	    	
	    	if(Math.round(oldYE*10000)/10000. != Math.round(oldYS*10000)/10000.){
	    		
		    	double m = -(oldXE-oldXS)/(oldYE-oldYS);
		    			    	
		    	newX2 = x2 + h/m;
	    		    	
		    	newY2 = m*(newX2 - x2)+y2;
	    	}
	    	
	    	double y3 = (oldYE-oldYS)/(oldXE-oldXS)*(x3-oldXS)+oldYS;
	    	
	    	pointsX.add(count+1, x1);
	    	pointsX.add(count+2, newX2);
	    	pointsX.add(count+3, x3);
	    	
	    	pointsY.add(count+1, y1);
	    	pointsY.add(count+2, newY2);
	    	pointsY.add(count+3, y3);
	    	
	    	count +=4;
    	}
    	
    }
    
    
    /**
     * Gibt uns eine ZV zw. 0 und 1/3 zurueck
     */
    private double getNextRandom() {
		
    	double result = _rand.nextDouble()/3.; 
    	
    	while(result <= 0d || result >= 1/3d){
    		
    		result = _rand.nextDouble()/3.;
    		
    	}
    	
    	return result;
    	
	}

	public void creatFractalDOWN (List<Double> pointsX,List<Double> pointsY){
    	
    	// 1000 in X
    	int size = pointsX.size();
    	int count = 0;
    	
    	for (int i = 0; i < size-1; i++) {
				    	
	    	double oldXS = pointsX.get(count);
	    	double oldXE = pointsX.get(count+1);
	    		
	    	double oldYS = pointsY.get(count);
	    	double oldYE = pointsY.get(count+1);
	    	
	    	double dist = oldXE-oldXS;
	    	    	
	    	double x1 = dist/3+oldXS;
	    	double x2 = dist/2+oldXS;
	    	double x3 = 2*dist/3+oldXS;
	    	
	    	double y1 = (oldYE-oldYS)/(oldXE-oldXS)*(x1-oldXS)+oldYS;
	    	
	    	double y2 = (oldYE-oldYS)/(oldXE-oldXS)*(x2-oldXS)+oldYS;;
	    	
	    	double newX2 = x2;

	    	double h =  -(oldXE-oldXS)/6*Math.sqrt(3);
	    	double newY2 = h+oldYE;
	    	
	    	if(Math.round(oldYE*10000)/10000. != Math.round(oldYS*10000)/10000.){
	    		
		    	double m = -(oldXE-oldXS)/(oldYE-oldYS);
		    			    	
		    	newX2 = x2 + h/m;
	    		    	
		    	newY2 = m*(newX2 - x2)+y2;
	    	}
	    	
	    	double y3 = (oldYE-oldYS)/(oldXE-oldXS)*(x3-oldXS)+oldYS;
	    	
	    	pointsX.add(count+1, x1);
	    	pointsX.add(count+2, newX2);
	    	pointsX.add(count+3, x3);
	    	
	    	pointsY.add(count+1, y1);
	    	pointsY.add(count+2, newY2);
	    	pointsY.add(count+3, y3);
	    	
	    	count +=4;
    	}
    	
    }
    
    
    public void creatRandomFractal (List<Double> pointsX,List<Double> pointsY){
    	
    	// 1000 in X
    	int size = pointsX.size();
    	int count = 0;
    	
    	for (int i = 0; i < size-1; i++) {
				    	
	    	double oldXS = pointsX.get(count);
	    	double oldXE = pointsX.get(count+1);
	    		
	    	double oldYS = pointsY.get(count);
	    	double oldYE = pointsY.get(count+1);
	    	
	    	double dist = oldXE-oldXS;
	    	    	
	    	double x1 = dist/3+oldXS;
	    	double x2 = dist/2+oldXS;
	    	double x3 = 2*dist/3+oldXS;
	    	
	    	double y1 = (oldYE-oldYS)/(oldXE-oldXS)*(x1-oldXS)+oldYS;
	    	
	    	double y2 = (oldYE-oldYS)/(oldXE-oldXS)*(x2-oldXS)+oldYS;;
	    	
	    	double newX2 = x2;
	    	double h = (oldXE-oldXS)/6*Math.sqrt(3);
	    		    	
	    	if(!_rand.nextBoolean()){
	    		h=-h;
	    	}
	    	
	    	double newY2 = h + oldYE;
	    	
	    	if(Math.round(oldYE*10000)/10000. != Math.round(oldYS*10000)/10000.){
	    		
		    	double m = -(oldXE-oldXS)/(oldYE-oldYS);
		    			    	
		    	newX2 = x2 + h/m;
	    		    	
		    	newY2 = m*(newX2 - x2)+y2;
	    	}
	    	
	    	double y3 = (oldYE-oldYS)/(oldXE-oldXS)*(x3-oldXS)+oldYS;
	    	
	    	pointsX.add(count+1, x1);
	    	pointsX.add(count+2, newX2);
	    	pointsX.add(count+3, x3);
	    	
	    	pointsY.add(count+1, y1);
	    	pointsY.add(count+2, newY2);
	    	pointsY.add(count+3, y3);
	    	
	    	count +=4;
    	}
    	
    }
    
    
    
    
    
    private void paintFractal (Graphics2D g, List<Double> pointsX, List<Double> pointsY){
    	    	
        for(int i=0;i< pointsX.size()-1;i++){
        	        	        	
        	try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	
    		g.drawLine(pointsX.get(i).intValue(),pointsY.get(i).intValue(),pointsX.get(i+1).intValue(),pointsY.get(i+1).intValue());
    		
    	}
    	    	
    }
        
    public void paintThis(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setTransform(FLIP_X_COORDINATE);
       
        //creatSnowFlakeBig(g,6);
        
        creatSnowFlakeBigRandom(g,6);
        
        //creatFractal(g,6);    
        
        //creatRandomSnowFlake(g,5);
    }
     
    private void creatFractal(Graphics2D g,int much) {
    	
    	List<Double> pointsX =new ArrayList<Double>();
        List<Double> pointsY =new ArrayList<Double>();
        
        pointsX.add(100d);
        pointsX.add(600d);
        
        pointsY.add(0d);
        pointsY.add(0d);
        
        for (int i = 0; i < much; i++) {
		
        	creatFractalTOPRandom(pointsX, pointsY);
	          
        }
        
        paintFractal(g, pointsX, pointsY);
        
        //boxes(g);
    	
    }
    
    private void boxes(Graphics2D g) {
		
		g.setColor(Color.red);
		
		g.drawRect(100, -050, 167, 167);
				
		Double h = (600-100)/6*Math.sqrt(3);
	
		int[] x1y1 = calcPointStart(267, 0, 350, 167, 40);
		int[] x2y2 = calcPointEnd(267, 0, 350, h, 40);
		
		int[] x3y3 = calcPointStart(267, 0, 350, 167, -127);
		int[] x4y4 = calcPointEnd(267, 0, 350, h, -127);
		
		g.drawPolygon(new int[]{x1y1[0],x2y2[0],x4y4[0],x3y3[0]}, new int[]{x1y1[1],x2y2[1],x4y4[1],x3y3[1]}, 4);
		

		x1y1 = calcPointStart(350, h, 434, 0, 127);
		x2y2 = calcPointEnd(350, h, 434, 0, 127);
		
		x3y3 = calcPointStart(350, h, 434, 0, -40);
		x4y4 = calcPointEnd(350, h, 434, 0, -40);
		
		g.drawPolygon(new int[]{x1y1[0],x2y2[0],x4y4[0],x3y3[0]}, new int[]{x1y1[1],x2y2[1],x4y4[1],x3y3[1]}, 4);
		
		g.drawRect(433, -050, 167, 167);
	}
    
    private int[] calcPointEnd(double x1, double y1,double x2, double y2, double abs) {
		
    	double m = (x1-x2)/(y2-y1);
		
		int newX1 = new Double(abs/Math.sqrt(1+m*m)+x2).intValue();
		int newY1 = new Double(m*(newX1-x2)+y2).intValue();
    	
		return new int[]{newX1,newY1};
	}

	private int[] calcPointStart(double x1, double y1,double x2, double y2, double abs){
    	
    	double m = (x1-x2)/(y2-y1);
		
		int newX1 = new Double(abs/Math.sqrt(1+m*m)+x1).intValue();
		int newY1 = new Double(m*(newX1-x1)+y1).intValue();
		
    	return new int[]{newX1,newY1};
    }
    
    private void creatRandomSnowFlake(Graphics2D g,int much) {
    	
    	List<Double> pointsX =new ArrayList<Double>();
        List<Double> pointsY =new ArrayList<Double>();
        
        pointsX.add(100d);
        pointsX.add(600d);
        
        pointsY.add(0d);
        pointsY.add(0d);
        
        for (int i = 0; i < much; i++) {
			
        	creatRandomFractal(pointsX, pointsY);
        }
        
        cutFirstLast(pointsX,pointsY);
        
        g.setColor(Color.BLUE);
        paintFractal(g, pointsX, pointsY);
        
        pointsX =new ArrayList<Double>();
        pointsY =new ArrayList<Double>();
        
        pointsX.add(100d);
        pointsX.add(600d);
        
        pointsY.add(96d);
        pointsY.add(96d);
        
        for (int i = 0; i < much; i++) {
			
        	creatRandomFractal(pointsX, pointsY);
        }
        
        cutFirstLast(pointsX,pointsY);
        
        g.setColor(Color.GREEN);
        paintFractal(g, pointsX, pointsY);
  	
	}

	private void creatSnowFlake(Graphics2D g,int much){
    	
    	  List<Double> pointsX =new ArrayList<Double>();
          List<Double> pointsY =new ArrayList<Double>();
          
          pointsX.add(00d);
          pointsX.add(500d);
          
          pointsY.add(0d);
          pointsY.add(0d);
          
          for (int i = 0; i < much; i++) {
		
	          creatFractalTOP(pointsX, pointsY);
	          
          }
          
          cutFirstLast(pointsX,pointsY);
          
          g.setColor(Color.GREEN);
          paintFractal(g, pointsX, pointsY);
          
          pointsX =new ArrayList<Double>();
          pointsY =new ArrayList<Double>();
          
          pointsX.add(00d);
          pointsX.add(500d);
          
          pointsY.add(96d);
          pointsY.add(96d);
          
          for (int i = 0; i < much; i++) {
		
	          creatFractalDOWN(pointsX, pointsY);
	          
          }
          
          cutFirstLast(pointsX,pointsY);
          
          g.setColor(Color.GREEN);
          paintFractal(g, pointsX, pointsY);
    	
    }
    
	private void creatSnowFlakeBig(Graphics2D g,int much){
    	
  	  List<Double> pointsX =new ArrayList<Double>();
        List<Double> pointsY =new ArrayList<Double>();
        
        pointsX.add(00d);
        pointsX.add(1500d);
        
        pointsY.add(0d);
        pointsY.add(0d);
        
        for (int i = 0; i < much; i++) {
		
	          creatFractalTOP(pointsX, pointsY);
	          
        }
        
        cutFirstLast(pointsX,pointsY);
        
        g.setColor(Color.black);
        paintFractal(g, pointsX, pointsY);
        
        pointsX =new ArrayList<Double>();
        pointsY =new ArrayList<Double>();
        
        pointsX.add(00d);
        pointsX.add(1500d);
        
        pointsY.add(96*3d);
        pointsY.add(96*3d);
        
        for (int i = 0; i < much; i++) {
		
	          creatFractalDOWN(pointsX, pointsY);
	          
        }
        
        cutFirstLast(pointsX,pointsY);
        
        g.setColor(Color.black);
        paintFractal(g, pointsX, pointsY);
  	
  }
	
	
	
    
	private void creatSnowFlakeBigRandom(Graphics2D g,int much){
    	
	  	  List<Double> pointsX =new ArrayList<Double>();
	        List<Double> pointsY =new ArrayList<Double>();
	        
	        pointsX.add(00d);
	        pointsX.add(1000d);
	        
	        pointsY.add(0d);
	        pointsY.add(0d);
	        
	        for (int i = 0; i < much; i++) {
			
		          creatRandomFractal(pointsX, pointsY);
		          
	        }
	        
	       // cutFirstLast(pointsX,pointsY);
	        
	        g.setColor(Color.black);
	        paintFractal(g, pointsX, pointsY);
	        
	        
	  }
	    
	
	
    private void cutFirstLast(List<Double> pointsX, List<Double> pointsY) {
		
        	double start = pointsX.get(0);
        	double end = pointsX.get(pointsX.size()-1);
        	
        	double dist = (end-start);
        	
        	double oneDrittel = dist/3.+start;
        	double twoDrittel = 2*dist/3.+start;
        	
        	for (int i = 0; i < pointsX.size(); i++) {
				
        		if(pointsX.get(i) > oneDrittel && pointsX.get(i) < twoDrittel){
        			
        		}else{
        			pointsX.remove(i);
        			pointsY.remove(i);
        			i--;
        		}
        		
			}
        			
	}

    /**
     * @param args
     */
    public static void main(String[] args) {
      
    	KochFlake coo = new KochFlake();
              
    }
}