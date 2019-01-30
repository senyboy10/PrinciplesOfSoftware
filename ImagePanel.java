/*
	Name: Alseny Sylla
	RIN: 661409905
	RCSID: syllaa

*/

package hw9;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


/* As mentioned on the hw instruction, the class ImagePanel does not represent an ADT
   Representation invariants and abstraction functions are not required*/

@SuppressWarnings("serial")
public class ImagePanel extends JPanel{
	private ImageLabel image_label;
	private BufferedImage final_img;


	public ImagePanel() throws IOException{
		@SuppressWarnings("unused")
		BufferedImage curr_img = ImageIO.read(new File ("data/RPI_campus_map_2010_extra_nodes_edges.png"));
		ImageIcon icon  = new ImageIcon("data/RPI_campus_map_2010_extra_nodes_edges.png");
		Image image = icon.getImage();
		image = image.getScaledInstance((int)(image.getWidth(null) * 0.35), (int)(image.getHeight(null)* 0.35), Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);

		final_img = new BufferedImage((int)(2300 * 0.35), (int)(2000*0.35), BufferedImage.TYPE_INT_ARGB);

		final_img.createGraphics();

		final_img.getGraphics().drawImage(image, 0, 0, final_img.getWidth(), final_img.getHeight(),
											0, 0, image.getWidth(null), image.getHeight(null), null);

		image_label = new ImageLabel(icon);
		image_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(image_label);
	}


	 /**
	  *@param: frame: JFrame s
	  *@param: first_build: LinkedHashMap<String, ArrayList<Integer>>
	  *@param: buildings: LinkedHashMap<String, ArrayList<Integer>>
	 * @param: no_path_found: boolean value
	  *@requires: frame!=null
	    */
	

	public void repaintLabel(JFrame frame, LinkedHashMap<String, ArrayList<Integer>> first_build, 
											LinkedHashMap<String, ArrayList<Integer>> buildings, boolean no_path_found){
		image_label.repaintLabel(frame, first_build, buildings, no_path_found, final_img);
	}


}
