package main.java.LinKern.projectGraph;

public class Vertex implements Comparable {

	private int _num;
	private double _x;
	private double _y;

	/**
	 * Default constructor
	 */
	public Vertex() {
		_num = 0;
		_x = 0;
		_y = 0;
	}

	/**
	 * Default constructor
	 * 
	 * @param x
	 *            The x to set.
	 * @param y
	 *            The y to set.
	 */
	public Vertex(int num, double x, double y) {
		_num = num;
		_x = x;
		_y = y;
	}

	/**
	 * @return Returns the x.
	 */
	public double getX() {
		return _x;
	}

	/**
	 * @param x
	 *            The x to set.
	 */
	public void setX(double x) {
		_x = x;
	}

	/**
	 * @return Returns the y.
	 */
	public double getY() {
		return _y;
	}

	/**
	 * @param y
	 *            The y to set.
	 */
	public void setY(double y) {
		_y = y;
	}

	/**
	 * @return number
	 */
	public int getNum() {
		return _num;
	}

	/**
	 * @param num
	 *            The num to set.
	 */
	public void setNum(int num) {
		_num = num;
	}

	/**
	 * Checks if a given vertex equals this one
	 * 
	 * @param v
	 *            vertix
	 * @return true is the are the same, false else
	 */
	public boolean equals(Vertex v) {
		return (_x == v._x && _y == v._y);
	}

	/**
	 * Calcs the distance to given vertex
	 * 
	 * @param v
	 *            Vertex
	 * @return distance
	 */
	public double dist(Vertex v) {
		return Math.round(Math.sqrt(Math.pow(_x - v._x, 2) + Math.pow(_y - v._y, 2)));
	}

	/**
	 * compare the num of two vertexs
	 */
	public int compareTo(Object arg) {
		Vertex ver = (Vertex) arg;
		if (ver._num > _num)
			return 1;
		else if (ver._num < _num)
			return -1;
		else
			return 0;
	}

	public String toString() {
		return ("Nr: " + _num + " (x: " + _x + " y: " + _y + ")");
	}
}
