import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;

public class panelTexte extends JPanel
{
	JFrame frame;
	JTextArea textArea;
	JTextField nombreDeMots;
	JButton boutonImporter;

	public panelTexte( JFrame frame )
	{
		super();
		this.frame = frame;

		this.setLayout(new BorderLayout());

		/*-------------------------------*/
		/* Création des Composants */
		/*-------------------------------*/

		this.textArea = new JTextArea();
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);

		this.textArea.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate (DocumentEvent e) { majTexte(); }

			@Override
			public void removeUpdate (DocumentEvent e) { majTexte(); }

			@Override
			public void changedUpdate(DocumentEvent e) { majTexte(); }
		});

		this.boutonImporter = new JButton("Importer");

		JPanel panelHaut = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel panelBas  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		this.nombreDeMots = new JTextField("Nombre de mots : 0", 15);
		this.nombreDeMots.setEditable(false);
		/*-------------------------------*/
		/* Positionnment des Composant */
		/*-------------------------------*/

		panelHaut.add(this.boutonImporter);
		panelBas.add(this.nombreDeMots);

		this.add(panelHaut, BorderLayout.NORTH);
		this.add(new JScrollPane(this.textArea), BorderLayout.CENTER);

		this.add(panelBas, BorderLayout.SOUTH);


	}

	private void majTexte()
	{
		String texte = this.textArea.getText().trim();
		int    nbMots = 0;
		if (!texte.isEmpty())
			nbMots = texte.split("\\s+").length;

		this.nombreDeMots.setText("Nombre de mots : " + nbMots);
	}

	public String getTextArea()
	{
		return this.textArea.getText();
	}
}
