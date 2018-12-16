package main.java.LinKern.comaList;

import java.util.NoSuchElementException;

public class DoubleList {
	/**
	 * private: inner class for list nodes
	 */
	private class ListNode {
		public Object obj; // object referenced by node
		public ListNode next; // successor of node in list
		public ListNode prev; // predecessor of node in list

		public ListNode() // default constructor
		{
			obj = null;
			next = prev = null;
		}

		public ListNode(Object myObj) // init constructor
		{
			obj = myObj;
			next = prev = null;
		}

		public String toString() // conversion to string
		{
			return (obj != null ? obj.toString() : "*");
		}

	} // class ListNode

	/*** data ******************************************************************/

	/**
	 * private: reference to dummy node at head of list
	 */
	private ListNode _head;

	/**
	 * private: reference to dummy node at tail of list
	 */
	private ListNode _tail;

	/**
	 * private: reference to current node in list
	 */
	private ListNode _curr;

	/*** constructors **********************************************************/

	/**
	 * default constructor, initializes empty list
	 */
	public DoubleList() {
		_head = new ListNode(); // create dummies
		_tail = new ListNode();
		_curr = _head;

		_head.next = _tail.next = _tail; // init. empty list
		_head.prev = _tail.prev = _head;

	} // DoubleList()

	/**
	 * copy constructor
	 *
	 * @param myList
	 *            list to be copied
	 */
	public DoubleList(DoubleList myList) {
		this(); // initialize empty list

		// rem: do not use iteration methods of myList for internal purpose!
		for (ListNode act = myList._head.next; act != myList._tail; act = act.next)
			insertAtTail(act.obj);

	} // DoubleList()

	/***
	 * get methods
	 ***********************************************************/

	/**
	 * @return is list empty?
	 */
	public boolean isEmpty() {
		return (_head.next == _tail);
	}

	/**
	 * @return actual number of list entries
	 */
	public int length() {
		int len = 0;

		for (resetToHead(); !isAtTail(); increment())
			++len;

		return len;

	} // length()

	/***
	 * methods for current node
	 **********************************************/

	/**
	 * @return is current node at head?
	 */
	public boolean isAtHead() {
		return (_curr == _head);
	}

	/**
	 * @return is current node at tail?
	 */
	public boolean isAtTail() {
		return (_curr == _tail);
	}

	/**
	 * @return object referenced by current node if possible
	 * @exception NoSuchElementException
	 *                if current node is out of list
	 */
	public Object currentData() throws NoSuchElementException {
		if (isAtHead())
			throw new NoSuchElementException("Current list node is at head!");
		if (isAtTail())
			throw new NoSuchElementException("Current list node is at tail!");

		return _curr.obj;

	} // currentData()

	/**
	 * @return reset current node to head
	 */
	public void resetToHead() {
		_curr = _head.next;
	}

	/**
	 * @return reset current node to tail
	 */
	public void resetToTail() {
		_curr = _tail.prev;
	}

	/**
	 * @return reset current node to next node in list
	 */
	public void increment() {
		_curr = _curr.next;
	}

	/**
	 * @return reset current node to previous node in list
	 */
	public void decrement() {
		_curr = _curr.prev;
	}

	/***
	 * insertion methods
	 *****************************************************/

	/**
	 * private: insert given object before given node
	 *
	 * @param obj
	 *            object to be inserted
	 * @param node
	 *            node before which to insert
	 */
	private void _insert(Object obj, ListNode node) {
		ListNode newNode = new ListNode(obj);

		newNode.next = node;
		newNode.prev = node.prev;
		node.prev = node.prev.next = newNode;

	} // _insert()

	/**
	 * insert object as first into list
	 *
	 * @param obj
	 *            object to be inserted
	 */
	public void insertAtHead(Object obj) {
		_insert(obj, _head.next);
	}

	/**
	 * insert object as last into list
	 *
	 * @param obj
	 *            object to be inserted
	 */
	public void insertAtTail(Object obj) {
		_insert(obj, _tail);
	}

	/**
	 * insert object before current node, if possible
	 *
	 * @param obj
	 *            object to be inserted
	 * @exception NoSuchElementException
	 *                if current node is at head
	 */
	public void insertBefore(Object obj) throws NoSuchElementException {
		if (isAtHead())
			throw new NoSuchElementException("Current list node is at head!");

		_insert(obj, _curr);

	} // insertBefore()

	/**
	 * insert object after current node, if possible
	 *
	 * @param obj
	 *            object to be inserted
	 * @exception NoSuchElementException
	 *                if current node is at tail
	 */
	public void insertAfter(Object obj) throws NoSuchElementException {
		if (isAtTail())
			throw new NoSuchElementException("Current list node is at tail!");

		_insert(obj, _curr.next);

	} // insertAfter()

	/***
	 * removal methods
	 *******************************************************/

	/**
	 * private: remove given node from list
	 *
	 * @param node
	 *            node to be removed
	 * @return object referenced by removed node
	 */
	private Object _remove(ListNode node) {
		Object obj = null;

		if (node != _head && node != _tail) {
			obj = node.obj;
			node.next.prev = node.prev;
			node.prev.next = node.next;
		}

		return obj;

	} // _remove()

	/**
	 * remove first object in list if any
	 *
	 * @return object referenced by removed node
	 */
	public Object removeAtHead() {
		return _remove(_head.next);
	}

	/**
	 * remove last object in list if any
	 *
	 * @return object referenced by removed node
	 */
	public Object removeAtTail() {
		return _remove(_tail.prev);
	}

	/**
	 * remove current node from list if possible
	 *
	 * @return object referenced by removed node
	 * @exception NoSuchElementException
	 *                if current node is out of list
	 */
	public Object removeCurrent() throws NoSuchElementException {
		if (isAtHead())
			throw new NoSuchElementException("Current list node is at head!");
		if (isAtTail())
			throw new NoSuchElementException("Current list node is at tail!");

		return _remove(_curr);

	} // removeCurrent()

	/**
	 * remove all nodes referencing given object from list
	 *
	 * @param obj
	 *            object to be searched
	 */
	public void remove(Object obj) {
		// rem: do not use iteration methods for internal purpose!
		for (ListNode act = _head.next; act != _tail; act = act.next)
			if (obj.equals(act.obj))
				_remove(act);

	} // remove()

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
		for (ListNode act = _head.next; act != _tail; act = act.next)
			strbuf.append(act.toString());

		return strbuf.toString();

	} // toString()
} // class DoubleList
