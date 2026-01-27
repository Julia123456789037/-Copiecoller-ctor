package app.src.metier;

import java.util.*;
import app.src.Controleur;

/**
 * Classe principale pour la détection de plagiat entre deux textes
 */
public class DetecteurPlagiat {
    private Controleur ctrl;
    private int seuilMini;  // Seuil minimum en nombre de LETTRES
    private Set<String> determinants;
    private HashMap<String, Mot> lstMotT1;
    private HashMap<String, Mot> lstMotT2;
    private HashMap<String, Mot> motsCommuns;
    private ArrayList<Plagiat> plagiatsDetectes;
    
    /**
     * Constructeur avec seuil personnalisé
     * @param seuilMini Le nombre minimum de LETTRES pour considérer un plagiat
     */
    public DetecteurPlagiat(int seuilMini, Controleur ctrl) {
        this.ctrl = ctrl;
        this.seuilMini = seuilMini;
        this.determinants = initialiserDeterminants();
        this.lstMotT1 = new HashMap<>();
        this.lstMotT2 = new HashMap<>();
        this.motsCommuns = new HashMap<>();
        this.plagiatsDetectes = new ArrayList<>();
    }
    
    /**
     * Initialise la liste des déterminants à ignorer
     */
    private Set<String> initialiserDeterminants() {
        try {
            return ctrl.getDets();
        } catch (Exception e) {
            Set<String> dets = new HashSet<>();
            dets.addAll(Arrays.asList(
                "les", "des", "une", "aux", "ces", "ses", "mes", "tes",
                "nos", "vos", "par", "sur", "est", "son", "ton", "car",
                "qui", "que", "ont", "pas", "plus", "mais", "tout", "tous",
                "très", "bien", "sous", "avec", "sans", "pour", "dans", "été"
            ));
            return dets;
        }
    }
    
    /**
     * Extrait les mots d'un texte et les stocke dans la HashMap appropriée
     */
    private void extraireMots(List<String> texte, int numeroTexte) {
        for (int i = 0; i < texte.size(); i++) {
            String motCourant = normaliser(texte.get(i));
            
            if (motCourant.length() > 2 && !estDeterminant(motCourant)) {
                if (numeroTexte == 1) {
                    if (lstMotT1.containsKey(motCourant)) {
                        lstMotT1.get(motCourant).ajouterIndiceT1(i);
                    } else {
                        Mot nouveauMot = new Mot(motCourant);
                        nouveauMot.ajouterIndiceT1(i);
                        lstMotT1.put(motCourant, nouveauMot);
                    }
                } else if (numeroTexte == 2) {
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
                for (Integer indice : motT2.getIndicesT2()) {
                    motT1.ajouterIndiceT2(indice);
                }
                motsCommuns.put(cle, motT1);
            }
        }
    }
    
    /**
     * Détecte les plagiats avec calcul des positions en caractères
     */
    private void detecterPlagiats(List<String> t1, List<String> t2, 
                                   String texteOriginal1, String texteOriginal2) {
        Set<String> plagiatsDejaTraites = new HashSet<>();
        
        // Précalculer les positions de chaque mot dans les textes originaux
        int[] positionsMots1 = calculerPositionsMots(texteOriginal1, t1);
        int[] positionsMots2 = calculerPositionsMots(texteOriginal2, t2);
        
        for (Map.Entry<String, Mot> entry : motsCommuns.entrySet()) {
            Mot mot = entry.getValue();
            
            for (Integer indiceT1 : mot.getIndicesT1()) {
                for (Integer indiceT2 : mot.getIndicesT2()) {
                    String cle = indiceT1 + ":" + indiceT2;
                    if (plagiatsDejaTraites.contains(cle)) {
                        continue;
                    }
                    
                    // Chercher la séquence (retourne indices de mots: [debut1, fin1, debut2, fin2])
                    int[] sequence = chercherSequenceIndices(t1, t2, indiceT1, indiceT2);
                    
                    if (sequence != null) {
                        int motDebutT1 = sequence[0];
                        int motFinT1 = sequence[1];
                        int motDebutT2 = sequence[2];
                        int motFinT2 = sequence[3];
                        
                        // Calculer les positions en caractères
                        // indiceDebut = position de la première lettre du premier mot
                        int indiceDebutT1 = positionsMots1[motDebutT1];
                        // indiceFin = position de la dernière lettre du dernier mot
                        int indiceFinT1 = positionsMots1[motFinT1] + t1.get(motFinT1).length() - 1;
                        
                        int indiceDebutT2 = positionsMots2[motDebutT2];
                        int indiceFinT2 = positionsMots2[motFinT2] + t2.get(motFinT2).length() - 1;
                        
                        // Calculer le nombre de lettres
                        int nombreLettres = calculerNombreLettres(t1, motDebutT1, motFinT1);
                        
                        if (nombreLettres >= seuilMini) {
                            Plagiat plagiat = new Plagiat(
                                indiceDebutT1, indiceFinT1,
                                indiceDebutT2, indiceFinT2,
                                nombreLettres,
                                motDebutT1, motFinT1,
                                motDebutT2, motFinT2
                            );
                            
                            plagiatsDetectes.add(plagiat);
                            
                            // Marquer les positions couvertes comme traitées
                            for (int i = motDebutT1; i <= motFinT1; i++) {
                                for (int j = motDebutT2; j <= motFinT2; j++) {
                                    plagiatsDejaTraites.add(i + ":" + j);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Cherche une séquence de mots identiques et retourne les indices de mots
     * @return tableau [motDebutT1, motFinT1, motDebutT2, motFinT2] ou null
     */
    private int[] chercherSequenceIndices(List<String> t1, List<String> t2, int posT1, int posT2) {
        int debut1 = posT1;
        int debut2 = posT2;
        int fin1 = posT1;
        int fin2 = posT2;
        
        // Extension à droite
        while (fin1 < t1.size() - 1 && 
               fin2 < t2.size() - 1 &&
               normaliser(t1.get(fin1 + 1)).equals(normaliser(t2.get(fin2 + 1)))) {
            fin1++;
            fin2++;
        }
        
        // Extension à gauche
        while (debut1 > 0 && 
               debut2 > 0 &&
               normaliser(t1.get(debut1 - 1)).equals(normaliser(t2.get(debut2 - 1)))) {
            debut1--;
            debut2--;
        }
        
        int nombreMots = fin1 - debut1 + 1;
        
        if (nombreMots >= 1) {
            return new int[]{debut1, fin1, debut2, fin2};
        }
        return null;
    }
    
    /**
     * Calcule la position de début de chaque mot dans le texte original
     */
    private int[] calculerPositionsMots(String texteOriginal, List<String> mots) {
        int[] positions = new int[mots.size()];
        int positionCourante = 0;
        
        for (int i = 0; i < mots.size(); i++) {
            String mot = mots.get(i);
            int pos = texteOriginal.indexOf(mot, positionCourante);
            if (pos != -1) {
                positions[i] = pos;
                positionCourante = pos + mot.length();
            }
        }
        return positions;
    }
    
    /**
     * Calcule le nombre de lettres dans une séquence de mots
     */
    private int calculerNombreLettres(List<String> mots, int debut, int fin) {
        int total = 0;
        for (int i = debut; i <= fin; i++) {
            // Compter uniquement les lettres (pas la ponctuation)
            String mot = mots.get(i);
            for (char c : mot.toCharArray()) {
                if (Character.isLetter(c)) {
                    total++;
                }
            }
        }
        return total;
    }
    
    private String normaliser(String mot) {
        return mot.toLowerCase().trim().replaceAll("[.,;:!?'\"]", "");
    }
    
    private boolean estDeterminant(String mot) {
        return determinants.contains(normaliser(mot));
    }
    
    /**
     * Analyse deux textes et retourne les plagiats détectés
     * @param texte1 Le premier texte (liste de mots)
     * @param texte2 Le deuxième texte (liste de mots)
     * @param texteOriginal1 Le texte original 1 (chaîne complète)
     * @param texteOriginal2 Le texte original 2 (chaîne complète)
     * @return La liste des plagiats détectés
     */
    public ArrayList<Plagiat> analyser(List<String> texte1, List<String> texte2,
                                        String texteOriginal1, String texteOriginal2) {
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
        
        // Étape 4: Détecter les plagiats avec les textes originaux
        detecterPlagiats(texte1, texte2, texteOriginal1, texteOriginal2);
        
        return plagiatsDetectes;
    }
    
    /**
     * Version simplifiée de analyser (reconstruit les textes originaux)
     */
    public ArrayList<Plagiat> analyser(List<String> texte1, List<String> texte2) {
        String texteOriginal1 = String.join(" ", texte1);
        String texteOriginal2 = String.join(" ", texte2);
        return analyser(texte1, texte2, texteOriginal1, texteOriginal2);
    }
    
    // ============================================
    // GETTERS ET SETTERS
    // ============================================
    
    public HashMap<String, Mot> getLstMotT1() {
        return lstMotT1;
    }
    
    public HashMap<String, Mot> getLstMotT2() {
        return lstMotT2;
    }
    
    public HashMap<String, Mot> getMotsCommuns() {
        return motsCommuns;
    }
    
    public int getSeuilMini() {
        return seuilMini;
    }
    
    public void setSeuilMini(int seuilMini) {
        this.seuilMini = seuilMini;
    }
}