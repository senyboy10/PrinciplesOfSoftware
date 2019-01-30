/*
	Name: Alseny Sylla
	RIN: 661409905
	RCSID: syllaa
*/


package hw4;

import java.util.Comparator; 

@SuppressWarnings("unused")
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
	
	
	
	 // Representation invariant: nodeName is not equal to NULL
	
	private T nodeName; //The name or label of the graph. 

//--------------------------------------------------------------------------------
	/**
	 * @param: name_ : initial label of the node. 
	 * @requires name_ != null 
	 * @modifies this 
	 * @effects returns a node with the value given. 
	 * @returns none
	 */
	public Node(T name_){  nodeName = name_; }
//--------------------------------------------------------------------------------

//---------------------------------------------------------------------------------
	public T getNodeName()  {  return nodeName;  }
	
	
	//@Override hashCode
	public int hashCode(){ return nodeName.hashCode(); }
	
	
	//@Override equals 
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj){
		if (obj instanceof Node)
		{
			
			return this.getNodeName().equals(((Node) obj).getNodeName()); 
		}
		else{ return false; }
	}
	
	//@Override 
	public int compareTo(Node<T> toCompare){
		return this.getNodeName().compareTo(toCompare.getNodeName()); 
	}
//----------------------------------------------------------------------------------
}
