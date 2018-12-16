package main.java.LinKern.comaList;

import java.util.NoSuchElementException;

public class Queue {
	/**
	 * private: linked list behind the queue
	 */
	private DoubleList _list;

	/**
	 * default constructor, initializes empty queue
	 */
	public Queue() {
		_list = new DoubleList();
	}

	/**
	 * @return is queue empty?
	 */
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	/**
	 * give object at head of queue without removing it
	 *
	 * @return object at head of queue
	 * @exception NoSuchElementException
	 *                if queue is empty
	 */
	public Object top() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("Stack is empty!");

		_list.resetToHead(); // reset to first list node

		return _list.currentData();

	} // top()

	/**
	 * insert object at tail of queue
	 * 
	 * @param obj
	 *            object to be inserted
	 */
	public void push(Object obj) {
		_list.insertAtTail(obj);
	}

	/**
	 * remove object at head of queue
	 * 
	 * @return topmost object on stack
	 * @exception NoSuchElementException
	 *                if stack is empty
	 */
	public Object pop() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("Stack is empty!");

		return _list.removeAtHead();

	}
}
