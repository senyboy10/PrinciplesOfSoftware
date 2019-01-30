package hw7;

import java.util.*;
import java.io.*; 

public class CampusParsing {

	public CampusParsing(){


	}

//----------------------------------------------------------------------------------------------------------------------
	/**
	 * @param filename - file to parse 
	 * @param name_id- the map that stores the parsed pairs <name, id>
	 * @param id_coordinate - the map that stores the parsed pairs <id, coordinate> 
	 * @effects Fill in <name, id> pairs to name_id and <id, coordinate> to id_coordinate
	 * @throws IOException if the file can't be read or it is not a csv
	 */
	public  void readNodeData(String filename, HashMap<String, String> name_id, HashMap<String, ArrayList<Integer>> id_coordinate) throws IOException{
	
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(filename)); 

		String line_ = null; 

		while((line_ = reader.readLine()) != null){

			String[] resouces = line_.split(","); 

			 if (resouces.length != 4)  throw new IOException("File "+filename+" is not a CSV file.");
			
			String name = resouces[0]; 
			String id = resouces[1]; 

			name_id.put(id, name); 

			int  x_values = Integer.parseInt(resouces[2]); 
			int y_values = Integer.parseInt(resouces[3]); 
			
			ArrayList<Integer> x_y_coordinates = new ArrayList<Integer>(); 

			x_y_coordinates.add(x_values); 
			x_y_coordinates.add(y_values); 
			
			
			id_coordinate.put(id, x_y_coordinates); 
		
		}
		reader.close(); 
	}

//-----------------------------------------------------------------------------------------------------------------
	

//------------------------------------------------------------------------------------------------------------
	/**
	 * @param filename - file to parse. path to the csv
	 * @param e_id - the map to store parsed <id, id> pairs
	 * @effects - adds parsed<id, id> pairs to the map
	 * @throws IOException if the file cannot be read or the file is not a csv
	 */
	@SuppressWarnings("resource")
	public void readEdgeData(String filename, HashMap<String, HashSet<String>> e_id) throws IOException{
	
		BufferedReader reader = new BufferedReader(new FileReader(filename));

		String line_ = null; 

		while((line_ = reader.readLine()) != null)
		{

			int check_ = line_.indexOf(","); 

			if(check_ == -1){
				throw new IOException("File " +filename+ " is not a csv file. "); 
			}

			String id1 = line_.substring(0, check_); 
			String id2 = line_.substring(check_+1, line_.length()); 
			
			if(e_id.containsKey(id1)){
				e_id.get(id1).add(id2); 
			}
			else{
				HashSet<String> temp_set = new HashSet<String>(); 
				temp_set.add(id2); 
				e_id.put(id1, temp_set); 	
			}
		}
		reader.close();
	}
}
//------------------------------------------------------------------------------------------------------------