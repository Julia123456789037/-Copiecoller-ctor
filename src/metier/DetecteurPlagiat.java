import java.util.*;
import metier.Controleur;

/**
 * Classe principale pour la détection de plagiat entre deux textes
 */
public class DetecteurPlagiat {

	private Controleur control;
	private int seuilMini;
	private Set<String> determinants;
	private HashMap<String, Mot> lstMotT1;
	private HashMap<String, Mot> lstMotT2;
	private HashMap<String, Mot> motsCommuns;
	private List<Plagiat> plagiatsDetectes;
	
	/**
	 * Constructeur avec seuil par défaut (8 mots)
	 */
	public DetecteurPlagiat( Controleur contr) {
		this(8);
		this.control = contr;
	}
	
	/**
	 * Constructeur avec seuil personnalisé
	 * @param seuilMini Le nombre minimum de mots pour considérer un plagiat
	 */
	public DetecteurPlagiat(int seuilMini, Controleur cont) {
		this.control = cont;
		this.seuilMini = seuilMini;
		this.determinants = this.control.initialiserDeterminants();
		this.lstMotT1 = new HashMap<>();
		this.lstMotT2 = new HashMap<>();
		this.motsCommuns = new HashMap<>();
		this.plagiatsDetectes = new ArrayList<>();
	}
	/* 
	/**
	 * Initialise la liste des déterminants de 3 lettres à ignorer
	 * @return Un ensemble de déterminants
	 *
	private Set<String> initialiserDeterminants() {
		Set<String> dets = new HashSet<>();
		dets.addAll(Arrays.asList(
			"les", "des", "une", "aux", "ces", "ses", "mes", "tes",
			"nos", "vos", "par", "sur", "est", "son", "ton", "car",
			"qui", "que", "ont", "pas", "plus", "mais", "tout", "tous",
			"très", "bien", "sous", "avec", "sans", "pour", "dans", "été"
		));
		return dets;
	}*/
	
	/**
	 * Extrait les mots d'un texte et les stocke dans la HashMap appropriée
	 * @param texte Le texte sous forme de liste de mots
	 * @param numeroTexte 1 pour T1, 2 pour T2
	 */
	private void extraireMots(List<String> texte, int numeroTexte) {
		for (int i = 0; i < texte.size(); i++) {
			String motCourant = normaliser(texte.get(i));
			
			// Vérifier les conditions : longueur > 2 et pas un déterminant
			if (motCourant.length() > 2 && !estDeterminant(motCourant)) {
				
				if (numeroTexte == 1) {
					// Traitement pour T1
					if (lstMotT1.containsKey(motCourant)) {
						lstMotT1.get(motCourant).ajouterIndiceT1(i);
					} else {
						Mot nouveauMot = new Mot(motCourant);
						nouveauMot.ajouterIndiceT1(i);
						lstMotT1.put(motCourant, nouveauMot);
					}
					
				} else if (numeroTexte == 2) {
					// Traitement pour T2
					if (lstMotT2.containsKey(motCourant)) {
						lstMotT2.get(motCourant).ajouterIndiceT2(i);
					} else {
						Mot nouveauMot = new Mot(motCourant);
						nouveauMot.ajouterIndiceT2(i);
						lstMotT2.put(motCourant, nouveauMot);
					}
				}
			}
		}
	}
	
	/**
	 * Trouve l'intersection des mots présents dans les deux textes
	 */
	private void trouverMotsCommuns() {
		for (Map.Entry<String, Mot> entry : lstMotT1.entrySet()) {
			String cle = entry.getKey();
			Mot motT1 = entry.getValue();
			
			if (lstMotT2.containsKey(cle)) {
				Mot motT2 = lstMotT2.get(cle);
				
				// Fusionner les indices de T2 dans l'objet de T1
				for (Integer indice : motT2.getIndicesT2()) {
					motT1.ajouterIndiceT2(indice);
				}
				
				// Ajouter à la liste des mots communs
				motsCommuns.put(cle, motT1);
			}
		}
	}
	
	/**
	 * Détecte les plagiats en parcourant les mots communs
	 * @param t1 Le premier texte
	 * @param t2 Le deuxième texte
	 */
	private void detecterPlagiats(List<String> t1, List<String> t2) {
		Set<String> plagiatsDejaTraites = new HashSet<>();
		
		for (Map.Entry<String, Mot> entry : motsCommuns.entrySet()) {
			Mot mot = entry.getValue();
			
			// Pour chaque occurrence du mot dans T1
			for (Integer indiceT1 : mot.getIndicesT1()) {
				// Pour chaque occurrence du mot dans T2
				for (Integer indiceT2 : mot.getIndicesT2()) {
					
					String cle = indiceT1 + ":" + indiceT2;
					if (plagiatsDejaTraites.contains(cle)) {
						continue;
					}
					
					// Chercher le plagiat à partir de ces positions
					Plagiat plagiat = chercherSequence(t1, t2, indiceT1, indiceT2);
					
					if (plagiat != null && plagiat.getLongueur() >= seuilMini) {
						plagiatsDetectes.add(plagiat);
						
						// Marquer toutes les positions couvertes comme traitées
						for (int i = plagiat.getIndiceDebutT1(); i <= plagiat.getIndiceFinT1(); i++) {
							for (int j = plagiat.getIndiceDebutT2(); j <= plagiat.getIndiceFinT2(); j++) {
								plagiatsDejaTraites.add(i + ":" + j);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Cherche une séquence de mots identiques à partir de positions données
	 * @param t1 Le premier texte
	 * @param t2 Le deuxième texte
	 * @param posT1 Position de départ dans T1
	 * @param posT2 Position de départ dans T2
	 * @return Un objet Plagiat ou null si aucune séquence trouvée
	 */
	private Plagiat chercherSequence(List<String> t1, List<String> t2, int posT1, int posT2) {
		// Positions de départ
		int debut1 = posT1;
		int debut2 = posT2;
		
		// ---- EXTENSION À DROITE ----
		int fin1 = posT1;
		int fin2 = posT2;
		
		while (fin1 < t1.size() - 1 && 
			fin2 < t2.size() - 1 &&
			normaliser(t1.get(fin1 + 1)).equals(normaliser(t2.get(fin2 + 1))) &&
			!estDeterminant(t1.get(fin1 + 1))) {
			fin1++;
			fin2++;
		}
		
		// ---- EXTENSION À GAUCHE ----
		while (debut1 > 0 && 
			debut2 > 0 &&
			normaliser(t1.get(debut1 - 1)).equals(normaliser(t2.get(debut2 - 1))) &&
			!estDeterminant(t1.get(debut1 - 1))) {
			debut1--;
			debut2--;
		}
		
		// Créer l'objet Plagiat
		int longueur = fin1 - debut1 + 1;
		
		if (longueur >= 1) { return new Plagiat(debut1, fin1, debut2, fin2); } 
		else { return null; }
	}
	
	/**
	 * Normalise un mot (minuscules, suppression ponctuation)
	 * @param mot Le mot à normaliser
	 * @return Le mot normalisé
	 */
	private String normaliser(String mot) {
		return mot.toLowerCase().trim().replaceAll("[.,;:!?'\"]", "");
	}
	
	/**
	 * Vérifie si un mot est un déterminant
	 * @param mot Le mot à vérifier
	 * @return true si c'est un déterminant, false sinon
	 */
	private boolean estDeterminant(String mot) {
		return determinants.contains(normaliser(mot));
	}
	
	/**
	 * Analyse deux textes et retourne les plagiats détectés
	 * @param texte1 Le premier texte (liste de mots)
	 * @param texte2 Le deuxième texte (liste de mots)
	 * @return La liste des plagiats détectés
	 */
	public List<Plagiat> analyser(List<String> texte1, List<String> texte2) {
		// Réinitialiser les structures
		lstMotT1.clear();
		lstMotT2.clear();
		motsCommuns.clear();
		plagiatsDetectes.clear();
		
		// Étape 1: Extraire les mots de T1
		extraireMots(texte1, 1);
		
		// Étape 2: Extraire les mots de T2
		extraireMots(texte2, 2);
		
		// Étape 3: Trouver les mots communs
		trouverMotsCommuns();
		
		// Étape 4: Détecter les plagiats
		detecterPlagiats(texte1, texte2);
		
		// Retourner les plagiats détectés
		return plagiatsDetectes;
	}
	
	// ============================================
	// GETTERS ET SETTERS
	// ============================================
	
	public HashMap<String, Mot> getLstMotT1() { return lstMotT1; }
	public HashMap<String, Mot> getLstMotT2() { return lstMotT2; }
	public HashMap<String, Mot> getMotsCommuns() { return motsCommuns; }
	public int getSeuilMini() { return seuilMini; }
	
	public void setSeuilMini(int seuilMini) { this.seuilMini = seuilMini; }
}