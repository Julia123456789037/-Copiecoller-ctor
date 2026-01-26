package app.src.metier;

/**
 * Classe représentant un plagiat détecté entre deux textes
 * Les indices représentent les positions en caractères dans les textes originaux
 * La longueur représente le nombre de lettres (pas de mots)
 */
public class Plagiat {
    private int indiceDebutT1;  // Position de la première lettre du premier mot dans T1
    private int indiceFinT1;    // Position de la dernière lettre du dernier mot dans T1
    private int indiceDebutT2;  // Position de la première lettre du premier mot dans T2
    private int indiceFinT2;    // Position de la dernière lettre du dernier mot dans T2
    private int longueur;       // Nombre total de lettres
    
    // Indices de mots (optionnels, pour référence)
    private int motDebutT1;
    private int motFinT1;
    private int motDebutT2;
    private int motFinT2;
    
    /**
     * Constructeur complet avec indices de caractères et indices de mots
     * @param indiceDebutT1 Position de la première lettre du premier mot dans T1
     * @param indiceFinT1 Position de la dernière lettre du dernier mot dans T1
     * @param indiceDebutT2 Position de la première lettre du premier mot dans T2
     * @param indiceFinT2 Position de la dernière lettre du dernier mot dans T2
     * @param longueur Nombre total de lettres
     * @param motDebutT1 Indice du mot de début dans T1
     * @param motFinT1 Indice du mot de fin dans T1
     * @param motDebutT2 Indice du mot de début dans T2
     * @param motFinT2 Indice du mot de fin dans T2
     */
    public Plagiat(int indiceDebutT1, int indiceFinT1, int indiceDebutT2, int indiceFinT2,
                   int longueur, int motDebutT1, int motFinT1, int motDebutT2, int motFinT2) {
        this.indiceDebutT1 = indiceDebutT1;
        this.indiceFinT1 = indiceFinT1;
        this.indiceDebutT2 = indiceDebutT2;
        this.indiceFinT2 = indiceFinT2;
        this.longueur = longueur;
        this.motDebutT1 = motDebutT1;
        this.motFinT1 = motFinT1;
        this.motDebutT2 = motDebutT2;
        this.motFinT2 = motFinT2;
    }
    
    /**
     * Constructeur simplifié (sans indices de mots)
     */
    public Plagiat(int indiceDebutT1, int indiceFinT1, int indiceDebutT2, int indiceFinT2, int longueur) {
        this(indiceDebutT1, indiceFinT1, indiceDebutT2, indiceFinT2, longueur, -1, -1, -1, -1);
    }
    
    // ============================================
    // GETTERS - Indices de caractères
    // ============================================
    
    /**
     * Obtenir la position de la première lettre du premier mot dans T1
     */
    public int getIndiceDebutT1() {
        return indiceDebutT1;
    }
    
    /**
     * Obtenir la position de la dernière lettre du dernier mot dans T1
     */
    public int getIndiceFinT1() {
        return indiceFinT1;
    }
    
    /**
     * Obtenir la position de la première lettre du premier mot dans T2
     */
    public int getIndiceDebutT2() {
        return indiceDebutT2;
    }
    
    /**
     * Obtenir la position de la dernière lettre du dernier mot dans T2
     */
    public int getIndiceFinT2() {
        return indiceFinT2;
    }
    
    /**
     * Obtenir le nombre de lettres du plagiat
     */
    public int getLongueur() {
        return longueur;
    }
    
    // ============================================
    // GETTERS - Indices de mots
    // ============================================
    
    public int getMotDebutT1() {
        return motDebutT1;
    }
    
    public int getMotFinT1() {
        return motFinT1;
    }
    
    public int getMotDebutT2() {
        return motDebutT2;
    }
    
    public int getMotFinT2() {
        return motFinT2;
    }
    
    @Override
    public String toString() {
        return "Plagiat{" +
                "T1=[char:" + indiceDebutT1 + "-" + indiceFinT1 + "], " +
                "T2=[char:" + indiceDebutT2 + "-" + indiceFinT2 + "], " +
                "longueur=" + longueur + " lettres" +
                '}';
    }
}