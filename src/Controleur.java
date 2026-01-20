import java.util.Arrays;
import java.util.List;

import metier.DetecteurPlagiat;
import metier.Plagiat;

public class Controleur 
{
    public List<String> texte1;
    public List<String> texte2;
    
	public Controleur() throws Exception
	{
		FrameBasique frame = new FrameBasique(this);
	}
	public static void main(String[] args) throws Exception
	{
		new Controleur();
	}

    /**
     * transmet les paramètre et le résultat pour une annalyse de plagiat entre le back et le front
     * @param String texte 1
     * @param String texte 2
     * @param int seuil minimal pour consodéré qu'on a du plagiat
     * @return une liste des différent plagiat
     */
    private static List<Plagiat> annalysePlagiat( String texte1, String texte2, int seuilMini) {
        formatexte( texte1, texte2 );

        // Créer le détecteur avec seuil de 5 pour cet exemple
        DetecteurPlagiat detecteur = new DetecteurPlagiat(seuilMini);
        
        // Analyser les textes
        List<Plagiat> resultats = detecteur.analyser( this.texte1, this.texte2);
        
        // Afficher les résultats
        //afficherResultats(resultats, texte1, texte2);

        return resultats;
    }

    /**
     * Format les texte dans des List<String>
     * @param String texte 1
     * @param String texte 2
     */
    private static void formatexte( String texte1, String texte2 ) {
        this.texte1 =  Arrays.asList(texte1.split("\\s+"));
        this.texte2 =  Arrays.asList(texte2.split("\\s+"));
    }

}