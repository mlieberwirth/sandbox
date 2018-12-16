/*
 * Created on 10.06.2005
 *
 */
package main.java.LinKern.projectLinKernighan;

import java.util.Random;

import main.java.LinKern.comaList.DoubleList;
import main.java.LinKern.comaList.LinkedList;
import main.java.LinKern.comaList.PriorityQueue;
import main.java.LinKern.projectAlgo.Adjazens;
import main.java.LinKern.projectGUI.GUI;
import main.java.LinKern.projectGraph.Vertex;

public class LinKernighan extends Thread
{
	private static DoubleList list;
	
	/**
	 * is called from GUI to give the needed variables and to start LK as a Thread
	 * 
	 * @param g the current gui
	 * @param l the list of points to use for calculation
	 */
	public LinKernighan ( GUI g, DoubleList l )
	{
		gui = g;
		list = l;
		
		STOP = false;
	}
	
	/**
	 * the start method for the Thread
	 */
	public void run() 
	{
		startLK();
	}
	//variable for the anzahl start-touren
	public static double startTouren = 1;
	
	private Random rand = new Random();///3333);
	
	private static final int UNUSED = -1; 
	
	private static double laufzahlaktuell;
	private static double iteration;
	public static double AnzahlDerIterationen = 2;
	public static int fortschritt;
	
	public static boolean STOP = false;
	
	public static GUI gui;
	
	public static Vertex[] Result;
	public static double bestLength = Double.longBitsToDouble(0x7ff0000000000000L);
	
	public static double bound;
	public Vertex[] V;
	public double[][] Dist;
	private int[] succ;
	private int[] pred;
	private boolean[] reversed;
	private boolean[][] blocked;
	private LinkedList[] neighbour;
	private double G_i;
	
	public Vertex[] bestSolution;
	
	//Arrays to save the best solution till now
	private int[] bestSolutionSucc;
	private int[] bestSolutionPred;
	private boolean[] bestSolutionReversed;
	private boolean [][] bestSolutionBlocked;
	private int connectToT1;
	
	//arrays to save the config at entry into main loop
	//saved for backtracking
	private int[] backupSucc;
	private int[] backupPred;
	private boolean[][] backupBlocked;
	private boolean[] backupReversed;
	private double backupG_i;
	private int[] firstbackupSucc;
	private int[] firstbackupPred;
	private boolean[][] firstbackupBlocked;
	private boolean[] firstbackupReversed;
	
	
	private intEdge backupY;
	
	private int l;
	
	private static boolean check = false;
	
	private boolean backtrack = true;
	
	public double length;
	
	/**
	 * class to save Edges. The save the index of the node in V, instead of the Vertex itself
	 * 
	 *  @author Jonas Schweiger
	 */
	private class intEdge implements Comparable
	{
		private int v1;
		private int v2;
		private double length;
		
		public intEdge(int v, int w)
		{
			v1 = v;
			v2 = w;
			
			length = Dist[v][w];
		}
		
		public int compareTo(Object Obj)
		{
			intEdge v = (intEdge) Obj;
			
			if (this.length < v.length)
				return -1;
			else if (this.length == v.length)
				return 0;
			else return 1;
		}
		
		public String toString()
		{
			String str = "";
			
			str += v1 + " -> " + v2 +"(" + length + ")";
			return str;
		}
	}
	
	/**
	 * @param v Array of Vertex containing the initial Graph
	 */
	public LinKernighan(Vertex[] v)
	{
		V = v;
		l=V.length;
		initDist();
		initPredSuccReversed();
		neighbour = new LinkedList[l];
		length = getLength();
	}
	
	public LinKernighan(int[] s, double[][] d, DoubleList L )
	{
		bestLength = Double.longBitsToDouble(0x7ff0000000000000L);;
		l = s.length;
		V = new Vertex[l];
		int k= 0;
		for (L.resetToHead();!L.isAtTail() ;L.increment() )
		{
			V[k] = (Vertex) L.currentData();
			//System.out.println((Vertex) L.currentData());
			k++;
		}
		
//		for (int i = 0; i < l; i++)
//			System.out.println(V[i]);
		
		Dist = d;
		succ = s;
		pred = new int[l];
		reversed = new boolean[l];
		for (int i = 0; i < l;i++)
		{
			pred[succ[i]]=i;
			reversed[i]=false;
		}
		
		blocked = new boolean[l][l];
		for (int i=0; i < l ; i++)
			for (int j=0; j < l ; j++)
				blocked[i][j]=blocked[j][i]=false;
		
		neighbour = new LinkedList[l];
		length = getLength();
	}
	
	/**
	 * Constructor expecting a DoubleList
	 * 
	 * @param list DoubleList with the vertices
	 */
	public LinKernighan(DoubleList list)
	{
		int list_length = list.length();
		V = new Vertex[list_length];
		list.resetToHead();
		
		for (int i = 0; i < list_length; i++)
		{
			V[i] = (Vertex) list.currentData();
			list.increment();
		}
		
		l=list_length;
		initDist();
		initPredSuccReversed();
		
		neighbour = new LinkedList[l];
		length = getLength();
	}
	
	
	/**
	 * Initialises the adjazenzmatrix saving the distances between 2 vertices
	 */
	private void initDist()
	{
		int n = l;
		Dist = new double[n][n];
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j <= i; j++)
			{
				Dist[i][j] = Math.round( Math.sqrt(Math.pow(V[i].getX() - V[j].getX(),2) + Math.pow(V[i].getY() - V[j].getY(),2)));
				Dist[j][i] = Dist[i][j];
			}
	}
	
	/**
	 * Initialises the Pred Succ and Reversed arrays with the order given by the order in V
	 * No randimisation!
	 */
	private void initPredSuccReversed()
	{
		pred = new int[l];
		succ = new int[l];
		reversed = new boolean[l];
		blocked = new boolean[l][l];
		
		for (int i = 0; i < l; i++)
		{
			succ[i]=i+1;
			pred[i]=i-1;
			reversed[i]=false;
			for (int j=0; j < l ; j++)
				blocked[i][j]=blocked[j][i]=false;
		}
		//Kreis hinten und vorne schlie�en
		succ[l-1] = 0;
		pred[0] = l - 1;
	}
	
	/**
	 * Return the actual successor of a node. 
	 * Therefor it checks whether the order in a node is reversed
	 * 
	 * @param i node
	 * @return Successor of the node
	 */
	private int getSucc(int i)
	{
		if (!reversed[i])
			return succ[i];
		else
			return pred[i];
	}
	
	/**
	 * Sets the predator of a node to another node
	 * 
	 * @param node Node, where you want to change the pred
	 * @param i new predator
	 */
	private void setSucc(int node, int i)
	{
		if (reversed[node])
			pred[node] = i;
		else
			succ[node] = i;
	}
	
	/**
	 * Sets the predator of a node to another node
	 * 
	 * @param node Node, where you want to change the pred
	 * @param i new predator
	 */
	private void setSucc_saved(int node, int i)
	{
		if (bestSolutionReversed[node])
			bestSolutionPred[node] = i;
		else
			bestSolutionSucc[node] = i;
	}
	
	/**
	 * Return the actual predator of a node. 
	 * Therefor it checks whether the order in a node is reversed
	 * 
	 * @param i node
	 * @return Predator of the node
	 */
	private int getPred(int i)
	{
		if (!reversed[i])
			return pred[i];
		else 
			return succ[i];
	}
	
	/**
	 * Sets the predator of a node to another node
	 * 
	 * @param node Node, where you want to change the pred
	 * @param i new predator
	 */
	private void setPred(int node, int i)
	{
		if (!reversed[node])
			pred[node] = i;
		else
			succ[node] = i;
	}
	
	/**
	 * Sets the predator of a node to another node
	 * Executed on the saved best solution
	 * 
	 * @param node Node, where you want to change the pred
	 * @param i new predator
	 */
	private void setPred_saved(int node, int i)
	{
		if (!bestSolutionReversed[node])
			bestSolutionPred[node] = i;
		else
			bestSolutionSucc[node] = i;
	}
	
	/**
	 * Swaps the direction from i backwards following the old pred direction till it arrives at the end
	 * 
	 * @param i Start of reversal
	 */
	private void reverseAfter(int i)
	{
		while (i!=UNUSED)
		{
			reversed[i] = !reversed[i];
			//normally we would go the the predator of i
			//but as we swaped direction we go the other way now
			i = getSucc(i);
		}
	}
	
	/**
	 * Reverses the entire cycle
	 */
	private void reverseAll()
	{
		for (int i = 0; i < l; i++)
			reversed[i] = !reversed[i];		
	}
	
	/**
	 * Blocks a given edge for removement and insertion
	 * 
	 * @param v edge to be blocked
	 */
	private void block(intEdge e)
	{
		blocked[e.v1][e.v2] = true;
		blocked[e.v2][e.v1] = true; 
	}
	
	/**
	 * blocks the edge between i and j
	 * 
	 * @param i First Vertex
	 * @param j Second Vertex
	 */
	private void block(int i, int j)
	{
		blocked[i][j] = true;
		blocked[j][i] = true;
			
	}
	
	/**
	 * Unblocks a given edge for removement and insertion
	 * 
	 * @param v edge to be unblocked
	 */
	private void unblock(intEdge e)
	{
		blocked[e.v1][e.v2] = blocked[e.v2][e.v1] = false;  
	}
	
	/**
	 * Add the given link into the graph.
	 * The node start is used to insert it in the right direction
	 * 
	 * @param e Edge to be inserted
	 * @param start index of start node
	 */
	private void insertLink(intEdge e, int start)
	{
		int end;
		
		if (e.v1 == start)
			end = e.v2;
		else
			end = e.v1;
		
		setSucc(start, end);
		setPred(end, start);
	}
	
	/**
	 * breaks the link from the given node in running direction
	 * 
	 * @param start given node
	 * @return end point of broken link
	 */
	private int breakLink(int start)
	{
		int end = getSucc(start);
		
		setSucc(start, UNUSED);
		setPred(end, UNUSED);
		
		return end;
	}
	
	/**
	 * Saves the best know solution
	 */
	private void rememberSolution()
	{
		bestSolutionSucc = (int[]) succ.clone(); 
		bestSolutionPred = (int[]) pred.clone();
		bestSolutionReversed = (boolean[]) reversed.clone();
		bestSolutionBlocked = (boolean[][]) blocked.clone();
	}
	
	/**
	 * Restores the best tour found
	 */
	private void restoreSolution()
	{
		succ = (int[])bestSolutionSucc.clone();
		pred = (int[])bestSolutionPred.clone() ;
		reversed = (boolean[])bestSolutionReversed.clone();
		blocked = (boolean[][])bestSolutionBlocked.clone();
	}
	
	/**
	 * Saves the all details of algo before entry into main loop
	 */
	private void createBackup(intEdge y)
	{
		backupSucc = (int[]) succ.clone(); 
		backupPred = (int[]) pred.clone();
		backupReversed = (boolean[]) reversed.clone();
		backupBlocked = (boolean[][]) blocked.clone();
		backupG_i = G_i;
		backupY = y;
	}
	
	/**
	 * Restores the backup made before entry into main loop
	 * 
	 * @return returns y.
	 */
	private intEdge restoreBackup()
	{
		succ = (int[])backupSucc.clone();
		pred = (int[])backupPred.clone();
		reversed = (boolean[])backupReversed.clone();
		blocked = (boolean[][])backupBlocked.clone();
		G_i = backupG_i;
		return backupY;
	}
	
	/**
	 * Saves the all details of algo before entry into main loop
	 */
	private void createFirstBackup()
	{
		firstbackupSucc = (int[]) succ.clone(); 
		firstbackupPred = (int[]) pred.clone();
		firstbackupReversed = (boolean[]) reversed.clone();
		firstbackupBlocked = (boolean[][]) blocked.clone();
	}
	
	/**
	 * Restores the backup made before entry into main loop
	 * 
	 */
	private void restoreFirstBackup()
	{
		succ = (int[])firstbackupSucc.clone();
		pred = (int[])firstbackupPred.clone();
		reversed = (boolean[])firstbackupReversed.clone();
		blocked = (boolean[][])firstbackupBlocked.clone();
		
	}
	
	/**
	 * Checks if a given Edge is already in the tour
	 * 
	 * @param e Given edge
	 * @return true, if e is in the tour, false else
	 */
	private boolean isInTour(intEdge e)
	{
		if (getPred(e.v1) == e.v2 || getPred(e.v2) == e.v1)
			return true;
		else 
			return false;
	}
	
	/**
	 * Calc the length of the actual tour
	 * 
	 * @return length of tour
	 */
	public double getLength()
	{
		double length = 0;
		int j;
		for (int i = 0; i < l; i++)
		{
			j = getSucc(i);
			if (j != UNUSED)
				length += Dist[i][j];
		}
		
		return length;
	}
	
	/**
	 * Initialises the neighbourhood of a given node
	 *
	 * @param Vertex who needs his neighbours
	 */
	private void makeNeighbour(int i)
	{
		//wo sie schon bestimmt sind braucht man keine neuen 
		if(neighbour[i]!=null)
			return;
		
		neighbour[i] = new LinkedList();
		PriorityQueue pq=new PriorityQueue(l-1);	
		
		//alle weggehenden Kanten in die Queue schmeissen und die kleinsten 5 wieder rausholen;
		for (int j = 0;j < l ; j++)
			if (i!=j)
				pq.push( new intEdge(i,j));
		
		for(int k=0;!pq.isEmpty() && k<=100;k++)
			neighbour[i].insertAfter(pq.pop());
		
		neighbour[i].reset();
	}
	
	/**
	 * resets all the neighbourhoods that have already been initialised
	 */
	private void resetNB()
	{
		for(int k=0;k<l;k++)
			if(neighbour[k]!=null)
				neighbour[k].reset();
	}
	
	/**
	 * gives us the next nearest neighbour of a given node
	 * 
	 * @param t node you want the neighbour of
	 * @return edge 
	 */
	private intEdge getNextNeighbour(int t)
	{
		intEdge y= (intEdge) neighbour[t].currentData();
		neighbour[t].increment();
		return y;
	}
	
	/**
	 * Checks if a node has more neighbours available
	 * 
	 * @param t node to check for
	 * @return true, if node has more neighbour, false else
	 */
	private boolean hasMoreNeighbours(int t)
	{
		return !neighbour[t].isAtEnd();
	}
	
	/**
	 * Reinicialises the blocked array
	 */
	private void cleanBlocked()
	{
		for (int i = 0; i<l;i++)
			for (int j = 0; j<l; j++)
				blocked[i][j] = false;
	}
	
	private void createRandomTour()
	{
		
				
		for (int i = 0; i < l; i++)
		{
			succ[i]=UNUSED;
			reversed[i] = false;
		}
		
		int next = 0;
		int actual = 0;
		for (int i = 0; i < l-1; i++)
		{
			do
				next = rand.nextInt(l);
			while(succ[next] != UNUSED || next == actual);
			succ[actual] = next;
			pred[next] = actual;
			actual = next;
		}
		
		//jetzt den Kreis schliessen
		succ[actual] = 0;
		pred[0] = actual;
				
			
	}
	
	public void getResult()
	{
		Result = new Vertex[l];
		int next = 0;
		
		Result[0] = V[0];
		
		for (int i = 1; i<l;i++)
		{
			next = getSucc(next);
			Result[i] = V[next];
		}
	}
	
	
/*** �nderung 3 Anfang *************************************************************/
	
	/**
	 * extracts the actual tour and calls the paint method of the GUI
	 */
	public void paintResult()
	{
		getResult();
		gui.setValues(Result, bound, this.getLength()); // ohne fortschritt
	}
	

	public static void startLK()
	{
		//long start = System.currentTimeMillis();
		Adjazens.setGUI(gui);
		gui.setFortschritt(0);
		Adjazens A = new Adjazens(list);
		bound = Adjazens.mst(A);
		
		//System.out.println("Schranke: " + (System.currentTimeMillis() - start));
		//start = System.currentTimeMillis();
		A = new Adjazens(list);
		double[][] adja = A._adja;
		int [] succ = Adjazens.neighbour(A);
		
		//System.out.println("Nearest Neighbour: " + (System.currentTimeMillis() - start));

		LinKernighan LK = new LinKernighan(succ, adja, list);

		//start = System.currentTimeMillis();

		laufzahlaktuell = 0;
		
		LK.LK();
		
		for (int i = 0; i < startTouren-1;i++)
		{
			LK.createRandomTour();
			laufzahlaktuell++;
			LK.LK();
			

		}
		
		//LK.paintResult();
		
		//System.out.println("Gesamter LK: " + (System.currentTimeMillis() - start));
		
		//System.out.println("STOP");
		
		if(!STOP)
			gui.setFortschritt( 100);
		gui.stopAction();
		
	}
	/********************* THE ALGO ********************************/ 
	
	public void LK()
	{
		checkTour();
		double best = 0;
		double best_inner = 0;
		length = getLength();
		for (int j = 1; j <= AnzahlDerIterationen && !STOP; j++)
		{
			long start = System.currentTimeMillis();
			best = length;
			for (int i = 0; i < l; i++)
			{
				if (STOP)
				{
					//System.out.println("STOOOOOOPPPPPPPPPP!!!");
					break;
				}				
			
				cleanBlocked();
				do
				{
					best_inner = length;
					connectToT1 = getPred(i);
					LK(i);
					setSucc(connectToT1, i);
					setPred(i, connectToT1);
					if (bestLength > length)
					{
						bestLength = length;
						//System.out.println(length);
						paintResult();
					}
				}				
				while (best_inner > length);
				length = getLength();
				checkTour();
				int Schranke = (int) (40. / startTouren);
				int firstPart = (int) (((double)(j-1)) *(100-Schranke) / (AnzahlDerIterationen * startTouren));
				int secondPart = (int) (laufzahlaktuell * (100-Schranke) / (startTouren));
				int laufen = (int)((double)i/(double)l*((100-Schranke)/AnzahlDerIterationen)/startTouren);
				
				//System.out.println(Schranke + " + " + firstPart + " + " + secondPart + " + " + laufen);
				
				gui.setFortschritt(Schranke + firstPart + secondPart + laufen);
				//best = length;
			}
			//STOP = true;
			//System.out.println(j + "   Zeit: " + (System.currentTimeMillis() - start) + "   Bestlength: " + bestLength);
		}
	}
	

	
	
	/**
	 * executes the Lin-Kernighan algorithm for a given starting point
	 * @param starting point
	 */
	private void LK(int start)
	{
		double Gbest = 0; //Saves the best change till now
		G_i = 0; //Saves the actual gain at iteration i
		int i;
		resetNB();
		
		//Arrays to save the best solution till now
		rememberSolution();
		
		checkTour();
		
		
		
		int t1 = start;
		int _t1 = UNUSED;
		int _t2 = UNUSED;
		int t2 = UNUSED;
		int t2_old = UNUSED; //used later to remember the overwriten t2
				
		//choose t2
		//if Gbest is 0 after the first iteration, choose the again (6d)
		for (int j = 0; j <= 1  && Gbest == 0; j++ )
		{
			i =1;
			
			if ( j == 1 )
				restoreSolution();
			
			//first try the longer edge and I this fails, the shorter
			if ((j == 0 && Dist[t1][getPred(t1)] >= Dist[t1][getSucc(t1)]) || (j == 1 && Dist[t1][getPred(t1)] < Dist[t1][getSucc(t1)]))
			{
				t2 = getPred(t1);		
			
				//break the connection between t1 & t2
				setPred(t1, UNUSED);
				setSucc(t2, UNUSED);
			}
			else
			{
				t2 = getSucc(t1);
		
				//break the connection between t1 & t2
				setPred(t2, UNUSED);
				setSucc(t1, UNUSED);
		
				//reverse the sense of the entire circle
				reverseAll();
			}
			
			checkClosable(t1);
			
			//Block and remember x1
			intEdge x = new intEdge(t1, t2);
			
			if(blocked[x.v1][x.v2 ])
				continue;
			else
				block(x);
			
			
			
			makeNeighbour(t2);
			
			
			createFirstBackup();
			//search y1
			//to privide limited backtracking, select candiates for y1
			//in increasing length
			while (Gbest == 0 && hasMoreNeighbours(t2)) 
			{
				restoreFirstBackup();
				intEdge y = getNextNeighbour(t2);
				
				//Check if the choosen edge is already in the tour
				//If it is so, select another y
				if (isInTour(y))
					continue;
				
				//Problem wenn es gl�scht wurde in erster Iteration greift er es sp�ter wieder auf
				if(blocked[y.v1][y.v2])
					continue;
				
				//check the gain criterion
				//if it fails once it will always fail
				//because all the other choices of y will be even longet
				G_i = x.length - y.length;
				if (G_i < 0)
				{
					G_i = 0;
					break; //if the gain criterion failed, select a new y1
				}
				int t3;
				
				//remember the old pred of _t1 (endpoint of y)
				t3 = y.v2;

				
				int old_pred_t1 = getPred(t3);
				
				//insert and block the new Edge
				//(y1 geht immer in entgegengesetzte Richtung)
				insertLink(y,t2);
				block(y);				
				
				createBackup(y);
				backtrack = true;
				//MAIN LOOP
				//
				while (Gbest == 0 && backtrack == true)
				{
					_t2 = t2;
					_t1 = t3;
					y = restoreBackup();
					i = 1;
					do
					{
						i++;
						//System.out.println(i);
						//System.out.println(this);
					
						//get xi
						//t2i-1 will be called _t1
						//t2i will be called _t2
						//like this we have the same names as for the first x1
					
						t2_old = _t2;
					
						//get and break next x, where t1 is the starting point
						_t2 = breakLink(_t1);
					
					
					
						//If x is already blocked, restore the last solution and
						//leave the loop for backtracking
						if (blocked[_t1][_t2]) 
						{
							//restoreSolution();
							restoreBackup();
							if (i == 2)
								backtrack = false;
							break;
						}
						else block(_t1,_t2);
					
						//now we have to reverse the orientation from t2_old backwards
						reverseAfter(_t1);
					
					
						//set the pred of t1 to the old pred (we llost this inof before)
						setPred(_t1,old_pred_t1);
					
						checkClosable(t1);
					
						//now check whether closing the up would lead us to a better tour than
						//the best tour seen before
						double g =  Dist[_t2][_t1] - Dist[_t2][t1];
						if (G_i + g > Gbest)
						{
							Gbest = G_i + g;
							rememberSolution();
							connectToT1 = _t2;
						}
					
						makeNeighbour(_t2);
						//get the next y, if possible
						if(hasMoreNeighbours(_t2))
							do
							{
								y =getNextNeighbour(_t2);
								if (!blocked[y.v1][y.v2])
									if(!isInTour(y))
										break;
							}
							while(hasMoreNeighbours( _t2));
						else
							break;
					
						int _t1_old = _t1;
					
							_t1 = y.v2;

						old_pred_t1 = getPred(_t1);
					
						//insert and block the new Edge
						insertLink(y,_t2);
						block(y);
					
						G_i += Dist[_t2][_t1_old] - y.length;
					
						//Tour Komplett, h�re auf
						if(y.v1 == t1 || y.v2 == t1)
							break;
										
					} while (G_i >= Gbest);
					if (Gbest == 0)
						restoreBackup();
				}
				//we left the loop, so we have to restore the best solution found!
				if (Gbest > 0)
					restoreSolution();				
			}
			restoreSolution();
		}
	restoreSolution();
	length -= Gbest;
	}
	

	
	
	/*********** SOME CHECKMETHODS ********************************/
	
	/**
	 * checks if we have a tour in both directions
	 * 
	 * @return returns true, if we have a tour touching all nodes, false else
	 */
	private boolean checkTour()
	{
		if (check == false) //no checking wanted
			return true;
		
		int posPred = 0; //starting position
		int posSucc = 0;
		boolean[] touchedPred = new boolean[l];
		boolean[] touchedSucc = new boolean[l];
	
		for(int i= 0; i < l; i++)
			touchedPred[i] = touchedSucc[i] = false;
		
		
		//check whether after l steps you are at the begin again
		//and check whether we have touched all nodes
		//do the check in both directions at the same time
		for (int i = 0; i < l; i++)
		{
			posSucc = getSucc(posSucc);
			posPred = getPred(posPred);
			
			if (posSucc == UNUSED) //there is a break!
			{
				System.out.println("A Node has no succ: " + posSucc);
				return false;
			}
			else if (posPred == UNUSED)
			{
				System.out.println("A Node has no pred: " + posPred);
				return false;
			}
			
			//mark node true
			touchedPred[posPred] = true;
			touchedSucc[posSucc] = true;
			
		}
		if (posSucc != 0 || posPred != 0) // we are not a the begin again
		{
			System.out.println("This is not a cycle!");
			if (posSucc != 0 && posPred != 0)
				System.out.println("In both direcctions");
			if (posPred != 0)
				System.out.println("In pred direcction");
			if (posSucc != 0)
				System.out.println("In succ direcction");
			
			return false;
		}
		
		//check if all nodes were touched
		for (int i = 0; i < l; i++)
			if (!touchedSucc[i] || !touchedPred[i])
			{
				System.out.println("Node" + i + "was not touched!!!");
				return false;
			}
		
		return true;		
	}
	
	/**
	 * Checks if we have a path trough all the nodes
	 * If we do so, the path can be closed to a complete cycle
	 * 
	 * @param start startnode of path
	 * @return true, if it can be closed, false else
	 */
	private boolean checkClosable(int start)
	{
		if (check == false) //no checking wanted
			return true;
		
		boolean[] touched = new boolean[l];
		
		//initialise touched with false as no node has been touched before
		for (int i = 0; i < l; i++)
			touched[i] = false;
		
		touched[start] = true;
		int pos = getSucc(start);
		
		//walk till you reach a node where you have been are at the end
		while (pos != UNUSED && !touched[pos])
		{
			touched[pos] = true;
			pos = getSucc(pos);
		}
		
		if (pos == UNUSED) //we found an end
		{
			// check whether all nodes were touched, because it is so, 
			//then we can link pos to start and have a tour 
			for (int i = 1; i < l;i++)
				if (!touched[i])
				{
					System.out.println("Node " + i + "was not touched");
					return false;
				}
		}
		else // we have a cycle
		{
			System.out.println("We have a cycle starting in " + pos);
			return false;
		}
		
		return true;	
	}
	
	
	
	public String toString()
	{
		String str="";
		int k=0;
		for(int i=0; i<l;i++)
			if(reversed[k])
				str+=k+"->"+(k=pred[k])+" ";
			else
				str+=k+"->"+(k=succ[k])+" ";
		return str;
	}
	
}
