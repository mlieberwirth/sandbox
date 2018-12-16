package main.java.LinKern.comaList;

import java.util.NoSuchElementException;

public class LinkedList {
	/**
	 * private: inner class for list nodes
	 */
	private class ListNode {
		public Object obj; // object referenced by node
		public ListNode next; // successor of node in list

		public ListNode() // default constructor
		{
			obj = null;
			next = null;
		}

		public ListNode(Object myObj) // init constructor
		{
			obj = myObj;
			next = null;
		}

		public String toString() // conversion to string
		{
			return (obj != null ? obj.toString() : "Empty");
		}

	} // class ListNode

	/*** data ******************************************************************/

	/**
	 * private: reference to first node in list
	 */
	private ListNode _head;

	/**
	 * private: reference to current node in list
	 */
	private ListNode _curr;

	/*** constructors **********************************************************/

	/**
	 * default constructor, initializes empty list
	 */
	public LinkedList() {
		_head = _curr = null;
	}

	/**
	 * copy constructor
	 *
	 * @param myList
	 *            list to be copied
	 */
	public LinkedList(LinkedList myList) {
		this(); // initialize empty list

		ListNode tail = null;

		// rem: do not use iteration methods of myList for internal purpose!
		for (ListNode act = myList._head; act != null; act = act.next) {
			ListNode newNode = new ListNode(act.obj);

			if (tail == null)
				_head = tail = newNode;
			else
				tail = tail.next = newNode;

		} // for ( ListNode act )
	} // LinkedList()

	/***
	 * get methods
	 ***********************************************************/

	/**
	 * @return is list empty?
	 */
	public boolean isEmpty() {
		return (_head == null);
	}

	/**
	 * @return actual number of list entries
	 */
	public int length() {
		int len = 0;

		// rem: do not use iteration methods for internal purpose!
		for (ListNode act = _head; act != null; act = act.next)
			++len;

		return len;

	} // length()

	/***
	 * methods for current node
	 **********************************************/

	/**
	 * @return is current reference out of list?
	 */
	public boolean isAtEnd() {
		return (_curr == null);
	}

	public boolean isNextAtEnd() {
		return (_curr.next == null);
	}

	/**
	 * set current node to list head
	 */
	public void reset() {
		_curr = _head;
	}

	/**
	 * reset current node to next list entry
	 */
	public void increment() {
		if (!isAtEnd()) // stay in place if at end
			_curr = _curr.next;
	}

	/**
	 * @return object associated with current node
	 * @exception NoSuchElementException
	 *                if current node is out of list
	 */
	public Object currentData() throws NoSuchElementException {
		if (isAtEnd())
			throw new NoSuchElementException("No current list node!");

		return _curr.obj;
	}

	/***
	 * set methods
	 ***********************************************************/

	/**
	 * insert object at beginning of list
	 *
	 * @param obj
	 *            object to be inserted
	 */
	public void insert(Object obj) {
		ListNode newNode = new ListNode(obj);

		newNode.next = _head;
		_head = newNode;

	} // insert()

	/**
	 * insert object after current node, if possible
	 *
	 * @param obj
	 *            object to be inserted
	 * @exception NoSuchElementException
	 *                if current node is out of list
	 */
	public void insertAfter(Object someData) throws NoSuchElementException {
		ListNode newNode = new ListNode(someData);
		if (isEmpty())
			_head = _curr = newNode;
		else {
			if (_curr == null)
				throw new NoSuchElementException("Cursor not on a valid element.");

			newNode.next = _curr.next;
			_curr.next = newNode;
			_curr = newNode;
		}
	}

	/**
	 * remove first node from list if possible
	 *
	 * @return object referenced by deleted node
	 * @exception NoSuchElementException
	 *                if list is empty
	 */
	public Object removeFirst() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("List is empty!");

		Object obj = _head.obj;
		_head = _head.next;

		return obj;

	} // removeFirst()

	public Object advance() throws NoSuchElementException {
		if (isAtEnd())
			throw new NoSuchElementException("Kein Knoten mehr!");

		_curr = _curr.next;
		return _curr.obj;
	}

	/**
	 * remove all nodes referencing given object from list
	 *
	 * @param obj
	 *            object to be searched
	 */
	public void remove(Object obj) {
		ListNode lastNode = null;

		// rem: do not use iteration methods for internal purpose!
		for (ListNode act = _head; act != null; act = act.next) {
			if (obj.equals(act.obj))
				if (lastNode == null)
					_head = act.next;
				else
					lastNode.next = act.next;

			lastNode = act;

		} // for ( act )

	} // remove()

	/**
	 * remove current node from list, if any
	 * 
	 * @exception NoSuchElementException
	 *                if current node is out of list
	 */
	public void removeCurrent() throws NoSuchElementException {
		if (isAtEnd())
			throw new NoSuchElementException("No current list node!");

		ListNode lastNode = null;

		// rem: do not use iteration methods for internal purpose!
		for (ListNode act = _head; act != null; act = act.next) {
			if (act == _curr) // found _curr back
			{
				if (lastNode == null)
					_head = act.next;
				else
					lastNode.next = act.next;

				break; // there is only one current node
			}

			lastNode = act;

		} // for ( act )
	} // removeCurrent()

	/**
	 * delete all nodes in list
	 */
	public void removeAll() {
		if (isEmpty())
			return;

		_curr = _head;
		_head.next = null;
		_head = null;
	}

	/***
	 * conversion methods
	 ****************************************************/

	/**
	 * convert list to string
	 *
	 * @return string of listed objects
	 */
	public String toString() {
		StringBuffer strbuf = new StringBuffer();

		// rem: do not use iteration methods for internal purpose!
		for (ListNode act = _head; act != null; act = act.next)
			strbuf.append(act.toString() + "\n");

		return strbuf.toString();

	}
}
