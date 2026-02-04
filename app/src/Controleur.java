package app.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.src.ihm.FrameBasique;
import app.src.metier.Plagiat;
import app.src.metier.DetecteurPlagiat;

public class Controleur
{
	public List<String> texte1;
	public List<String> texte2;
	public Set<String> dets = new HashSet<>();

	public Controleur() throws Exception
	{
		new FrameBasique(this);
	}

	public static void main(String[] args) throws Exception
	{
		new Controleur();
	}

	/**
	 * transmet les paramètre et le résultat pour une annalyse de plagiat entre
	 * le back et le front
	 * 
	 * @param String
	 *            texte 1
	 * @param String
	 *            texte 2
	 * @param int
	 *            seuil minimal pour consodéré qu'on a du plagiat
	 * @return une liste des différent plagiat
	 */
	public ArrayList<Plagiat> annalysePlagiat(String text1, String text2, int seuilMini)
	{
		formatexte(text1, text2);

		// Créer le détecteur avec seuil de 5 pour cet exemple
		DetecteurPlagiat detecteur = new DetecteurPlagiat(seuilMini, this);

		// Analyser les textes avec les textes originaux pour que les indices correspondent
		ArrayList<Plagiat> resultats = detecteur.analyser(this.texte1, this.texte2, text1, text2);

		// Afficher les résultats
		// afficherResultats(resultats, texte1, texte2);

		return resultats;
	}

	/**
	 * Format les texte dans des List<String>
	 * 
	 * @param String
	 *            texte 1
	 * @param String
	 *            texte 2
	 */
	private void formatexte(String text1, String text2)
	{
		this.texte1 = Arrays.asList(text1.split("\\s+"));
		this.texte2 = Arrays.asList(text2.split("\\s+"));

		System.out.println();

	}

	public Set<String> getDets()
	{
		return dets;
	}

}