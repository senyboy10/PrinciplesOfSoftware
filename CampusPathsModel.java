// Name: Alseny Sylla
// RIN: 661409905
//email: syllaa@rpi.edu

package hw7;
import java.lang.*;
import java.util.*;
import java.io.*;
import hw4.*;
import hw7.CampusParsing;

@SuppressWarnings("unused")
public class CampusPathsModel {
	

	private HashMap<String, String> building; 
	private HashMap<String, String> name_id; 

	private HashMap<String, ArrayList<Integer>> id_coordinate; 

	private ArrayList<String> valid_routes;

	Graph<String, Double> database;


//-------------------------------------------------------------     
	/**
	* Overview: Create a new graph from the given CSV file with the filename
	 * createNewGraph
	 * @param filename1- string of the node filename
	 * @param filename2 - string of the edge filename
	 * @effects -  will help us build graph with csv data.
	 * @modifies graph
	 * @throws RuntimeException if the file is in wrong format. 
	 */

	public void createNewGraph(String filename1, String filename2){
	
		this.building = new HashMap<String, String>(); 
		this.name_id = new HashMap<String, String>(); 
		this.id_coordinate = new HashMap<String, ArrayList<Integer>>();


		this.database = new Graph<String, Double>(); 

		HashMap<String, HashSet<String>> id_id = new HashMap<String, HashSet<String>>(); 

		try{
			CampusParsing parser = new CampusParsing(); 
			parser.readNodeData(filename1, building, this.id_coordinate);
			parser.readEdgeData(filename2, id_id);
		}
		catch (IOException e){
			e.printStackTrace(); 
		}
	
		for(Map.Entry<String, String> mapentry : building.entrySet()){
			database.addNode(mapentry.getKey());
			name_id.put(mapentry.getValue(), mapentry.getKey()); 
		}


		
		for(Map.Entry<String, HashSet<String>> mapentry : id_id.entrySet()){
			String position1 = mapentry.getKey();
			for(String position2 : mapentry.getValue()){
				if(!position1.equals(position2)){
					
									
					double distance = Math.sqrt(((id_coordinate.get(position1).get(0) - id_coordinate.get(position2).get(0))*
										         (id_coordinate.get(position1).get(0) - id_coordinate.get(position2).get(0)) +
										         (id_coordinate.get(position1).get(1) - id_coordinate.get(position2).get(1)) *
										         (id_coordinate.get(position1).get(1) - id_coordinate.get(position2).get(1))));
					database.addEdge(position1, position2, distance);
					database.addEdge(position2, position1, distance);
				}
				
			}
		
		}

		
	}
//------------------------------------------------------------
	
	 

//--------------------------------------------------------------    
	/**
	 * @param end_y y axis for end coordinate
	 * @param start_y  y axis for start coordinate
	 * @param end_x x axis for end coordinate
	 * @Param start_x  x axis for start coordinate
	 * @return a string which is the angle between the two coordinates. 
	 */
	private static String getDirection(double end_y, double start_y, double end_x, double start_x)
	{
		double value_y = end_y - start_y;

		double value_x = end_x - start_x;

		double angle = Math.atan2(value_y, value_x);  


		if(angle >= -1.962 && angle < -1.179){
			return "North";
		}
		else if(angle < 0.391 && angle >= -0.391){
			return "East"; 
		}
		else if(angle <= 1.962 && angle > 1.179){
			return "South"; 
		}
		else if(angle >= 2.749 || angle <= -2.749){
			return "West"; 
		}
		else if(angle < -0.391 && angle >= -1.179){
			return "NorthEast"; 
		}
		else if(angle > 0.391 && angle <= 1.179){
			return "SouthEast";
		}
		else if(angle > 1.962 && angle <= 2.749){
			return "SouthWest"; 
		}
		else if(angle >= -2.749 && angle < -1.962){
			return "NorthWest"; 
		}
		return null; 
	}

//--------------------------------------------------------------



//--------------------------------------------------------------	
	/**
	 * @returns HashMap<String, String>
	 */
	public HashMap<String, String> getname_id(){
		return new HashMap<String, String>(name_id);
	}
//--------------------------------------------------------------


	public HashMap<String, ArrayList<Integer>> getId_coordinate(){
		return new HashMap<String,ArrayList<Integer> >(id_coordinate);
	}

	public ArrayList<String> getValidRoutes(){
		return new ArrayList<String>( valid_routes);
	}


//--------------------------------------------------------------	
	/**
	 * @returns HashMap<String, String>
	 */
	public HashMap<String, String> getbuilding(){
		return new HashMap<String, String>(building); 
	}
//---------------------------------------------------------------	
	
//
	public String convertNameToID(String b) {
		//System.out.println(building);
		return name_id.get(b);
	}

	public LinkedHashMap<String, ArrayList<Integer>> read_directions (String node1, String node2){
		String temp = findPath(node1, node2);
		if(valid_routes==null) {
			return null;
		}
		LinkedHashMap<String, ArrayList<Integer>> answer = new LinkedHashMap<String, ArrayList<Integer>>();
		for(String n: valid_routes){
			String temp1 = n;
			String temp2 = temp1;
			answer.put(temp1, id_coordinate.get(temp2));
			
		}
		return answer;
	}

	/**
	 * Helps us compare nodes. 
	 */
	private class NodeComparator implements Comparator<String>{
		private HashMap<String, Double> assign; 
		public NodeComparator(HashMap<String, Double> initial){
			assign = initial;
		}
		/*
		 * @Override
		 */
		public int compare(String entry1, String entry2){
			if(assign.containsKey(entry1) && assign.containsKey(entry2)){
				return Double.compare(assign.get(entry1), assign.get(entry2));
				
			}
			else{

			 	throw new RuntimeException("a key is missing."); 
			}
		}
	}



//---------------------------------------------------------------------------------	
	/* 
		Helper function for finding the path. It pretty much takes care of the entire 
		algorithm for findthing the best path,.
		it returns the string that contains the direction. 
	*/
	public String djisktraPath(PriorityQueue<String> mypriorityQueue, 
							   HashMap<String, Double> buildingLengthFromOrigin,
							   String origin_location, String end_location){

		

		HashMap<String, String> trajectory = new HashMap<String, String>(); 

		
		String nextBuilding;
		String nextBuilding_length; 

		while(!mypriorityQueue.isEmpty())
		{
			String curr = mypriorityQueue.remove(); 

			Iterator<String> neighbors = database.ChildrenList(curr); 

			while(neighbors.hasNext())
			{
				String temp_building = neighbors.next(); 

				int split = temp_building.indexOf('['); 

				//Get the name first after removing the bracket. 
				nextBuilding = temp_building.substring(0, split); 

				nextBuilding_length = temp_building.substring(split+1, temp_building.length()-1); 

				Double convert_length_double = Double.parseDouble(nextBuilding_length); 

				Double better_path = buildingLengthFromOrigin.get(curr) + convert_length_double; 

				if(better_path < buildingLengthFromOrigin.get(nextBuilding) && buildingLengthFromOrigin.get(nextBuilding) != null)
				{
					buildingLengthFromOrigin.put(nextBuilding, better_path); 
					mypriorityQueue.remove(nextBuilding); 
					mypriorityQueue.add(nextBuilding); 
					trajectory.put(nextBuilding, curr + ":" + nextBuilding_length); 
				}
			}	
		}
		String roadInstruction = "";
		String variable1, linker = end_location , direction, nextLocation; 
		Double temporaryDistance = 0.0; 
		double cost = 0.0;
		boolean thereIsPath = true;
		ArrayList<String> temp_valid_links = new ArrayList<String>();
		while(!linker.equals(origin_location))
		{
			variable1 = trajectory.get(linker); 
			if(variable1 == null){
				thereIsPath = false; 
				break; 
			}
			
			int delimiter = variable1.indexOf(":"); 
			nextBuilding = variable1.substring(0, delimiter); 
			nextBuilding_length = variable1.substring(delimiter+1, variable1.length()); 

			temporaryDistance = Double.parseDouble(nextBuilding_length); 

			double d2_y = id_coordinate.get(linker).get(1);
			double d1_y = id_coordinate.get(nextBuilding).get(1);
			double d2_x = id_coordinate.get(linker).get(0);
			double d1_x = id_coordinate.get(nextBuilding).get(0);
			direction = getDirection(d2_y, d1_y, d2_x , d1_x );

			nextLocation = building.get(linker); 

			if(nextLocation.equals(""))
			{
				nextLocation = "Intersection " + linker; 
			}

			roadInstruction = "\tWalk " + direction + " to (" + nextLocation + ")\n" + roadInstruction; 
			temp_valid_links.add(linker);
			linker = nextBuilding; 
			cost += temporaryDistance; 
		}
		if(thereIsPath && roadInstruction.length() != 0)
		{
			roadInstruction = "Path from " + building.get(origin_location) + " to " + building.get(end_location) + ":\n" + roadInstruction;
			roadInstruction = roadInstruction + String.format("Total distance: %.3f pixel units.\n", cost); 
			 Collections.reverse(temp_valid_links);
			valid_routes = new ArrayList<String>(temp_valid_links);
		}
		else{
			
			roadInstruction = "There is no path from " + building.get(origin_location) + " to " + building.get(end_location) + ".\n"; 
		}
		return roadInstruction; 

	}
//---------------------------------------------------------------------------------
	

//-----------------------------------------------------------------------------------------

	/**
	 * @param origin_location name of the first node 
	 * @param end_location, name of the second node. 
	 * @returns a path from origin_location to endLocation if there is possible path. 
	 */
	public String findPath(String origin_location, String end_location){


		//SAME LOCATION (taking care of first case)
		if(origin_location.equals(end_location)){
			return "Path from " + building.get(origin_location) + " to " + building.get(end_location) + ":\n"
			+ "Total distance: 0.000 pixel units.\n";
		}

		Iterator<String> campusBuildings = database.NodeList(); 

		HashMap<String, Double> buildingLengthFromOrigin = new HashMap<String, Double>();
		Comparator<String> comp =  new NodeComparator( buildingLengthFromOrigin) ;
		PriorityQueue<String> mypriorityQueue = new PriorityQueue<String>(10, comp);
												
												 
		
		//add other building store some values for their distance
		while(campusBuildings.hasNext())
		{
			String temp1 = campusBuildings.next();
			String temp2 = temp1;
			buildingLengthFromOrigin.put(temp1, Double.POSITIVE_INFINITY); 
			mypriorityQueue.add(temp2);

		}

		//start with distance from initial location is 0
		buildingLengthFromOrigin.put(origin_location, 0.00);
	

		return djisktraPath(mypriorityQueue, buildingLengthFromOrigin, origin_location, end_location);
	
	
	}
	
//----------------------------------------------------------------------------------------




	
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args){
		
		CampusPathsModel Rpi = new CampusPathsModel(); 
		Rpi.createNewGraph("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");
		
		//System.out.println(Rpi.findPath("76", "52"));
		//System.out.println(Rpi.read_directions("76", "76"));
		/*Iterator it = Rpi.getname_id().entrySet().iterator();
  		 while (it.hasNext()) {
       Map.Entry pair = (Map.Entry)it.next();
       System.out.println(pair.getKey() + " = " + pair.getValue()+"\n");
       //it.remove(); // avoids a ConcurrentModificationException
*/   	//}

	}
}
