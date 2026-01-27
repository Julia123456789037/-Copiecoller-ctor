package app.src.ihm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Image;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.parser.ParseContext;

public class panelTexte extends JPanel implements ActionListener
{
	FrameBasique frame;
	JTextArea textArea;
	JTextField nombreDeMots;
	JTextField nombreDeMotsMin;
	JButton boutonImporter;
	JButton boutonSupprimer;
	private int nbMotsCommun = 8;
	private String cheminDernierFichier = null;

	public panelTexte( FrameBasique frame )
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

		this.boutonImporter    = new JButton();
		ImageIcon iconImporter = new ImageIcon("../bin/logo/importer.png");
		Image imgImporter      = iconImporter.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
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
		/*  Positionnment des Composant  */
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
		this.textArea.getDocument().addDocumentListener(new TexteChangeListener());

		majTexte();
	}

	// Classe interne pour écouter et gérer les changements des zones de texte
	private class TexteChangeListener implements DocumentListener
	{
		public void insertUpdate(DocumentEvent e)  { majTexte(); }
		public void removeUpdate(DocumentEvent e)  { majTexte(); }
		public void changedUpdate(DocumentEvent e) { majTexte(); }
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

	public String getTexte()
	{
		return this.textArea.getText();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == boutonImporter)
		{
			System.out.println("Bouton Importer cliqué");
			JFileChooser choisirImport = new JFileChooser();
			if (this.cheminDernierFichier != null)
				choisirImport.setCurrentDirectory(new File(this.cheminDernierFichier).getParentFile());

			choisirImport.setDialogTitle("Choisir un fichier texte");
			choisirImport.setFileSelectionMode(JFileChooser.FILES_ONLY);
			choisirImport.setAcceptAllFileFilterUsed(false);
			choisirImport.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichier txt",   "txt" ));
			choisirImport.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Document Word", "docx"));
			choisirImport.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichier PDF",   "pdf" ));
			
			int val = choisirImport.showOpenDialog(this.frame);
			
			if (val == JFileChooser.APPROVE_OPTION)
			{
				File   fichierChoisi = choisirImport.getSelectedFile();
				String cheminFichier = fichierChoisi.getAbsolutePath();
				this.setCheminDernierFichier(cheminFichier);
				this.frame.setCheminDernierFichier(cheminFichier);

				try
				{
					String contenuFichier = lireFichier(fichierChoisi);
					this.textArea.setText(contenuFichier);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(this.frame, "Erreur lors de la lecture du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		else if (e.getSource() == boutonSupprimer)
		{
			System.out.println("Bouton Supprimer cliqué");
			this.textArea.setText("");
		}
	}

	public String getTextArea() { return this.textArea.getText(); }

	public JTextArea getJTextArea()
	{
		return this.textArea;
	}

	public void setCheminDernierFichier(String chemin) { this.cheminDernierFichier = chemin; }

	private String lireFichier(File fichier) throws Exception
	{
		AutoDetectParser   parser   = new AutoDetectParser();
		BodyContentHandler handler  = new BodyContentHandler(-1); // -1 = pas de limite de taille
		Metadata           metadata = new Metadata();
		ParseContext       context  = new ParseContext();

		try (FileInputStream fis = new FileInputStream(fichier))
		{
			parser.parse(fis, handler, metadata, context);
		}

		return handler.toString();
	}
}
