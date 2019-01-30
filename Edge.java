/*
	Name: Alseny Sylla
	RIN: 661409905
	RCS: syllaa
*/


package hw4; 

public class Edge<T extends Comparable <T>, V extends Comparable <V>> implements Comparable<Edge<T,V>> {

	/*
		Every Edge needs a starting node and ending node. 
		firstNode = is the starting node. 
		secondNode = is the ending node. 
	*/
	private Node<T> firstNode, secondNode; 
	private V edgeId; 
	
//----------------------------------------------------------------------
	/**
	 * @param String firstNode, this will be the firstNode node 
	 * @param String secondNode, this will be the end node. 
	 * @param String edgeId_, weight or value of the edge. 
	 * @modifies this 
	 * @returns Edge 
	 */
	public Edge(T firstNode_, T secondNode_, V edgeId_){
		firstNode = new Node<T>(firstNode_); 
		secondNode = new Node<T>(secondNode_); 
		edgeId = edgeId_;  
	}
//--------------------------------------------------------------------------	
	

//--------------------------------------------------------------------------
	/**
	 * @effects returns a reference to the starting node
	 * @returns Node 
	 */
	public Node<T> getFirstNode(){
		//return a copy of the starting node. 
		return new Node<T>(firstNode.getNodeName());  
	}


	/**
	 * @effects returns a reference to ending node. 
	 * @returns Node 
	 */
	public Node<T> getSecondNode(){
		 //return a copy of end Node
		return new Node<T>(secondNode.getNodeName());
	}

	/**
	 * @effects returns the edgeId 
	 * @returns String
	 */
	public V getEdgeId(){
		return edgeId; 
	}

//--------------------------------------------------------------------------
	
	/*
	 * this is a comparator for edges. 
	 */
	public int compareTo(Edge<T,V> tempEdge){
		if(firstNode.equals(tempEdge.getFirstNode())){
			return edgeId.compareTo(tempEdge.getEdgeId()); 
		}
		else{
			return firstNode.compareTo(tempEdge.getFirstNode()); 
		}
	}
	
	public void modifyEdgeId(V id_){
		edgeId = id_; 
	}
//-----------------------------------------------------------------------
}
