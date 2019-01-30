/*
	Name: Alseny Sylla
	RIN: 661409905
	RCSID: syllaa

*/
		/*This class focus on the back end. */
	
package hw9;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import hw7.CampusPathsModel;

/**
* SimpleFrame does not represent an ADT
* Does not have representation invariants or abstraction functions
*/

@SuppressWarnings("serial")
class SimpleFrame extends JFrame implements ActionListener {

	private JPanel paneContents;
	@SuppressWarnings("rawtypes")
	private JComboBox startingPointBox;
	@SuppressWarnings("rawtypes")
	private JComboBox destinationBox;
	private JButton shortest_path;
	private JButton reset_button;
	private ImagePanel image_panel;
	CampusPathsModel cp;
	
		/**
    *@effects: Creates a SimpleFrame object
    *@modifies: cp, paneContents objects
    */
	public SimpleFrame(String map_title) throws IOException{
		
		super(map_title);
		setSize(1000,1000);
		cp= new CampusPathsModel();
		cp.createNewGraph("data/RPI_map_data_Nodes.csv", "data/RPI_map_data_Edges.csv");

		paneContents = new JPanel();
		this.showMap();
		this.showBuildingsDropDrownList();
		this.showShortestPathButton();
		paneContents.setLayout(new FlowLayout());
		this.add(paneContents);
		

	}

		 /**
	    *@effects: Creates/instantiates an ImagePanel object
	    *@modifies: image_panel,  paneContent
	    */
	public void showMap() throws IOException{
		image_panel = new ImagePanel();
		paneContents.add(image_panel, BorderLayout.NORTH);
	}

	
	public void showBuildingsDropDrownList() throws IOException{
		SortedSet<String> building_names = new TreeSet<String>();
		for (String id : cp.getname_id().keySet()){
			building_names.add(id);//add IDs as Nodes 
		}
		
		 String[] start_names = new String[building_names.size()];
	     start_names = building_names.toArray(start_names);
  		

  		  JPanel inner_loc = new JPanel();

		  startingPointBox = new JComboBox<Object>(start_names);
		  startingPointBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		  startingPointBox.setEditable(false);

	      destinationBox = new JComboBox<Object>(start_names);
	      destinationBox.setAlignmentX(Component.CENTER_ALIGNMENT);
	      destinationBox.setEditable(false);

		  inner_loc.add(startingPointBox);
		  inner_loc.add(destinationBox);

		  paneContents.add(inner_loc, BorderLayout.CENTER);
	}

	  /**
	  *@effects: startingBuildingBox item, destinationBox item
	  *@modifies: startingBuildingBox, destinationBox
	   */
	public void resetPath(){
		startingPointBox.setSelectedItem("133 Sunset Terrace");
		destinationBox.setSelectedItem("133 Sunset Terrace");
		image_panel.repaintLabel(this, null, null, false);
	}


	  /**
	  *@effects: shortest_path button, resetButton button, paneContent JPanel
	  *@modifies: shortest_path, reset_button, paneContent
	    */
	public void showShortestPathButton()
	{
		JPanel button_panel = new JPanel();

		shortest_path = new JButton("Display The Shortest Route");
		shortest_path.setAlignmentX(Component.CENTER_ALIGNMENT);
		shortest_path.addActionListener(this);

		reset_button = new JButton("Reset");
		reset_button.setAlignmentX(Component.BOTTOM_ALIGNMENT);
		reset_button.addActionListener(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					resetPath();
				}
			}
		);

		button_panel.add(shortest_path);
		button_panel.add(reset_button);

		paneContents.add(button_panel, BorderLayout.SOUTH);

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		findShortestPath();
	}

	public void findShortestPath(){
		// Choose Starting Point and destination
		String starting_point = (String) startingPointBox.getSelectedItem();
		String destination = (String) destinationBox.getSelectedItem();
		
	
		String starting_point_ID = cp.convertNameToID(starting_point);
		String destination_point_ID = cp.convertNameToID(destination);
		
		//This functions returns HashMap that has the name of building/Intersections on the path with their coordinates
		LinkedHashMap<String, ArrayList<Integer>> path_it = cp.read_directions(starting_point_ID, destination_point_ID);

		ArrayList<Integer> temp = new ArrayList<Integer>(cp.getId_coordinate().get(starting_point_ID));
		//allows me to get starting point and its coordinates
		LinkedHashMap<String, ArrayList<Integer>> building1 = new LinkedHashMap<String, ArrayList<Integer>>();
		building1.put(starting_point_ID, temp);
		
		if(path_it == null){
			LinkedHashMap<String, ArrayList<Integer>> tp = new LinkedHashMap<String, ArrayList<Integer>>();
			tp.put(destination_point_ID, cp.getId_coordinate().get(destination_point_ID));
			image_panel.repaintLabel(this, building1, tp, true);
		}

		image_panel.repaintLabel(this, building1, path_it, false);
	}


	
}

//----------------------Main Class-------------------------------


public class RPICampusPathsMain {
	public static void main(String[] args) throws IOException{
		SimpleFrame frame = new SimpleFrame("RPI Campus Navigation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
//-----------------End of Main Class----------------------------