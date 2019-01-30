/*
  Name: Alseny Sylla
  RIN: 661409905
  RCSID: syllaa
*/
package hw4;

import java.util.*;
import hw4.Edge;  

public class Graph<K extends Comparable<K>, V extends Comparable<V>>{
	/*
	 * DIRECTED LABEL MULTI-GRAPH 
	 */
	
	//edges correspond to all the edges in the graph. 
	private HashSet<Edge<K, V>> edges; 
	
	//this container stores nodes with corresponding edges. 
	private HashMap<Node<K>, HashSet<Edge<K, V>>> nodes; 

//---------------------------------------------------------------------------------------
	/**
	 * @requires true
	 * @modifies this 
	 * @effects creates an empty graph 
	 */
	public Graph(){
		//These two variables are the main database of the graph. 
		//nodes = a hashmap whose key is  a node and value list of associated edges. 
		//edges = all the edges in the graph. 

		//Initialization. 
		nodes = new HashMap<Node<K>, HashSet<Edge<K, V>>>(); 
		edges = new HashSet<Edge<K, V>>(); 
	}
//---------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------
	/**
	 * @param newNode_, a string to convert to a Node to add to the graph. 
	 * @modifies this
	 * @requires newNode_ != null 
	 * @effects adds a new node in the graph. 
	 * @returns true if the node is added, and false otherwise.  
	 * @throws RuntimeException if newNode_ is already in the graph. 
	 */
	public boolean addNode(K newNode_){
		if(!nodes.containsKey(new Node<K>(newNode_))){
			nodes.put(new Node<K>(newNode_), new HashSet<Edge<K, V>>()); 
			return true;
		}
		return false;
	}
//---------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------
	/**
	 * @param firstNode_ the node the edge initiates from. 
	 * @param secondNode_: node the edge ends at. 
	 * @param edgeId_ , weight, name of the edge.  
	 * @requires requires all three arguments not be null 
	 * @modifies this 
	 * @effects adds an between the two nodes with a specified label.
	 * 			Also add to the list of edges for the graph. 
	 * 
	 * @returns true if the edge is added and false otherwise. 
	 */
	public boolean addEdge(K firstNode_, K secondNode_, V edgeId_){
		if( firstNode_== null|| secondNode_ == null || edgeId_ == null) {
			return false;
		}
		
		
		if(nodes.containsKey(new Node<K>(firstNode_)))
		{
			nodes.get(new Node<K>(firstNode_)).clear(); 
			boolean  added_to_edges = edges.add(new Edge<K, V>(firstNode_, secondNode_,edgeId_));

			boolean added_to_nodes = nodes.get(new Node<K>(firstNode_)).add(new Edge<K, V>(firstNode_, secondNode_, edgeId_)); 

			return  (added_to_edges && added_to_nodes);
			
		}
		return false;

		
	}


//----------------------------------------------------------------------------------------	



//---------------------------------------------------------------------------------------
	/**
	 * @requires true
	 * @returns iterator to a lexicographically sorted list of array nodes
	 * 
	 */
	public Iterator<K> NodeList()
	{

		//keyset() returns a set view of the keys contined in the nodes map. 
		ArrayList<Node<K>> allNodes = new ArrayList<Node<K>>(nodes.keySet()); 
		//sort the nodes. 
		Collections.sort(allNodes);
		
		//create an ArrayList<k> to store the copy of the nodes. 
		ArrayList<K> nodelist_ = new ArrayList<K>(allNodes.size()); 
		
		for (Node<K> i : allNodes){
			nodelist_.add(i.getNodeName()); 
		}
		
		return nodelist_.iterator();
	}
//__________________________________________________________________________________





//----------------------------------	
	public int getNumNodes(){
		return nodes.size(); 
	}
//__________________________________



//-----------------------------------
	public int getNumEdges(){return edges.size(); }
//___________________________________



//-------------------------------------------------------------------
	/**
	 * @requires n not to be null. 
	 * @param n node we are checking for
	 * @returns True if the node is in there and false otherwise.
	 */
	public boolean hasNode(K n){
		@SuppressWarnings("rawtypes")
		Node node_ = new Node<K>(n);
		return nodes.containsKey(node_); 
	}
//___________________________________________________________________	

	

//-------------------------------------------------------------------------------	 
	/**
	 * @requires start!= null, end != null 
	 * @param start, a value to use as the starting node and end, 
	 *			a value to use as ending node 
	 * @modifies none 
	 * @returns V if it exists, null if edge does not have a label 
	 * @throws RuntimeException if start or end are null 
	 */
	public V getEdgeWeight(K firstNode_, K secondNode_){

		Iterator<Edge<K,V>> itr = nodes.get(new Node<K>(firstNode_)).iterator(); 
		Edge<K, V> temp = itr.next(); 
		V val = temp.getEdgeId(); 
		for(Edge<K, V> e : nodes.get(new Node<K>(firstNode_))){
			if(e.getEdgeId().compareTo(val) < 1){
				val = e.getEdgeId(); 
			}
		}
		return val; 
	}
//_____________________________________________________________________________

	//specifications
	/**
	 * @param parentNode, node to get the children of 
	 * @effects returns a list of Iterator<K> 
	 * @returns Iterator<K> list of edges. 
	 */
	public Iterator<String> ChildrenList(K parentNode){
		
		


		ArrayList<String> children_ = new ArrayList<String>(); 
		ArrayList<Edge<K, V>> edg = new ArrayList<Edge<K,V>>(edges.size());
		for(Edge<K,V> e : edges){
			edg.add(e); 
		}
		Collections.sort(edg); 

		for(Edge<K,V> e : edg){
			if((e.getFirstNode().getNodeName()).equals(parentNode)){
				children_.add(e.getSecondNode().getNodeName() + "[" + e.getEdgeId() + "]"); 
			}
		}
		
		
		
		return children_.iterator(); 
		
	}
	
}
