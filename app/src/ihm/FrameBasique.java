package app.src.ihm;

import javax.swing.*;

import app.src.Controleur;

import java.awt.BorderLayout;

import java.awt.Color;

public class FrameBasique extends JFrame
{

	private PanelBasique panelBasique;
	private Controleur ctrl;

	public FrameBasique(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle("CopieColler'ctor");

		this.setLocation(50, 50);

		this.setSize(1250, 850);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout(10, 0));

		// this.setLayout( new GridLayout( 4, 3, 10, 10));

		this.setBackground(Color.BLUE);

		/*------------------------------*/
		/* Création des Panels */
		/*------------------------------*/

		this.panelBasique = new PanelBasique(this, ctrl);

		/*------------------------------*/
		/* Positionnment des panels */
		/*------------------------------*/

		this.add(this.panelBasique);

		this.setVisible(true);
	}

	public void comparer()
	{
		this.ctrl.annalysePlagiat( this.panelBasique.getTexteG(), this.panelBasique.getTexteD(),
				this.panelBasique.getNbMotsCommun());
	}

}