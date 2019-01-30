package hw7;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap; 

public class CampusPaths {

	public static void main(String[] args){
		CampusPathsModel cp = new CampusPathsModel();
		cp.createNewGraph("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");
		String menu = "Menu:\n"
				+ "b: lists all buildings\n"
				+ "r: prints directions for the shortest route between two buildings or their IDs\n"
				+ "q: quits the program\n"
				+ "m: prints a menu of all commands\n";

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		HashMap<String, String> BuildingIDs = cp.getname_id(); 
		HashMap<String, String> BuildingNames = cp.getbuilding();
		boolean valid = true;
		while(!input.equals("q"))
		{
			try
			{
				input = reader.readLine();
			}
			catch (IOException e)
			{
				break;
			}
			if(input.equals("b"))
			{
				TreeMap<String, String> mymap = new TreeMap<String, String>(cp.getname_id()); 
				for(Entry<String, String> entry : mymap.entrySet()){
					if(entry.getKey().equals("")){
						continue; 
					}
					System.out.println(entry.getKey() + "," + entry.getValue());
				}
			
			}
				
			else if(input.equals("r"))
			{
				
				String line1_;
				String line2_;
				
				

				System.out.print("First building id/name, followed by Enter: ");
				try
				{
					line1_ = reader.readLine();
				}
				catch (IOException badinput)
				{
					break;
				}
				System.out.print("Second building id/name, followed by Enter: ");
				try
				{
					line2_ = reader.readLine();
				}
				catch (IOException badinput)
				{
					break;
				}
			

				
				boolean firstBuildingExist, secondBuildingExist, isID1, isID2; 
				isID1 = BuildingNames.containsKey(line1_); 
				isID2 = BuildingNames.containsKey(line2_); 
				firstBuildingExist = BuildingIDs.containsKey(line1_);
				secondBuildingExist = BuildingIDs.containsKey(line2_); 
				
				if((isID1 && BuildingNames.get(line1_).equals("")) || !firstBuildingExist && !isID1){
					valid = false; 
					System.out.println("Unknown building: [" + line1_ + "]"); 
				}
				if(!line1_.equals(line2_) && (!secondBuildingExist && !isID2 || (isID2 && BuildingNames.get(line2_).equals("")))){
					valid = false; 
					System.out.println("Unknown building: [" + line2_ + "]");
				}
				
				if(valid){
					if(firstBuildingExist){
						line1_ = BuildingIDs.get(line1_); 
					}
					if(secondBuildingExist){
						line2_ = BuildingIDs.get(line2_); 
					}
					System.out.print(cp.findPath(line1_, line2_));
				}
				
			}
			else if(input.equals("q"))
				break;
			else if(input.equals("m"))
				System.out.print(menu);
			else
				System.out.print("Unknown option\n");
		}
		return;
	}
}
