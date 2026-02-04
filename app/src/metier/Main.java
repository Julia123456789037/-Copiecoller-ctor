package app.src.metier;

import java.util.List;

/**
 * Classe principale pour tester le détecteur de plagiat
 */
public class Main {
    
    // public static void main(String[] args) {
    //     // ========== EXEMPLE 1 ==========
    //     System.out.println("========== EXEMPLE 1 ==========");
        
    //     List<String> texte1 = Arrays.asList(
    //         "Le", "chat", "noir", "mange", "une", "souris", "grise", "dans", 
    //         "le", "jardin", "ensoleillé", "hier", "matin"
    //     );
        
    //     List<String> texte2 = Arrays.asList(
    //         "Hier", "matin", "le", "chat", "noir", "mange", "une", "souris", 
    //         "grise", "dans", "son", "jardin"
    //     );
        
    //     // Créer le détecteur avec seuil de 5 pour cet exemple
    //     DetecteurPlagiat detecteur = new DetecteurPlagiat(4, null);
        
    //     // Analyser les textes
    //     List<Plagiat> resultats = detecteur.analyser(texte1, texte2);
        
    //     // Afficher les résultats
    //     afficherResultats(resultats, texte1, texte2);
        
        
    //     // ========== EXEMPLE 2 ==========
    //     System.out.println("\n\n========== EXEMPLE 2 ==========");
        
    //     List<String> texte3 = Arrays.asList(
    //         "L'intelligence", "artificielle", "transforme", "radicalement", 
    //         "notre", "société", "moderne", "en", "modifiant", "profondément",
    //         "les", "méthodes", "de", "travail", "et", "les", "interactions",
    //         "humaines", "dans", "divers", "domaines", "professionnels"
    //     );
        
    //     List<String> texte4 = Arrays.asList(
    //         "Notre", "société", "moderne", "est", "transformée", "par",
    //         "l'intelligence", "artificielle", "qui", "modifie", "profondément",
    //         "les", "méthodes", "de", "travail", "et", "les", "interactions",
    //         "humaines", "au", "quotidien"
    //     );
        
    //     DetecteurPlagiat detecteur2 = new DetecteurPlagiat(4, null);
    //     List<Plagiat> resultats2 = detecteur2.analyser(texte3, texte4);
        
    //     afficherResultats(resultats2, texte3, texte4);
        
        
    //     // ========== STATISTIQUES ==========
    //     System.out.println("\n\n========== STATISTIQUES EXEMPLE 1 ==========");
    //     afficherStatistiques(detecteur, texte1, texte2);
    // }
    
    /**
     * Affiche les résultats de la détection de plagiat
     * @param resultats Liste des plagiats détectés
     * @param texte1 Le premier texte
     * @param texte2 Le deuxième texte
     */
    private static void afficherResultats(List<Plagiat> resultats, 
                                         List<String> texte1, 
                                         List<String> texte2) {
        System.out.println("Nombre de plagiats détectés : " + resultats.size());
        System.out.println();
        
        if (resultats.isEmpty()) {
            System.out.println("Aucun plagiat détecté.");
            return;
        }
        
        for (int i = 0; i < resultats.size(); i++) {
            Plagiat plagiat = resultats.get(i);
            System.out.println("==========================================");
            System.out.println("Plagiat #" + (i + 1) + " :");
            System.out.println("  Texte 1: indices [" + plagiat.getIndiceDebutT1() + 
                             " - " + plagiat.getIndiceFinT1() + "]");
            System.out.println("  Texte 2: indices [" + plagiat.getIndiceDebutT2() + 
                             " - " + plagiat.getIndiceFinT2() + "]");
            System.out.println("  Longueur: " + plagiat.getLongueur() + " mots");
            
            // Afficher le texte du plagiat
            String sequenceT1 = extraireSequence(texte1, plagiat.getIndiceDebutT1(), 
                                                 plagiat.getIndiceFinT1());
            String sequenceT2 = extraireSequence(texte2, plagiat.getIndiceDebutT2(), 
                                                 plagiat.getIndiceFinT2());
            
            System.out.println("  Extrait T1: \"" + sequenceT1 + "\"");
            System.out.println("  Extrait T2: \"" + sequenceT2 + "\"");
        }
    }
    
    /**
     * Affiche les statistiques de l'analyse
     * @param detecteur Le détecteur utilisé
     * @param texte1 Le premier texte
     * @param texte2 Le deuxième texte
     */
    private static void afficherStatistiques(DetecteurPlagiat detecteur,
                                            List<String> texte1,
                                            List<String> texte2) {
        System.out.println("Seuil minimum: " + detecteur.getSeuilMini() + " mots");
        System.out.println("Taille texte 1: " + texte1.size() + " mots");
        System.out.println("Taille texte 2: " + texte2.size() + " mots");
        System.out.println("Mots uniques dans T1 (hors déterminants): " + 
                          detecteur.getLstMotT1().size());
        System.out.println("Mots uniques dans T2 (hors déterminants): " + 
                          detecteur.getLstMotT2().size());
        System.out.println("Mots communs: " + detecteur.getMotsCommuns().size());
        
        System.out.println("\nListe des mots communs:");
        for (String mot : detecteur.getMotsCommuns().keySet()) {
            Mot motObj = detecteur.getMotsCommuns().get(mot);
            System.out.println("  - \"" + mot + "\" : " + 
                             "T1=" + motObj.getIndicesT1() + ", " +
                             "T2=" + motObj.getIndicesT2());
        }
    }
    
    /**
     * Extrait une séquence de mots d'un texte
     * @param texte Le texte source
     * @param debut L'indice de début
     * @param fin L'indice de fin
     * @return La séquence de mots sous forme de chaîne
     */
    private static String extraireSequence(List<String> texte, int debut, int fin) {
        StringBuilder resultat = new StringBuilder();
        for (int i = debut; i <= fin && i < texte.size(); i++) {
            resultat.append(texte.get(i));
            if (i < fin) {
                resultat.append(" ");
            }
        }
        return resultat.toString();
    }
}