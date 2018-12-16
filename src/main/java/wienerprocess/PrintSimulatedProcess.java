package main.java.wienerprocess;

 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import javax.swing.JFrame;

public class PrintSimulatedProcess extends JFrame {
 
    final AffineTransform FLIP_X_COORDINATE;
 
    public PrintSimulatedProcess() {
        super("CoordinateSystemExample");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 1000);
        FLIP_X_COORDINATE = new AffineTransform(1, 0, 0, -1, 0, getHeight()/2);
        setVisible(true);
    }
 
    public double[] creatWiener (int n,int seed){
    	
    	Random rand = new Random(seed);
    	
    	double X = 0;
    	double Sn = 0;
    	double[] Yn = new double[n];
    	
    	
    	for(int i=0;i<n;i++){
    		X = rand.nextGaussian();
    		Sn += X;
    		Yn[i] = Sn/Math.sqrt(n);
    	}
    	
    	
    	
    	return Yn;
    	
    }
    
    private void paintWiener (Graphics2D g, int n,double[] wiener){
    	    	
        for(int i=0;i< wiener.length-1;i++){
        	
        	int wieny = new Double(wiener[i]*100).intValue();
        	int wieny2 = new Double(wiener[i+1]*100).intValue();
        	        	
    		g.fillOval(i,wieny , 2, 2);
    		g.drawLine(i,wieny, (i+1), wieny2);
    		
    	}
    	    	
    }
    
    private void paintEuler (Graphics2D g, int n,int m,double[] wiener){
    	
    	int zahl = n / m;
    	
        for(int i=0;i< wiener.length-1;i++){
        	
        	int wieny = new Double(wiener[i]*100).intValue();
        	int wieny2 = new Double(wiener[i+1]*100).intValue();
        	        	
    		g.fillOval(i*zahl,wieny , 2, 2);
    		g.drawLine(i*zahl,wieny, (i+1)*zahl, wieny2);
    		
    	}
    	    	
    }
    
    private void testing(Graphics2D g){
    	Color[] col = {Color.RED,Color.GRAY,Color.BLUE,Color.GREEN,Color.ORANGE,Color.PINK};
        
        for (int i=0;i<6;i++){
        	
        	g.setColor(col[i]);
        	        	        	
            paintWiener(g, 1000,creatWiener(1000, i));
        }
      
        g.setColor(Color.BLACK);
        
        // X Line
        g.drawLine(0, 0, 1000, 0);
        for(int i=0;i<100;i++){
        	g.drawLine(i*10, -1, i*10, 1);
        }
        
        // Y Line
        g.drawLine(0,-500, 0, 500);
        for(int i=-50;i<50;i++){
        	g.drawLine(0,i*10, 1, i*10);
        }
    }
    
    private double[] fkt (double[] W){
    	
    	double X0 = 1;
    	double a = 1.5;
    	double b = 1;
    	
    	int n = W.length;
    	
    	double[] X = new double[n];
    	
    	for(int t=0; t < n;t++){
    		
    		X[t] = X0 * Math.exp( (a-.5*Math.pow(b, 2))*t/n + b*W[t] );
    	}
    	
    	return X;
    	
    }
    
    private double[] euler (double[] W, int m){
    	
    	int n= W.length;
    	
    	double[] Y = new double[m+1];
    	
    	Y[0] = 1;
    	
    	double a = 1.5;
    	double b = 1;
    	
    	int zahl = new Double (Math.floor((n)/m)).intValue();
    	
    	int[] tau = new int[m+1];
    	    	
    	tau[0]=0;
    	    
    	if(zahl != 1){    		
    		for(int i=1;i<m+1;i++){
    			tau[i] = (i)*zahl-1;
    		}
    	}
    	else{
    		for(int i=0;i<m+1;i++){
    			tau[i] = i;
    		}
    	}
    	    	
    	double delta=0;
    	
    	for(int i=0 ; i < m; i ++ ){
    		
    		delta = (tau[i+1]-tau[i]+0.0)/n;
    		
    		Y[i+1]=Y[i]+a*Y[i]*delta+b*Y[i]*(W[tau[i+1]]-W[tau[i]]);
    	}
    	
    	return Y;
    }
    
    @Override
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setTransform(FLIP_X_COORDINATE);
       
        /*int n = 1000;
        int m = 100;
        
        for( int i=0;i<10;i++){
        	
        	//g.setColor(Color.GREEN);
	        
	        double[] w = creatWiener(n, i*2);
	        
	        double[] fkt = fkt(w);
	
	        //paintWiener(g, n, fkt);
	        
	        double[] Y = euler(w,m);
	        
	        //g.setColor(Color.BLUE);
	        
	        //paintEuler(g,n, m, Y);
        }
        */
        g.setColor(Color.RED);
        meanFKT(g, 10);
        g.setColor(Color.BLUE);
        meanFKT(g, 100);
        g.setColor(Color.GREEN);
        meanFKT(g, 1000);
        g.setColor(Color.ORANGE);
        meanFKT(g, 5000);
        
        
        g.setColor(Color.BLACK);
        
        // X Line
        g.drawLine(0, 0, 1000, 0);
        for(int i=0;i<100;i++){
        	g.drawLine(i*10, -1, i*10, 1);
        }
        
        // Y Line
        g.drawLine(0,-500, 0, 500);
        for(int i=-50;i<50;i++){
        	g.drawLine(0,i*10, 1, i*10);
        }
       
    }
             
    public double error(int N){
    	
    	double[] W;
    	double[] X;
    	double[] Y;
    	double E=0;
    	
    	for(int i=0;i<N;i++){
    		W = creatWiener(100000, i);
    		X = fkt(W);
    		Y = euler(W,10000);
    		E += Math.abs(X[X.length-1]-Y[Y.length-1])/N;
    	}
    	
    	return E;
    	
    }
    
    public void meanFKT (Graphics2D g,int N){
    	
    	double[] W= new double[1000];
    	double[] X= new double[1000];
    	double[] meanX = new double[1000];
    	double[] Y= new double[100];
    	double[] meanY= new double[100];
    	
    	for(int i=0;i < N;i++){
    		W = creatWiener(1000, i);
    		X = fkt(W);
    		
    		for(int j=0;j<X.length;j++){
    			meanX[j] += X[j]/N;
    		}
    		
    		//Y = euler(W,100);
    		
    		//for(int j=0;j<meanY.length;j++){
    			//meanY[j] += Y[j]/N;
    		//}
    	}
    	
    	paintWiener(g, 1000, meanX);
    	//paintEuler(g, 1000, 100, meanY);
    	
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        PrintSimulatedProcess coo = new PrintSimulatedProcess();
        
        //System.out.println(coo.error(25));
    }
}