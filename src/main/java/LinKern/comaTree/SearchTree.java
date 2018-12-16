package main.java.LinKern.comaTree;

public class SearchTree extends BinTree {
	protected BinTreeNode _free;

	/**
	 * default constructor
	 */
	public SearchTree() {
		super();
		// Zusatzzeiger
		_free = new BinTreeNode();
		_free = _curr;
	}

	/**
	 * @param obj
	 *            to search
	 * @return true if obj found(_free is on), false _free is on "parent"
	 */
	public boolean _freeRun(Comparable obj) {
		_freeToRoot();

		if (isEmpty())
			return false;

		// soll nach Suchbaumeigenschaft durch den Baum laufen
		while (!_free.isLeaf()) {
			if (obj.compareTo((Comparable) _free.data) < 0 && _free.left != null)
				_free = _free.left;
			else if (obj.compareTo((Comparable) _free.data) > 0 && _free.right != null)
				_free = _free.right;
			else
			// ist es der Gesuchte bleibe, sonst bleib bei Vati
			if (obj.compareTo((Comparable) _free.data) == 0)
				return true;
			else
				return false;
		}
		// der Wurzeltest
		if (obj.compareTo((Comparable) _free.data) == 0)
			return true;

		return false;
	}

	/**
	 * @param dir
	 *            is way to go
	 * @return true if it was possible and false if not
	 */
	public boolean _freewalk(String dir) {
		boolean result = true;

		// geht liniks wenn da ein Knoten ist
		if (dir.equals("left") && _free.left != null)
			_free = _free.left;
		// geht rechts wenn da ein Knoten ist
		else if (dir.equals("right") && _free.right != null)
			_free = _free.right;
		// geht hoch wenn da ein Knoten ist
		else if (dir.equals("up") && _free != _dummy.left)
			_free = _free.parent;

		else
			result = false;

		return result;
	}

	/**
	 * @return data from _free
	 */
	public Comparable _freedata() {
		return (Comparable) _free.data;
	}

	/**
	 * set _free to root
	 */
	public void _freeToRoot() {
		_free = _dummy.left;
	}

	/**
	 * set _curr on _free
	 */
	public void _currTo_free() {
		_curr = _free;
	}

	/**
	 * set _free on _curr
	 */
	public void _freeTo_curr() {
		_free = _curr;
	}

	/**
	 * return tree-heigth
	 */
	public int maxSearch() {
		return getHeight();
	}

	/**
	 * @param obj
	 *            to look for
	 * @return null if no same obj found or the obj, if it same
	 */
	public Comparable contains(Comparable obj) {
		if (_freeRun(obj))
			return obj;
		return null;
	}

	/**
	 * @param obj
	 *            to delete
	 * @return deleted obj, _free is on (ex)parent or null
	 */
	public Comparable remove(Comparable obj) throws ArithmeticException {
		// ist Objekt �berhaupt da, wenn _free is drauf
		if (_freeRun(obj)) {
			// hat die R�ckgabe
			BinTreeNode nody = _free;

			// keine Kinder
			if (_free.isLeaf()) {
				// Zeigerbiegen
				if (_free.isLeftChild()) {
					_free = _free.parent;
					_free.left = null;
				} else {
					_free = _free.parent;
					_free.right = null;
				}

				if (!checkSearch())
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

				if (!checkSearch())
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

				if (!checkSearch())
					throw new ArithmeticException("Der Baum hat ein Ding weg!");

				return (Comparable) nody.data;
			}
			// zwei Kinder
			else {
				// _curr kommt als Helfer, Teamwork ist angesagt :-}
				_currTo_free();

				// _free geht zum linkesten im RTB
				_freewalk("right");
				while (_free.left != null)
					_freewalk("left");

				// ein Dritter wir gebrauch da _free nach dem remove "raus" ist
				BinTreeNode node = new BinTreeNode(_free.data);
				remove((Comparable) _free.data);

				// node nimmt Z�gel von _curr in die Hand
				node.parent = _curr.parent;
				node.left = _curr.left;
				node.right = _curr.right;

				if (_curr.isLeftChild())
					_curr.parent.left = node;
				else
					_curr.parent.right = node;

				_curr.left.parent = node;
				if (_curr.right != null)
					_curr.right.parent = node;

				// es ist geschaft, alles sind froh �ber den Erfolg
				_free = _curr = node;

				if (!checkSearch())
					throw new ArithmeticException("Der Baum hat ein Ding weg!");

				return (Comparable) nody.data;
			}

		} else
			return null;
	}

	/**
	 * @param obj
	 *            to insert
	 * @return inserted obj or allready ex. obj
	 */
	public Comparable insert(Comparable obj) throws ArithmeticException {
		// ist obj schon da, nix geschieht
		if (_freeRun(obj))
			return (Comparable) _free.data;

		else {
			BinTreeNode node = new BinTreeNode(obj);
			// beim leeren Baum wird er root
			if (isEmpty()) {
				_dummy.left = node;
				node.parent = _dummy;
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

			_free = _curr = node;
		}

		if (!checkSearch())
			throw new ArithmeticException("Der Baum hat ein Ding weg!");

		return obj;
	}

	/**
	 * @return true if it is a SearchTree
	 */
	protected boolean checkSearch() {
		// Test ob _check an ist
		if (!_checkMode)
			return true;

		// wenig zu tuen bei lerrem Baum
		if (isEmpty())
			return true;

		// normaler 2er Baumcheck
		if (!_checkLinks())
			return false;

		// check vom SearchTree
		if (!_checkz())
			return false;
		else
			return true;
	}

	/**
	 * @return true and all is okay
	 */
	private boolean _checkz() {
		reset();

		Comparable node1 = (Comparable) _curr.data;

		while (!isAtEnd()) {
			increment();
			if ((node1).compareTo(_curr.data) > 0)
				return false;
			node1 = (Comparable) _curr.data;

		}

		return true;
	}
}