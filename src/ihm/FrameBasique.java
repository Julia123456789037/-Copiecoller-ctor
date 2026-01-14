import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.Color;

//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;

public class FrameBasique extends JFrame
{

	private PanelBasique panelBasique;

	public FrameBasique()
	{
		this.setTitle("CopieColler'ctor");

		this.setLocation(50, 50);

		this.setSize(500, 250);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout(10, 0));

		// this.setLayout( new GridLayout( 4, 3, 10, 10));

		this.setBackground(Color.BLUE);

		/*------------------------------*/
		/* Création des Panels */
		/*------------------------------*/

		this.panelBasique = new PanelBasique();

		/*------------------------------*/
		/* Positionnment des panels */
		/*------------------------------*/

		this.add(this.panelBasique);

		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		new FrameBasique();
	}

}