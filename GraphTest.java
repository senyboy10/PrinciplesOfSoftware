package hw4;

import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.*;

/**
 * @author Alseny Sylla
 * 
 * This class contains the test cases which will test to see if the implementation of the
 * graph class is correct.
 */

@SuppressWarnings("unused")
public class GraphTest{
	//convenient way to make a node
	private Node<String> node(String n) {
		return new Node<String>(n);
	}
	//convenient way for Zero Graph
	private Graph<String,String> graph() {
		return new Graph<String,String>();
	}
	
	Node<String> testingNode1 = node("A");
	Node<String> testingNode2 = node("B");
	Edge<String,String> e1 = new Edge<String, String>(testingNode1.getNodeName(), testingNode2.getNodeName(), "A-B edge");
	Edge<String,String> e2 = new Edge<String, String>(testingNode2.getNodeName(), testingNode1.getNodeName(), "B-A edge");
	
	Graph<String, String> testGraph;
	
	private Graph<String, String> zeroGraph = new Graph<String,String>();
	private Graph<String, String> oneNodeGraph = new Graph<String, String>();
	private Graph<String, String> twoNodeGraph = new Graph<String, String>();
	private Graph<String, String> oneNodeGraphOneEdgeGraph = new Graph<String, String>();
	private Graph<String, String> twoNodeGraphOneEdgeGraph = new Graph<String, String>();
	private Graph<String, String> twoNodeGraphTwoEdgeGraph = new Graph<String, String>();
	
	@Before
	public void setup() {
		testGraph = new Graph<String,String>();
		
		oneNodeGraph.addNode(testingNode1.getNodeName());
		oneNodeGraphOneEdgeGraph.addNode(testingNode1.getNodeName());
		
		twoNodeGraph.addNode(testingNode1.getNodeName());
		twoNodeGraph.addNode(testingNode2.getNodeName());
		
		twoNodeGraphOneEdgeGraph.addNode(testingNode1.getNodeName());
		twoNodeGraphOneEdgeGraph.addNode(testingNode2.getNodeName());
		twoNodeGraphOneEdgeGraph.addEdge("A", "B", "A-B edge");
		
		twoNodeGraphTwoEdgeGraph.addNode(testingNode1.getNodeName());
		twoNodeGraphTwoEdgeGraph.addNode(testingNode2.getNodeName());
		twoNodeGraphTwoEdgeGraph.addEdge("A", "B", "A-B edge");
		twoNodeGraphTwoEdgeGraph.addEdge("B", "A", "B-A edge");
	}
	
	//Constructor Tests
	@Test
	public void testGraphConstructor() {
		graph();
		assert(testGraph != null);
	}
	
	@Test
	public void testNodeConstructor() {
		assertTrue(testingNode1.getNodeName() != null);
		assertTrue(testingNode2.getNodeName() != null);
	}
	
	@Test
	public void testEdgeConstructor() {
		assertTrue(testingNode1.equals(e1.getFirstNode()));
		assertTrue(testingNode2.equals(e1.getSecondNode()));
		assertEquals("B-A edge", e2.getEdgeId());
		assertFalse(testingNode1.equals(null));
	}
	//Testing Node member functions
	@Test
	public void testGetNodeName() {
		assertEquals("These Nodes are equal", "A", testingNode1.getNodeName());
		assertEquals("These Nodes are equal", "B", testingNode2.getNodeName());
	}

	@Test
	public void testNodeEqaulity() {
		Node<String> temptestingNode1 = node("A");
		Node<String> temptestingNode2 = node("B");
		Node<String> tempN3 = node("C");
		Node<String> tempN4 = node(null);
		
		assertTrue(temptestingNode1.equals(testingNode1));
		assertTrue(temptestingNode2.equals(testingNode2));
		assertFalse(tempN3.equals(testingNode1));
		assertFalse(tempN4.equals(null));
	}

	@Test
	public void testAddNode() {
		Graph<String, String> testGraph = graph();
		String nullTester = null;
		
		assertTrue(testGraph.addNode(testingNode1.getNodeName()));
		assertTrue(testGraph.addNode(testingNode2.getNodeName()));
		assertTrue(testGraph.addNode("C"));
		
		assertTrue(testGraph.hasNode(testingNode1.getNodeName()));
		
		assertFalse(testGraph.addNode(testingNode1.getNodeName()));
		assertFalse(testGraph.addNode(testingNode2.getNodeName()));
		
	}

	@Test
	public void testAddEdge() {
		Graph<String, String> testGraph = graph();
		Node<String> testNode = node("C");
		Node<String> testNode1 = node("D");
		Node<String> testNode2 = node("E");
		
		assertTrue(testGraph.addNode(testingNode1.getNodeName()));
		assertTrue(testGraph.addNode(testingNode2.getNodeName()));
		
		assertTrue(testGraph.addEdge(testingNode1.getNodeName(), testingNode2.getNodeName(), "A-B edge"));
		assertTrue(testGraph.addEdge(testingNode2.getNodeName(), testingNode1.getNodeName(), "B-A edge"));
		assertTrue(testGraph.addEdge(testingNode1.getNodeName(), testNode.getNodeName(), "A-C edge"));
		assertTrue(testGraph.addEdge(testingNode1.getNodeName(), testNode1.getNodeName(), "A-D edge"));
		
		
		assertFalse(testGraph.addEdge(null,null,null));
		
	}
	
}