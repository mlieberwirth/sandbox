package main.java.LinKern.comaTree;

public class AVLTree extends SearchTree {
	protected class AVLTreeNode extends BinTreeNode {
		public int _hoch;

		/**
		 * default Consstructor
		 */
		public AVLTreeNode() {
			super();
			data = null;
			_hoch = 0;
		}

		public AVLTreeNode(Object obj) {
			super(obj);
			_hoch = 0;
		}

		public void setHohe(int hohe) {
			_hoch = hohe;
		}

		public int getHohe() {
			return _hoch;
		}

		public void plusHohe() {
			_hoch++;
		}

		public String toString() // conversion to string
		{
			String str = data != null ? data.toString() : "Empty";
			str = str + " " + _hoch;

			return str;
		}
	}

	/**
	 * default constructor
	 */

	public AVLTree() {
		super();
	}

	/**
	 * @param node
	 *            is parent to test
	 * @return true for good balance else false
	 */
	private boolean isBalance(BinTreeNode node) {
		int lh = -1, rh = -1;

		// nichts wenn node Blatt ist
		if (node.isLeaf())
			return true;

		// node hat nur rechtes Kind
		if (node.left == null)
			rh = ((AVLTreeNode) node.right).getHohe();
		// node hat linkes Kind
		else if (node.right == null)
			lh = ((AVLTreeNode) node.left).getHohe();

		// node hat 2 Kinder
		else {
			rh = ((AVLTreeNode) node.right).getHohe();
			lh = ((AVLTreeNode) node.left).getHohe();
		}

		if (Math.abs(lh - rh) > 1)
			return false;

		else
			return true;
	}

	/**
	 * @param node
	 *            to test balance and repair
	 * @return true if balance is okay
	 */
	public boolean makeBalance(BinTreeNode node) {
		// test ob Balance gest�rt
		if (isBalance(node))
			return true;

		// minus weil sonst Prob bei keine Kind und h�he 0
		int lh = -1, rh = -1, llh = -1, lrh = -1, rlh = -1, rrh = -1;

		// node hat nur rechtes Kind
		if (node.left != null) {
			lh = ((AVLTreeNode) node.left).getHohe();

			if (node.left.left != null)
				llh = ((AVLTreeNode) node.left.left).getHohe();
			if (node.left.right != null)
				lrh = ((AVLTreeNode) node.left.right).getHohe();

		}
		// node hat linkes Kind
		if (node.right != null) {
			rh = ((AVLTreeNode) node.right).getHohe();

			if (node.right.right != null)
				rrh = ((AVLTreeNode) node.right.right).getHohe();
			if (node.right.left != null)
				rlh = ((AVLTreeNode) node.right.left).getHohe();
		}

		// Linkeh�he gr��er als Rechteh�he
		if (lh > rh) {
			// links links
			if (llh > lrh)
				return rotRight(node);
			// links rechts
			else if (llh < lrh)
				return dRight(node);
			// Gleichheit
			else
				return rotRight(node);
		}
		// Rechteh�he gr��er als Linkeh�he
		else if (lh < rh) {
			// rechts links
			if (rlh > rrh)
				return dLeft(node);
			// rechts rechts
			else if (rlh < rrh)
				return rotLeft(node);
			// Gleichheit
			else
				return rotLeft(node);
		}
		// Gleicheit (Sinnlos aber der Vollst�ndigheit halber)
		else
			return true;
	}

	/**
	 * @param node
	 *            start node (inclu.)
	 */
	public void balComplette(BinTreeNode node) throws ArithmeticException {
		if (node == _dummy)
			return;

		makeBalance(node);
		balComplette(node.parent);

		if (!checkAVL())
			throw new ArithmeticException("Der Baum hat ein Ding weg!");
	}

	/**
	 * @param node
	 *            is start(incl.), dummy is end to clac heights
	 */
	public void makeHohe(BinTreeNode node) {
		if (node == _dummy)
			return;

		int lh = -1;
		int rh = -1;

		if (node.left != null)
			lh = ((AVLTreeNode) node.left).getHohe();

		if (node.right != null)
			rh = ((AVLTreeNode) node.right).getHohe();

		((AVLTreeNode) node).setHohe(Math.max(rh, lh) + 1);

		makeHohe(node.parent);
	}

	/**
	 * @param node
	 *            to rot right
	 * @return true if it was possible, _free is on it
	 */
	private boolean rotRight(BinTreeNode node) {
		BinTreeNode curry = new BinTreeNode();
		// _free & curry sitzen auf dem zu rot Knoten
		_free = curry = node;

		// _free geht zum linken Kind, Fehler wenn es nicht da ist (gibt's bei
		// uns nicht)
		if (!_freewalk("left"))
			return false;

		// hat _free jetzt rechts Kind muss die Ref. ge�ndert werden
		if (_free.right != null) {
			curry.left = _free.right;
			_free.right.parent = curry;
		}
		// bei keinem wird LTB von curry getrennt
		else
			curry.left = null;

		// formales umbiegen der Zeiger
		_free.parent = curry.parent;

		if (curry.isLeftChild())
			curry.parent.left = _free;
		else
			curry.parent.right = _free;

		curry.parent = _free;
		_free.right = curry;

		makeHohe(curry);

		return true;
	}

	/**
	 * @param node
	 *            to rot left
	 * @return true if it was possible, _free is on it
	 */
	private boolean rotLeft(BinTreeNode node) {
		// Komentare �qui. zu rotRight
		BinTreeNode curry = new BinTreeNode();
		curry = _free = node;

		if (!_freewalk("right"))
			return false;

		if (_free.left != null) {
			curry.right = _free.left;
			_free.left.parent = curry;
		} else
			curry.right = null;

		_free.parent = curry.parent;

		if (curry.isLeftChild())
			curry.parent.left = _free;
		else
			curry.parent.right = _free;

		_free.left = curry;
		curry.parent = _free;

		makeHohe(curry);

		return true;
	}

	/**
	 * @param node
	 *            to rot left and right
	 * @return true if it was possible
	 */
	private boolean dRight(BinTreeNode node) {
		// zuerst links rotieren
		if (!rotLeft(node.left))
			return false;
		// danach nach rechts
		if (!rotRight(node))
			return false;
		// wenn alles stimmt gibt's ein true
		return true;
	}

	/**
	 * @param node
	 *            to rot right and left
	 * @return true if it was possible
	 */
	private boolean dLeft(BinTreeNode node) {
		// erst rechts rotten
		if (!rotRight(node.right))
			return false;
		// dann links rotten
		if (!rotLeft(node))
			return false;
		// naja was wohl...
		return true;
	}

	/**
	 * @param obj
	 *            to remove
	 * @return obj whose was deleted (balance is okay)
	 */
	public Comparable remove(Comparable obj) throws ArithmeticException {
		// ist Objekt �berhaupt da, wenn, _free is drauf
		if (_freeRun(obj)) {
			// hat die R�ckgabe
			BinTreeNode nody = _free;

			// keine Kinder
			if (_free.isLeaf()) {
				// Zeigerbiegen
				if (_free.isLeftChild()) {
					_free = _free.parent;
					// muss das, oder wird der autom. entfernt?
					_free.left.parent = null;
					_free.left = null;
				} else {
					_free = _free.parent;
					// muss das, oder wird der autom. entfernt?
					_free.right.parent = null;
					_free.right = null;
				}

				makeHohe(_free);
				balComplette(_free);

				if (!checkAVL())
					throw new ArithmeticException("Der Baum hat ein Ding weg!");

				return (Comparable) nody.data;
			}

			// hat ein Kind
			if (_free.left == null && _free.right != null) {
				// Zeigerbiegen
				_free.right.parent = _free.parent;

				if (_free.isLeftChild())
					_free.parent.left = _free.right;
				else
					_free.parent.right = _free.right;

				_free = _free.parent;

				makeHohe(_free);
				balComplette(_free);

				if (!checkAVL())
					throw new ArithmeticException("Der Baum hat ein Ding weg!");

				return (Comparable) nody.data;
			}
			if (_free.left != null && _free.right == null) {
				// Zeigerbiegen
				_free.left.parent = _free.parent;

				if (_free.isLeftChild())
					_free.parent.left = _free.left;
				else
					_free.parent.right = _free.left;

				_free = _free.parent;

				makeHohe(_free);
				balComplette(_free);

				if (!checkAVL())
					throw new ArithmeticException("Der Baum hat ein Ding weg!");

				return (Comparable) nody.data;
			}
			// zwei Kinder
			else {
				// curry kommt als Helfer, Teamwork ist angesagt :-}
				BinTreeNode curry = new BinTreeNode();

				curry = _free;
				// _free geht zum linkesten im RTB

				_freewalk("right");

				while (_free.left != null)
					_freewalk("left");

				// ein Dritter wir gebrauch, da _free nach dem remove "raus" ist
				AVLTreeNode node = new AVLTreeNode(_free.data);

				// Vorteil: am Ende braucht keine balance gemacht zu werden,
				// geschieht schon in remove
				remove((Comparable) _free.data);

				// node nimmt Z�gel von curry in die Hand
				node.parent = curry.parent;
				node.left = curry.left;
				node.right = curry.right;
				node._hoch = ((AVLTreeNode) curry).getHohe();

				if (curry.isLeftChild())
					curry.parent.left = node;
				else
					curry.parent.right = node;

				if (curry.left != null)
					curry.left.parent = node;

				if (curry.right != null)
					curry.right.parent = node;

				_free = curry = node;

				if (!checkAVL())
					throw new ArithmeticException("Der Baum hat ein Ding weg!");

				return (Comparable) nody.data;
			}

		} else
			return null;
	}

	/**
	 * @param obj
	 *            will insert, if it allready exist do nothing
	 * @return inserted obj (balance is okay)
	 */

	public Comparable insert(Comparable obj) throws ArithmeticException {
		// ist obj schon da, nix geschieht
		if (_freeRun(obj))
			return (Comparable) _free.data;

		else {
			AVLTreeNode node = new AVLTreeNode(obj);
			// beim leeren Baum wird er root
			if (isEmpty()) {
				_dummy.left = node;
				node.parent = _dummy;
				_free = _curr = node;
			}
			// ist er gr��er als Vati setz ihn rechts
			else if (obj.compareTo(_free.data) > 0) {
				_free.right = node;
				node.parent = _free;
			}
			// ist er kleiner als Vati setz ihn links
			else {
				_free.left = node;
				node.parent = _free;
			}

			makeHohe(_free);
			balComplette(_free);

			_free = _curr = node;
		}

		if (!checkAVL())
			throw new ArithmeticException("Der Baum hat ein Ding weg!");

		return obj;
	}

	/**
	 * @return true if it is a AVLTree
	 */
	protected boolean checkAVL() {
		// Test ob _check an ist
		if (!_checkMode)
			return true;

		if (isEmpty())
			return true;

		// normaler Baumcheck (Search)
		if (!checkSearch())
			return false;

		// check vom SearchTree
		if (_checken((AVLTreeNode) _dummy.left, 0) > 0)
			return false;
		else
			return true;
	}

	/**
	 * @param node
	 *            to test
	 * @param count
	 *            starts normaly with 0
	 * @return count with height difference
	 */
	private int _checken(AVLTreeNode node, int count) {
		// nichts mehr zu tuen
		if (node.isLeaf())
			return count;

		// Balancentest
		if (!isBalance(node))
			count++;

		// LTB f�r den H�hentest
		if (node.left != null)
			// wenn's nicht okay ist z�hle hoch
			if (((AVLTreeNode) node.left).getHohe() != height(node.left))
				count++;
			else
				count = _checken((AVLTreeNode) node.left, count);

		// RTB f�r den H�hentest
		if (node.right != null)
			// wenn's nicht okay ist z�hle hoch
			if (((AVLTreeNode) node.right).getHohe() != height(node.right))
				count++;
			else
				count = _checken((AVLTreeNode) node.right, count);

		// Wurzeltest
		if (height(_dummy.left) != ((AVLTreeNode) _dummy.left)._hoch)
			count++;

		return count;
	}
}