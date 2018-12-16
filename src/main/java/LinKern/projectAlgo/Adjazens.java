package main.java.LinKern.projectAlgo;

import main.java.LinKern.comaList.DoubleList;
import main.java.LinKern.projectGUI.GUI;
import main.java.LinKern.projectGraph.Vertex;
import main.java.LinKern.projectLinKernighan.LinKernighan;

public class Adjazens {

	/*---private object data---*/

	/**
	 * matrix _adla[][]
	 */
	private static final double inf = Double.longBitsToDouble(0x7ff0000000000000L);// double
																					// infinity
	private static boolean _points[];

	public double _adja[][];
	public DoubleList L;

	public static double iterations;

	public static GUI gui;
	/*---public constructor---*/

	/**
	 * init constructor, initializes matrix from LinkedList liste
	 * 
	 * @param liste
	 * @return new adiazens matrix
	 */

	public Adjazens(DoubleList liste) {
		DoubleList liste2 = new DoubleList(liste);
		int listeLength = liste.length();
		_adja = new double[listeLength][listeLength];
		int counter = 0;
		for (liste.resetToHead(); !liste.isAtTail(); liste.increment()) {

			Vertex vert = (Vertex) liste.currentData();

			for (liste2.resetToHead(); !liste2.isAtTail(); liste2.increment()) {
				Vertex vert1 = (Vertex) liste2.currentData();
				double dis = vert.dist(vert1);

				_adja[vert.getNum() - 1][vert1.getNum() - 1] = _adja[vert1.getNum() - 1][vert.getNum() - 1] = dis;
				// evtl. nur dreiecksmatrix
			}
		}
		L = liste;
	}

	/*---copy constructor---*/

	/**
	 * copy constructor, copy matrix from adjazens matrix
	 * 
	 * @param myAdja
	 *            adjazens matrix from init constructor
	 * @return _adja adjazens matrix
	 */

	public Adjazens(Adjazens myAdja) {
		int myAdjaLength = myAdja.length();
		_adja = new double[myAdjaLength][myAdjaLength];
		for (int i = 0; i < myAdjaLength; i++) {
			for (int j = 0; j < myAdjaLength; j++) {
				_adja[i][j] = myAdja.entry(i, j);
			}
		}
	}

	/*---public methods---*/

	/**
	 * @return length from adjazens matrix
	 */

	public int length() {
		return this._adja.length;
	}

	public static void setGUI(GUI g) {
		gui = g;
	}

	/**
	 * @param x
	 * @param y
	 * @return double
	 */

	public double entry(int x, int y) {
		return this._adja[x][y];
	}

	/**
	 * @param x
	 * @param y
	 * @param c
	 *            set c on the place x,y
	 */

	public void setEntry(int x, int y, double c) {
		this._adja[x][y] = c;
	}

	/*---public static method neighbour---*/

	/**
	 * @param adj1
	 * @return path[]
	 */

	public static int[] neighbour(Adjazens adj1) {
		Adjazens adj = new Adjazens(adj1);
		int[] path = new int[adj.length()];
		for (int w = 0; w < path.length; w++)
			path[w] = -1;
		int leng = adj.length();
		int point = 0;
		int index = 0;

		for (int z = 0; z < (path.length - 1); z++) {
			double min = 10000000;
			for (int i = 0; i < leng; i++) {
				double temp = adj.entry(point, i);
				if (temp < min && temp > 0) {
					index = i;
					min = temp;
				} // end if
			} // end for
			path[point] = index;
			for (int k = 0; k < adj.length(); k++) {
				adj.setEntry(point, k, 0);
			}
			for (int j = 0; j < adj.length(); j++) {
				adj.setEntry(j, point, 0);
			}
			point = index;

		}

		int leer = -1;
		for (int d = 0; d < path.length; d++) {
			if (path[d] == -1) {
				leer = d;
				d = path.length;
			}
		}

		path[leer] = 0;
		return path;

	}

	public static double mst(Adjazens adja) {
		Adjazens adj2 = new Adjazens(adja);
		double wert = 0.0;
		double min = inf;
		_points = new boolean[adja.length()];
		_points[0] = true;
		for (int i = 1; i < _points.length; i++) {
			_points[i] = false;
		}

		iterations = 0.;
		double fortschritt = 0;
		while (checkpoints(_points) != true && !LinKernighan.STOP) {
			fortschritt = (iterations / _points.length * 40) / LinKernighan.startTouren;
			gui.setFortschritt((int) fortschritt);
			wert = wert + findmin(adja, adj2);
			iterations++;
		}

		for (int l = 1; l < _points.length && !LinKernighan.STOP; l++) {
			for (int m = 1; m < _points.length; m++) {
				if (adj2.entry(l, m) != 0.0 && min > adj2.entry(l, m)) {
					min = adj2.entry(l, m);
				}
			}
		}
		wert = wert + min;
		if (!LinKernighan.STOP)
			gui.setFortschritt((int) (40 / LinKernighan.startTouren));
		return wert;
	}

	public static boolean checkpoints(boolean[] _points) {
		for (int i = 0; i < _points.length; i++) {
			if (_points[i] == false)
				return false;
		}
		return true;
	}

	public static double findmin(Adjazens adja, Adjazens adj2) {
		double min = inf;
		int index = 0;
		int index2 = 0;
		for (int i = 0; i < _points.length; i++) {
			if (_points[i] == false)
				continue;
			else {
				for (int k = 0; k < _points.length; k++) {
					if (adja.entry(i, k) != 0.0 && min > adja.entry(i, k)) {
						min = adja.entry(i, k);
						index = k;
						index2 = i;
					} // end 2. if
				} // end 2. for

			} // end else
		} // end for
		_points[index] = true;
		adj2.setEntry(index, index2, 0.0);
		adj2.setEntry(index2, index, 0.0);
		streichen(adja);
		return min;
	}

	public static void streichen(Adjazens adja) {
		for (int i = 0; i < _points.length; i++) {
			if (_points[i] == false)
				continue;
			for (int k = 0; k < _points.length; k++) {
				if (_points[k] == false)
					continue;
				adja.setEntry(i, k, 0.0);
				adja.setEntry(k, i, 0.0);
			} // end 2. for
		} // end for
	}

}// end class
