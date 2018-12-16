package main.java.LinKern.projectGraph;

public class Edge implements Comparable {
	private Vertex _v1;
	private Vertex _v2;
	private double _length;

	public Edge(Vertex v1, Vertex v2, double lenght) {
		_v1 = v1;
		_v2 = v2;
		_length = lenght;
	}

	public Edge(Vertex v, Vertex w) {
		_v1 = v;
		_v2 = w;

		_length = v.dist(w);
	}

	/**
	 * @return lenght
	 */
	public double length() {
		return _length;
	}

	/**
	 * @return Returns the x1.
	 */
	public double getX1() {
		return _v1.getX();
	}

	/**
	 * @return Returns the x2.
	 */
	public double getX2() {
		return _v2.getX();
	}

	/**
	 * @return Returns the y1.
	 */
	public double getY1() {
		return _v1.getY();
	}

	/**
	 * @return Returns the y2.
	 */
	public double getY2() {
		return _v2.getY();
	}

	/**
	 * resets all points of edge
	 * 
	 * @param lenght
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void setAll(double x1, double y1, double x2, double y2, double length) {
		_v1.setX(x1);
		_v2.setX(x2);
		_v1.setY(y1);
		_v2.setY(y2);
		_length = length;
	}

	/**
	 * checks for equality
	 * 
	 * @param e
	 *            Edge
	 * @return false if not
	 */
	public boolean equals(Edge e) {
		boolean res = false;
		if ((_v1.equals(e._v1) && _v2.equals(e._v2)) || (_v1.equals(e._v2) && _v2.equals(e._v1)))
			if (_length == e._length)
				res = true;
		return res;
	}

	/**
	 * Checks if edge starts or ends at a given vertex
	 * 
	 * @param v
	 *            Vertex
	 * @return true, if edge starts or ends at v, false else
	 */
	public boolean startsAt(Vertex v) {
		return (_v1.equals(v) || _v2.equals(v));
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * @param obj
	 *            object to be compared to
	 * @return returns -1 is this edge is longer, 0 if length is the same and 1
	 *         if length of given edge is bigger
	 */
	public int compareTo(Object obj) {
		Edge E = (Edge) obj;

		if (E.length() > this.length())
			return 1;
		else if (E.length() == this.length())
			return 0;
		else
			return -1;
	}

	public String toString() {
		return ("(" + _v1.toString() + "; " + _v2.toString() + " = L: " + _length + ")");
	}
}
