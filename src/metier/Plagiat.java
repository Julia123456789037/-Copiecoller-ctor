package metier;

/**
 * Classe représentant un plagiat détecté entre deux textes
 */
public class Plagiat {
	private int indiceDebutT1;
	private int indiceFinT1;
	private int indiceDebutT2;
	private int indiceFinT2;
	private int longueur;
	
	/**
	 * Constructeur
	 * @param indiceDebutT1 Indice de début dans le texte 1
	 * @param indiceFinT1 Indice de fin dans le texte 1
	 * @param indiceDebutT2 Indice de début dans le texte 2
	 * @param indiceFinT2 Indice de fin dans le texte 2
	 */
	public Plagiat(int indiceDebutT1, int indiceFinT1, int indiceDebutT2, int indiceFinT2) {
		this.indiceDebutT1 = indiceDebutT1;
		this.indiceFinT1 = indiceFinT1;
		this.indiceDebutT2 = indiceDebutT2;
		this.indiceFinT2 = indiceFinT2;
		this.longueur = indiceFinT1 - indiceDebutT1 + 1;
	}
	
	/**
	 * Obtenir l'indice de début dans T1
	 * @return L'indice de début
	 */
	public int getIndiceDebutT1() { return indiceDebutT1; }
	
	/**
	 * Obtenir l'indice de fin dans T1
	 * @return L'indice de fin
	 */
	public int getIndiceFinT1() { return indiceFinT1; }
	
	/**
	 * Obtenir l'indice de début dans T2
	 * @return L'indice de début
	 */
	public int getIndiceDebutT2() { return indiceDebutT2; }
	
	/**
	 * Obtenir l'indice de fin dans T2
	 * @return L'indice de fin
	 */
	public int getIndiceFinT2() { return indiceFinT2; }
	
	/**
	 * Obtenir la longueur du plagiat
	 * @return Le nombre de mots plagiés
	 */
	public int getLongueur() { return longueur; }
	
	@Override
	public String toString() {
		return "Plagiat{" +
				"T1=[" + indiceDebutT1 + "-" + indiceFinT1 + "], " +
				"T2=[" + indiceDebutT2 + "-" + indiceFinT2 + "], " +
				"longueur=" + longueur +
				'}';
	}
}