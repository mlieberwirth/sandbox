package main.java.LinKern.comaTree;

abstract class BinTree {
	/**
	 * class for tree nodes
	 */
	protected class BinTreeNode {
		public BinTreeNode parent;
		public BinTreeNode left;
		public BinTreeNode right;
		public Object data;

		public BinTreeNode() // default constructor
		{
			parent = left = right = null;
			data = null;
		}

		public BinTreeNode(Object obj) // init constructor
		{
			data = obj;
		}

		public boolean isLeaf() // is node a leaf in tree?
		{
			return left == null && right == null;
		}

		public boolean isRoot() // is node root of tree?
		{
			return left != null || right != null;
		}

		public boolean isLeftChild() // is node left child of parent?
		{
			if (parent != null)
				return parent.left == this;
			return false;
		}

		public String toString() // conversion to string
		{
			return (data != null ? data.toString() : "Empty");
		}

	} // class BinTreeNode

	/*** data ******************************************************************/

	protected BinTreeNode _dummy;

	protected BinTreeNode _curr;

	protected static boolean _checkMode = true;

	/*** constructors **********************************************************/

	// default constructor, initializes empty tree
	public BinTree() {
		_dummy = _curr = new BinTreeNode();
	}

	/***
	 * get methods
	 ***********************************************************/

	// is tree empty?
	public boolean isEmpty() {
		return _dummy.left == null;
	}

	// root node of tree
	// -> what should be returned if tree is empty??
	protected BinTreeNode _getRoot() {
		return _dummy.left;
	}

	// current number of tree nodes
	public int getSize() {
		if (!isEmpty())
			return 0;

		int count = 1;
		reset();

		while (!isAtEnd()) {
			count++;
			increment();
		}

		return count;

	} // getSize()

	// height of tree
	public int getHeight() {
		if (isEmpty())
			return 0;

		return height(_dummy.left);

	}

	public int height(BinTreeNode tree) {
		int lheight = 0;
		int rheight = 0;
		// System.out.println(tree);
		if (tree.left != null)
			lheight = height(tree.left) + 1;

		if (tree.right != null)
			rheight = height(tree.right) + 1;

		return Math.max(lheight, rheight);
	}

	/**
	 * @param node
	 * @return waylenght from node to root
	 */
	public int zurHeight(BinTreeNode node) {
		int count = 0;
		while (node.parent != _dummy) {
			count++;
			node = node.parent;
		}
		return count;
	}

	/***
	 * methods for current node
	 **********************************************/

	// reset current node to first node in inorder sequence
	public void reset() {
		_curr = _dummy.left;
		while (_curr.left != null)
			_curr = _curr.left;
	}

	// does current node stand at end of inorder sequence?
	public boolean isAtEnd() {
		BinTreeNode testy = new BinTreeNode();
		testy = _dummy.left;

		while (testy.right != null)
			testy = testy.right;

		return testy == _curr;
	}

	// reset current node to successor in inorder sequence
	public void increment() {
		String origin = "left";

		do {
			if (origin.equals("above")) {
				if (_curr.left != null)
					_curr = _curr.left;
				else
					return;
			} else if (origin.equals("left")) {
				if (_curr.right != null) {
					_curr = _curr.right;
					origin = "above";
				} else
					origin = "right";
			} else {
				if (_curr.isLeftChild())
					origin = "left";
				_curr = _curr.parent;
			}
		} while (!origin.equals("left"));

	} // increment()

	// object referenced by current node
	public Object currentData() {
		return _curr.data;
	}

	// ist current node a leaf?
	public boolean isLeaf() {
		return _curr.isLeaf();
	}

	/***
	 * conversion methods
	 ****************************************************/

	// convert tree to string
	// -> please use getClass() somewhere so that class name of "this" shows
	public String toString() {
		if (isEmpty())
			return "Leer";

		BinTreeNode testy = _dummy.left;
		while (testy.left != null)
			testy = testy.left;

		BinTreeNode testy2 = _dummy.left;

		while (testy2.right != null)
			testy2 = testy2.right;

		String origin = "left";
		int t = 0;

		String str = "";
		for (int i = zurHeight(testy); i >= 0; i--)
			str = str + "~";
		str = str + testy.toString() + "\n";

		// System.out.println(getHeight());

		while (testy != testy2) {
			do {

				if (origin.equals("above")) {
					if (testy.left != null)
						testy = testy.left;
					else {
						origin = "left";
						break;
					}
				} else if (origin.equals("left")) {
					if (testy.right != null) {
						testy = testy.right;
						origin = "above";
					} else
						origin = "right";
				} else {
					if (testy.isLeftChild())
						origin = "left";
					testy = testy.parent;
				}

			} while (!origin.equals("left"));

			for (int i = zurHeight(testy); i >= 0; i--)
				str = str + "~";

			str = str + testy.toString() + "\n";

		}
		return str;

	} // toString()

	/***
	 * set methods
	 ***********************************************************/

	// switch debugging mode
	public static void setCheck(boolean mode) {
		_checkMode = mode;
	}

	/***
	 * debugging methods
	 *****************************************************/

	// check consistency of links in entire tree
	protected boolean _checkLinks() {
		if (!_checkMode) // no checking wanted
			return true;

		return _checky(_dummy.left);

	}

	protected boolean _checky(BinTreeNode node) {
		if (node.isLeaf())
			return true;

		if (node.left != null)
			if (node == node.left.parent)
				_checky(node.left);
			else
				return false;

		if (node.right != null)
			if (node == node.right.parent)
				_checky(node.right);
			else
				return false;

		return true;
	}
	// _checkLinks()

} // class BinTree
