/*
	Name: Alseny Sylla
	RIN: 661409905
	RCSID: syllaa

*/

package hw9;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class ImageLabel extends JLabel {

	private boolean eventOn;
	private LinkedHashMap<String, ArrayList<Integer>> first;
	private LinkedHashMap<String, ArrayList<Integer>> validBuildings;
	private boolean no_path;
	public static final int OVAL_SIZE = 40;

	 /**
	 *@param: icon: ImageIcon
	 *@effects: Instantiates a new ImageLabel object (this)
	    */
	public ImageLabel(ImageIcon icon){
		super(icon);
		eventOn = false;
	}

	 /**
	 *@param: frame: JFrame
	 *@param: first_build: LinkedHashMap<String, ArrayList<Integer>>. firstBuilding and its coord.
	 *@param: buildings: LinkedHashMap<String, ArrayList<Integer>> all the buildings with coords. 
	 *@param: no_path_found: boolean value
	 *@param: img: BufferedImage object
	 *@requires: frame != null && img !=null
	 *@effects: eventOn, first, builds, no_path
	 *@modifies: eventOn, first, builds, no_path
	    */
	public void repaintLabel(JFrame frame, LinkedHashMap<String, ArrayList<Integer>> first_build,
							 LinkedHashMap<String, ArrayList<Integer>> buildings, boolean no_path_found, BufferedImage img){
		eventOn = true;
		first = first_build;
		validBuildings = buildings;
		no_path = no_path_found;
		frame.repaint();

	}


	/**
	 *@param: g: Graphics object
	 *@effects: this (the image)
	 *@modifies: this (the image)
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);


			Graphics2D g2d = (Graphics2D) g.create();
			g2d.scale(0.35, 0.35);

			if(no_path){
				g2d.setColor(Color.black); 
	        	g2d.setStroke(new BasicStroke(5));
	        	int x_axis = first.get((first.keySet().toArray())[0]).get(0);
				int y_axis = first.get((first.keySet().toArray())[0]).get(1);
				int endpoint_x_axis = validBuildings.get((validBuildings.keySet().toArray())[0]).get(0);
				int endpoint_y_axis = validBuildings.get((validBuildings.keySet().toArray())[0]).get(1);
	        	g2d.drawOval(x_axis- OVAL_SIZE/2, y_axis- OVAL_SIZE/2, OVAL_SIZE, OVAL_SIZE);
	        	g2d.drawOval(endpoint_x_axis - OVAL_SIZE/2, endpoint_y_axis- OVAL_SIZE/2, OVAL_SIZE, OVAL_SIZE);
			}

			if(eventOn && first != null){
				g2d.setColor(Color.black);
				g2d.setStroke(new BasicStroke(5));
				int x_axis = first.get((first.keySet().toArray())[0]).get(0);
				int y_axis = first.get((first.keySet().toArray())[0]).get(1);
				g2d.drawOval(x_axis - OVAL_SIZE/2, y_axis - OVAL_SIZE/2, OVAL_SIZE, OVAL_SIZE);

				if(validBuildings!= null){
					for(ArrayList<Integer> value: validBuildings.values()){
						g2d.setStroke(new BasicStroke(5));
						g2d.drawLine(x_axis, y_axis, value.get(0),value.get(1));
						g2d.drawOval(value.get(0)-OVAL_SIZE/2, value.get(1)-OVAL_SIZE/2, OVAL_SIZE, OVAL_SIZE);
						x_axis = value.get(0);
						y_axis= value.get(1);

					}
				}
				eventOn = false;
				
			}


	}

}
