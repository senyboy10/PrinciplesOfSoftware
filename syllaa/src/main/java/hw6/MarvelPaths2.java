/*
	Name: Alseny Sylla
	RIN: 661409905
	RCSID: syllaa
*/
package hw6;

import hw4.*;
import java.io.*;
import java.util.*;
import hw5.MarvelParser;

public class MarvelPaths2 {
	//the container for the graph. Everything will be stored here. 
	Graph<String, Double> database = new Graph<String,Double>();


//-------------------------------------------------------------------------------
	public class Tuple implements Comparable<Tuple>{
		public ArrayList<String> direction_;
		public Double value_;
		
		/** 
		 * Constructs a new Tuple object
		 *
		 * @param first_ A list of String objects to be stored as the first element
		 * @param second_ A Double to be stored as the second element
         * @effects Constructs a new Tuple object
    	 */
		public Tuple(ArrayList<String> first_, Double second_)
		{
			direction_= first_;
			value_ = second_;
		}
		
		/**                                                                                             
	     * @param: t The other tuple to compare this Tuple object to
	     * @return: Integer that is a result of comparison between two doubles
	    */
		public int compareTo(Tuple t)
		{
			//return Double.compare(this.value_,value_);
			if(value_ > t.value_){return 1; }
			else if(t.value_> value_){return -1;}
			else{return 0;}
		}
	}
//_________________________________________________________________________________




//-----------------------------------------------------------------------------------
	/**                                                                                             
     * @param: filename The name of the file to be opened.
     * @modifies: this
	  *
     * @effects: Creates a new Graph object, with the characters as nodes and books as edge
     * 			 weights
    */

	public void createNewGraph(String filename)
	{
		Set<String> chars = new HashSet<String>(); 
		HashMap<String, Set<String>> charsInBooks = new HashMap<String, Set<String>>(); 

		try {
			MarvelParser.readData(filename,charsInBooks,chars);
			System.out.println("Read "+chars.size()+" characters who are in "+charsInBooks.keySet().size() +" books.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Figure out how many books every character shares with every other character
		HashMap<String, HashMap<String, Double>> share_helper = new HashMap<String, HashMap<String, Double>>();

			// Adds all characters as nodes to the graph
		for (String character : chars){
			database.addNode(character);
			share_helper.put(character, new HashMap<String, Double>());
		}


		for(HashMap.Entry<String, Set<String>> k : charsInBooks.entrySet()) {

			ArrayList<String> newCharBook = new ArrayList<String>(k.getValue());

			for(int i = 0; i < newCharBook.size(); ++i)
			{

				String char1 = newCharBook.get(i); 
				HashMap<String, Double> char1Costs = share_helper.get(char1); 

				for(int j = i + 1; j < newCharBook.size(); ++j)
				{

					String char2 = newCharBook.get(j); 
					HashMap<String, Double> char2Costs = share_helper.get(char2); 
					char1Costs.putIfAbsent(char2, 0.0);
					char2Costs.putIfAbsent(char1, 0.0); 
					char1Costs.put(char2, 1.0+char1Costs.get(char2)); 
					char2Costs.put(char1, 1.0+char2Costs.get(char1));
				}
			}
		} 

		//Now let's store everything in the graph
		double weight_;
		for(String entry : share_helper.keySet()){
			HashMap<String, Double> entry2 = share_helper.get(entry); 
			for(String ch2 : entry2.keySet()){
				Double divisor = (double)entry2.get(ch2); 
				weight_= 1.0/divisor;
				database.addEdge(entry, ch2, weight_); 
			}
		}

	}
//_______________________________________________________________________________________

	public String findPath(String firstNode_, String secondNode_){
		String start = firstNode_;
		String dest = secondNode_;
		PriorityQueue<Tuple> active = new PriorityQueue<Tuple>();
		Set<String> finished = new HashSet<String>();
		ArrayList<String> startToSelf = new ArrayList<String>();

		active.add(new Tuple(startToSelf, 0.0));

		String answer = "";
		boolean occurred = firstNode_.equals(secondNode_);

		if (occurred)
		{
			return  firstNode_ + " and " + secondNode_ + " are the same. \ntotal cost: 0.000\n";
		}

		if (!database.hasNode(start))
		{
			answer += "unknown character " + start + "\n";
			return answer;
			
		}
		if (!database.hasNode(dest) && !firstNode_.equals(dest))
		{
			answer += "unknown character " + dest + "\n";
			return answer;
			
		}
		
		
		answer += "path from " + start + " to " + dest + ":\n";


		while(!active.isEmpty())
		{
			Tuple thisPair = active.remove();
			
			String thisPair_end = thisPair.direction_.get(thisPair.direction_.size()-1);

			if(thisPair_end.equals(secondNode_)){
				String ret_val = "";
				if(thisPair.direction_.size()==1){
					ret_val += "total cost: 0.000\n";
				}
				else{
					for(int i =0; i < thisPair.direction_.size()-1; ++i){
						Double lb = database.getEdgeWeight(thisPair.direction_.get(i),thisPair.direction_.get(i+1));
						ret_val += thisPair.direction_.get(i) + " to "+ thisPair.direction_.get(i+1) + " with weight " + String.format("%.3f", lb) + "\n";
					}
					ret_val += String.format("total cost: %.3f\n", thisPair.value_);
				}

				return answer + ret_val;
			}

			if(finished.contains(thisPair_end)){
				continue;
			}


			Iterator<String> children_itr = database.ChildrenList(firstNode_); 
			while(children_itr.hasNext()){
				String child = children_itr.next(); 
				if(!finished.contains(child)){
					ArrayList<String> good_dir = new ArrayList<String>(thisPair.direction_);
					good_dir.add(child);
					double l_edge = database.getEdgeWeight(thisPair_end, child)+ thisPair.value_; 
					
					Tuple best = new Tuple(good_dir, l_edge); 
					active.add(best);
					
				}
			}
			finished.add(thisPair_end);
		}
		return answer + "\n";
	}



	public int getSize(){
		return database.getNumNodes();
	}

	public int getEdgeNum(){
		return database.getNumEdges(); 
	}


}