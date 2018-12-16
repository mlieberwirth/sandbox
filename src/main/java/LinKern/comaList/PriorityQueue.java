package main.java.LinKern.comaList;

import java.util.NoSuchElementException;
import java.util.Random;

public class PriorityQueue {
	/*** data ******************************************************************/

	/**
	 * private: code for "no valid index"
	 */
	private static final int UNUSED = -1;

	/**
	 * private: flag for debugging
	 */
	private static boolean _checkMode = false;

	/**
	 * private: heap as array, valid indices: 1 ... n
	 */
	private Comparable[] _objects;

	/**
	 * private: index of last element in array
	 */
	private int _lastIndex;

	/*** constructors **********************************************************/

	/**
	 * length constructor: initializes empty heap with internal space for given
	 * number of objects.
	 *
	 * @param numEntries
	 *            maximum number of objects
	 */
	public PriorityQueue(int numEntries) {
		_objects = new Comparable[numEntries];
		_lastIndex = UNUSED;
	}

	/**
	 * array constructor: initialize heap from given array that contains data in
	 * arbitrary order. The queue does not alter the given array. The length of
	 * this array designates the maximum number of elements simultaneously
	 * allowed in the queue.
	 *
	 * @param array
	 *            array filled with data
	 */
	public PriorityQueue(Comparable[] array) {
		_objects = new Comparable[array.length];
		for (int i = 0; i < array.length; i++)
			_objects[i] = array[i];

		_lastIndex = _objects.length - 1;
		_createHeap();

	} // PriorityQueue()

	/***
	 * get methods
	 ***********************************************************/

	/**
	 * @return is queue empty?
	 */
	public boolean isEmpty() {
		if (_lastIndex == UNUSED)
			return true;
		else
			return false;
	}

	/**
	 * @return current number of queue entries
	 */
	public int size() {
		return _lastIndex + 1;
	}

	/**
	 * get minimum queue entry without removing it
	 *
	 * @return minimum queue entry if possible
	 * @exception NoSuchElementException
	 *                if queue is empty
	 */
	public Comparable top() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("Queue is empty!!!");
		else
			return _objects[0];
	} // top()

	/***
	 * set methods
	 ***********************************************************/

	/**
	 * switch debugging mode
	 */
	public static void setCheck(boolean mode) {
		_checkMode = mode;
	}

	/**
	 * insert object into queue
	 *
	 * @param obj
	 *            object to be inserted
	 * @exception ArrayIndexOutOfBoundsException
	 *                if queue is full.
	 */
	public void push(Comparable obj) throws ArrayIndexOutOfBoundsException {
		if (_objects.length == _lastIndex + 1)
			throw new ArrayIndexOutOfBoundsException("Queue is full!!!");
		else {
			_lastIndex++;
			_objects[_lastIndex] = obj;

			// restore heap property

			int n = _lastIndex; // counter
			boolean bool = false; // turns true if obj has found position in
									// heap
			while (n > 0 && !bool) {
				int p = _parent(n);
				if (_objects[n].compareTo(_objects[_parent(n)]) < 0) {
					// switch objects
					Comparable tmp = _objects[n];
					_objects[n] = _objects[_parent(n)];
					_objects[_parent(n)] = tmp;

					n = _parent(n);
				} else
					bool = true;
			}

		}

		_checkHeap();

	} // push()

	/**
	 * get minimum queue entry and remove it
	 *
	 * @return minimum queue entry if possible
	 * @exception NoSuchElementException
	 *                if queue is empty
	 */
	public Comparable pop() throws NoSuchElementException {
		Comparable tmp;
		if (this.isEmpty())
			throw new NoSuchElementException("Queue is empty!");
		else {
			tmp = _objects[0];
			_objects[0] = _objects[_lastIndex];
			_lastIndex--;
			// if (this._checkHeap() == false)
			this._heapify(0);
		}

		_checkHeap();

		return tmp;
	} // pop()

	/***
	 * conversion methods
	 ****************************************************/

	/**
	 * @return string representation of queue
	 */
	public String toString() {
		return toString(0);
	} // toString()

	public String toString(int n) {
		if (n < 0 || n > _lastIndex)
			return "";
		else {
			String str = "";

			str = toString(_right(n));
			int i = n;
			while (i > 0) {
				str += "   ";
				i = _parent(i);
			}
			str += _objects[n].toString() + "\n";

			str += toString(_left(n));
			return str;
		}
	}

	/***
	 * private methods
	 *******************************************************/

	/**
	 * private: calculate index of parent of given entry
	 *
	 * @param index
	 *            given index.
	 * @return index of parent
	 */
	private int _parent(int index) {
		if (index <= 0 || index > _lastIndex)
			return UNUSED;
		else if (index % 2 == 1) // index is a left son
			return (index - 1) / 2;
		else
			return (index - 2) / 2;
	}

	/**
	 * private: calculate index of left child of given entry
	 *
	 * @param index
	 *            given index
	 * @return index of left child
	 */
	private int _left(int index) {
		if (index < 0 || index >= _lastIndex)
			return UNUSED;
		else
			return index + index + 1;
	}

	/**
	 * private: calculate index of right child of given entry
	 *
	 * @param index
	 *            given index
	 * @return index of right child
	 */
	private int _right(int index) {
		if (index < 0 || index >= _lastIndex)
			return UNUSED;
		else
			return index + index + 2;
	}

	/**
	 * private: re-establish heap property from given node index downward. it is
	 * assumed that the subtrees under the children of node index form a
	 * consistent partial heap.
	 *
	 * @param index
	 *            given index from where to work
	 */
	private void _heapify(int index) {
		int child;
		Comparable temp;

		if (2 * index + 1 > _lastIndex)
			return; // no sons --> nothing to do
		if (2 * index + 2 > _lastIndex) // 2*index+1 is only child of index
		{
			child = 2 * index + 1;
		} else // 2 sons, determine smaller one
		{
			if (_objects[2 * index + 1].compareTo(_objects[2 * index + 2]) < 0) {
				child = 2 * index + 1;
			} else {
				child = 2 * index + 2;
			}
		} // endif
			// check if exchange is necessary
		if (_objects[index].compareTo(_objects[child]) > 0) {
			temp = _objects[index];
			_objects[index] = _objects[child];
			_objects[child] = temp;
			// recursive call for possible further exchanges
			_heapify(child);
		} // endif
	} // _heapify()

	/**
	 * create heap propery in queue
	 */
	private void _createHeap() {
		for (int i = _lastIndex; i >= 0; i--)
			_heapify(i);

		_checkHeap();
	}

	/***
	 * debugging methods
	 *****************************************************/

	/**
	 * private: check consistency of heap property in entire tree
	 *
	 * @return is heap property consistent in queue?
	 */
	private boolean _checkHeap() {
		if (!_checkMode) // no checking wanted
			return true;
		for (int i = _lastIndex; i > 0; i--)
			if (_objects[i].compareTo(_objects[_parent(i)]) < 0) {
				System.out.println("Hier ist ein Problem!!!");
				return false;
			}

		// if no inconsistencies were found, return TRUE
		return true;

	} // _checkHeap()

	/**
	 * Sorts a given array and return is sorted in increasing order
	 * 
	 * @param array
	 *            array of Comparable to be returned sorted in increasing order
	 * @return sorted array
	 */
	public static Comparable[] heapSort(Comparable[] array) {
		PriorityQueue pq = new PriorityQueue(array);

		for (int i = 0; i < array.length; i++)
			array[i] = (Integer) pq.pop();

		return array;
	}

	/***
	 * test methods
	 **********************************************************/

	/**
	 * test class by sorting random numbers
	 *
	 * @param argv
	 *            just argv[0] as length of heap
	 */
	public static void main(String[] args) {
		/*** output usage information ***/

		if (args.length == 0) {
			System.out.println("usage: java PriorityQueue <n>");
			System.out.println("       n random numbers are sorted by heapsort.");
			return;
		} else {
			int n = Integer.parseInt(args[0]);
			Random ran = new Random();
			Comparable[] array = new Integer[n];

			for (int i = 0; i < array.length; i++) {
				array[i] = (new Integer(ran.nextInt(n)));
				System.out.print(array[i] + " ");
			}

			System.out.println();

			array = heapSort(array);

			for (int i = 0; i < array.length; i++)
				System.out.print(array[i] + " ");

		}
	}
}