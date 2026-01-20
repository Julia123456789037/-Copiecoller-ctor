import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;

public class panelTexte extends JPanel implements ActionListener
{
	JFrame frame;
	JTextArea textArea;
	JTextField nombreDeMots;
	JTextField nombreDeMotsMin;
	JButton boutonImporter;
	JButton boutonSupprimer;
	private int nbMotsCommun = 8;

	public panelTexte( JFrame frame )
	{
		super();
		this.frame = frame;

		this.setLayout(new BorderLayout(5, 5));

		/*-------------------------------*/
		/* Création des Composants */
		/*-------------------------------*/

		this.textArea = new JTextArea();
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.textArea.setMargin(new java.awt.Insets(5, 5, 5, 5));

		this.textArea.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate (DocumentEvent e) { majTexte(); }

			@Override
			public void removeUpdate (DocumentEvent e) { majTexte(); }

			@Override
			public void changedUpdate(DocumentEvent e) { majTexte(); }
		});

		this.boutonImporter = new JButton();
		ImageIcon iconImporter = new ImageIcon("../bin/logo/importer.png");
		Image imgImporter = iconImporter.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		this.boutonImporter.setIcon(new ImageIcon(imgImporter));

		this.boutonSupprimer = new JButton();
		ImageIcon iconSupprimer = new ImageIcon("../bin/logo/gomme.png");
		Image imgSupprimer = iconSupprimer.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
		this.boutonSupprimer.setIcon(new ImageIcon(imgSupprimer));

		JPanel panelHaut = new JPanel(new BorderLayout(5, 5));
		JPanel panelBas  = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		this.nombreDeMots = new JTextField("Nombre de mots : 0", 20);
		this.nombreDeMots.setEditable(false);
		
		this.nombreDeMotsMin = new JTextField("Mots min : 0", 20);
		this.nombreDeMotsMin.setEditable(false);
		
		/*-------------------------------*/
		/* Positionnment des Composant */
		/*-------------------------------*/

		panelHaut.add(this.boutonImporter, BorderLayout.WEST);
		panelHaut.add(this.boutonSupprimer, BorderLayout.EAST);

		panelBas.add(this.nombreDeMots);
		panelBas.add(this.nombreDeMotsMin);

		this.add(panelHaut, BorderLayout.NORTH);
		this.add(new JScrollPane(this.textArea), BorderLayout.CENTER);

		this.add(panelBas, BorderLayout.SOUTH);
		
		/*-------------------------------*/
		/* Activation des Composants */
		/*-------------------------------*/

		this.boutonImporter.addActionListener(this);
		this.boutonSupprimer.addActionListener(this);

		majTexte();
	}

	private void majTexte()
	{
		String texte = this.textArea.getText().trim();
		int    nbMots = 0;
		if (!texte.isEmpty())
			nbMots = texte.split("\\W+").length;

		this.nombreDeMots.setText("Nombre de mots : " + nbMots);
		this.nombreDeMotsMin.setText("Mots min : " + (this.nbMotsCommun * 3));
	}

	public void setNbMotsCommun(int nbMotsCommun)
	{
		this.nbMotsCommun = nbMotsCommun;
		majTexte();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == boutonImporter)
		{
			System.out.println("Bouton Importer cliqué");
		}
		else if (e.getSource() == boutonSupprimer)
		{
			System.out.println("Bouton Supprimer cliqué");
			this.textArea.setText("");
		}
	}

	public String getTextArea()
	{
		return this.textArea.getText();
	}
}
