package hw6;


import hw6.MarvelPaths2;

import java.util.*;
import org.junit.*;

import static org.junit.Assert.*; 

public class MarvelPaths2Test {
	@Test
	public void makeEmptyTest(){
		MarvelPaths2 databaseTest = new MarvelPaths2(); 
		databaseTest.createNewGraph("data/empty.csv");
	}
	
	@Test 
	public void nonExistentTest(){
		MarvelPaths2 databaseTest = new MarvelPaths2(); 
		databaseTest.createNewGraph("data/....csv");
	}
	
	@Test
	public void makeMarvelTest(){
		MarvelPaths2 databaseTest = new MarvelPaths2(); 
		databaseTest.createNewGraph("data/marvel.csv");
		assertEquals(6444, databaseTest.getSize()); 
		assertEquals(334414, databaseTest.getEdgeNum());
	}
	@Test 
	public void randomTest(){
		MarvelPaths2 databaseTest = new MarvelPaths2(); 
		databaseTest.createNewGraph("data/test1.csv");
		String false_a = databaseTest.findPath("a", "alseny");
		assertEquals(false_a, "unknown character a\n"); 
		
	}
	
	
	
	@Test
	public void sameTest(){
		MarvelPaths2 databaseTest = new MarvelPaths2();
		databaseTest.createNewGraph("data/test1.csv");
		String no_path = databaseTest.findPath("alseny", "alseny"); 
		assertEquals("alseny and alseny are the same. \ntotal cost: 0.000\n",no_path); 
	}
	
	
	
	@Test
	public void CheckPath(){
		ArrayList<String> test = new ArrayList<String>(); 
		test.add("hello"); 
		ArrayList<String> test2 = new ArrayList<String>();
		test2.add("from the other side"); 
		MarvelPaths2 temp = new MarvelPaths2(); 
		MarvelPaths2.Tuple p = temp.new Tuple(test, 10.0); 
		MarvelPaths2.Tuple q = temp.new Tuple(test2, (double) 11);
		int comparer = p.compareTo(q); 
		assertEquals(-1, comparer); 
		MarvelPaths2.Tuple z = temp.new Tuple(test, 10.0);
		assertEquals(0, z.compareTo(p)); 
	}
	
	
	
}
