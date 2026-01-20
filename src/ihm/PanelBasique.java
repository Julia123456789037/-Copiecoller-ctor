import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;

public class PanelBasique extends JPanel implements ActionListener, ItemListener, ChangeListener
{

	private JButton bouton;
	private JButton boutonSupprimer1;
	private JButton boutonSupprimer2;

	private ButtonGroup btnGroup;

	private JPanel pnlCentre;
	private JPanel pnlHaut;
	private panelTexte panelG;
	private panelTexte panelD;

	private JFrame frame;
	
	private JSpinner spinnerMots;
	private int nbMotsCommun = 8;

	public PanelBasique( JFrame frame )
	{

		this.frame = frame;

		this.setLayout(new BorderLayout(10, 10));
		/*-------------------------------*/
		/* Création des Composants */
		/*-------------------------------*/

		// Panel haut avec le sélecteur de mots communs
		this.pnlHaut = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel labelMots = new JLabel("Nombre de mots commun:");
		SpinnerNumberModel model = new SpinnerNumberModel(8, 1, 100, 1);
		this.spinnerMots = new JSpinner(model);
		
		this.pnlHaut.add(labelMots);
		this.pnlHaut.add(this.spinnerMots);

		// Panel centre avec les deux panelTexte
		this.pnlCentre = new JPanel(new GridLayout(1, 2, 10, 10));

		this.panelG = new panelTexte(frame);
		this.panelD = new panelTexte(frame);

		this.pnlCentre.add(this.panelG);
		this.pnlCentre.add(this.panelD);

		// Bouton Comparer
		this.bouton = new JButton("Comparer");

		/*-------------------------------*/
		/* Positionnment des Composant */
		/*-------------------------------*/

		this.add(this.pnlHaut, BorderLayout.NORTH);
		this.add(this.pnlCentre, BorderLayout.CENTER);
		this.add(this.bouton, BorderLayout.SOUTH);

		/*-------------------------------*/
		/* Activation des Composants */
		/*-------------------------------*/

		this.spinnerMots.addChangeListener(this);
		this.bouton.addActionListener(this);

		this.setVisible(true);
	}


	public void stateChanged(ChangeEvent e)
	{
		if (e.getSource() == spinnerMots)
		{
			this.nbMotsCommun = (Integer) this.spinnerMots.getValue();
			this.panelG.setNbMotsCommun(this.nbMotsCommun);
			this.panelD.setNbMotsCommun(this.nbMotsCommun);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if ( e.getSource() == bouton)
		{
			System.out.println("Bouton Comparer cliqué");
			System.out.println("Nombre de mots commun : " + this.nbMotsCommun);
			//this.ctrl.comparer();
		}
	}

}
