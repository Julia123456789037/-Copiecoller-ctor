package app.src.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.Color;
import java.awt.Image;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import app.src.Controleur;
import app.src.metier.Plagiat;

import javax.swing.*;

public class PanelBasique extends JPanel implements ActionListener, ChangeListener
{

	private JButton bouton;

	private JPanel pnlCentre;
	private JPanel pnlHaut;
	private panelTexte panelG;
	private panelTexte panelD;
	private Controleur ctrl;

	private JSpinner spinnerMots;
	private int nbMotsCommun = 8;
	private JButton boutonCouleur;
	private Color couleurSurlignage = Color.RED;

	public PanelBasique(FrameBasique frame, Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setLayout(new BorderLayout(10, 10));
		/*-------------------------------*/
		/* Création des Composants */
		/*-------------------------------*/

		// Panel haut avec le sélecteur de mots communs
		this.pnlHaut = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel labelMots = new JLabel("Nombre de mots commun:");
		SpinnerNumberModel model = new SpinnerNumberModel(8, 1, 100, 1);
		this.spinnerMots = new JSpinner(model);

		// Bouton pour changer la couleur du surlignage
		this.boutonCouleur = new JButton();
		ImageIcon iconSurligner = new ImageIcon("../bin/ressource/surligner.png");
		Image imgSurligner = iconSurligner.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		this.boutonCouleur.setIcon(new ImageIcon(imgSurligner));
		this.boutonCouleur.setBackground(Color.RED);
		this.boutonCouleur.setForeground(Color.WHITE);
		this.boutonCouleur.setOpaque(true);

		this.pnlHaut.add(labelMots);
		this.pnlHaut.add(this.spinnerMots);
		this.pnlHaut.add(this.boutonCouleur);

		// Panel centre avec les deux panelTexte
		this.pnlCentre = new JPanel(new GridLayout(1, 2, 10, 10));

		this.panelG = new panelTexte(frame);
		this.panelD = new panelTexte(frame);

		this.pnlCentre.add(this.panelG);
		this.pnlCentre.add(this.panelD);

		// Bouton Comparer
		this.bouton = new JButton("Comparer");

		/*-------------------------------*/
		/*  Positionnment des Composant  */
		/*-------------------------------*/

		this.add(this.pnlHaut, BorderLayout.NORTH);
		this.add(this.pnlCentre, BorderLayout.CENTER);

		JPanel panelComparer = new JPanel();
		panelComparer.add(this.bouton);
		this.add(panelComparer, BorderLayout.SOUTH);

		/*-------------------------------*/
		/*   Activation des Composants   */
		/*-------------------------------*/

		this.spinnerMots.addChangeListener(this);
		this.bouton.addActionListener(this);
		this.boutonCouleur.addActionListener(this);

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
		if (e.getSource() == bouton)
		{
			Highlighter hG = this.panelG.getJTextArea().getHighlighter();
			Highlighter hD = this.panelD.getJTextArea().getHighlighter();
			hG.removeAllHighlights();
			hD.removeAllHighlights();

			System.out.println("Bouton Comparer cliqué");
			System.out.println("Nombre de mots commun : " + this.nbMotsCommun);

			ArrayList<Plagiat> lstPlagia = this.ctrl.annalysePlagiat(this.panelG.getTextArea(), this.panelD.getTextArea(), nbMotsCommun);

			System.out.println(lstPlagia);

			try {
				for (Plagiat p : lstPlagia) {
					int startT1     = p.getIndiceDebutT1(); // Index du début
					int endT1       = p.getIndiceFinT1();   // Index de fin
					int startT2     = p.getIndiceDebutT2(); // Index du début
					int endT2       = p.getIndiceFinT2();   // Index de fin
					
				// Ajout du surlignage avec la couleur choisie
				hG.addHighlight(startT1, endT1, new DefaultHighlighter.DefaultHighlightPainter(this.couleurSurlignage));
				hD.addHighlight(startT2, endT2, new DefaultHighlighter.DefaultHighlightPainter(this.couleurSurlignage));
				}
			} catch (BadLocationException exception) {
				System.out.println("Erreur de surlignage : " + exception.getMessage());
			}


		}
		else if (e.getSource() == boutonCouleur)
		{
			Color nouvelleColor = JColorChooser.showDialog(this, "Choisir la couleur du surlignage", this.couleurSurlignage);
			if (nouvelleColor != null)
			{
				this.couleurSurlignage = nouvelleColor;
				this.boutonCouleur.setBackground(nouvelleColor);
			}
		}
	}

	// Ajout d'une méthode pour transmettre le chemin du dernier fichier importé
	public void setCheminDernierFichier(String chemin)
	{
		this.panelG.setCheminDernierFichier(chemin);
		this.panelD.setCheminDernierFichier(chemin);
	}
}
