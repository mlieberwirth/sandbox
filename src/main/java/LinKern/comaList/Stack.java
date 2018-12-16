package main.java.LinKern.comaList ;

import java.util.EmptyStackException;

public class Stack
{
    /**
     * private: linked list behind the stack
     */
    private LinkedList _list ;


    /**
     * default constructor, initializes empty stack
     */
    public Stack ( )
    {
        _list = new LinkedList() ;
    }


    /**
     * @return is stack empty?
     */ 
    public boolean isEmpty ( )
    {
        return _list.isEmpty() ;
    }


    /**
     * give topmost object on stack without removing it
     *
     * @return topmost object on stack
     * @exception EmptyStackException if stack is empty
     */ 
    public Object top ( )
        throws EmptyStackException
    {
	if ( isEmpty() )
	    throw new EmptyStackException() ;

	_list.reset() ;                             // reset to first list node

        return _list.currentData() ;

    }  // top()


    /**
     * insert object at top of stack
     * 
     * @param obj object to be inserted
     */ 
    public void push ( Object obj )
    {
	_list.insert( obj ) ;
    }


    /**
     * remove topmost object from stack
     * 
     * @return    topmost object on stack
     * @exception EmptyStackException if stack is empty
     */	
    public Object pop ( )
        throws EmptyStackException
    {
	if ( isEmpty() )
	    throw new EmptyStackException() ;

	return _list.removeFirst() ;

    }
}
