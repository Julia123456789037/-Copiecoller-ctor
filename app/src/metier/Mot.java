package app.src.metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un mot avec ses emplacements dans les deux textes
 */
public class Mot {
    private String mot;
    private List<Integer> indicesT1;
    private List<Integer> indicesT2;
    
    /**
     * Constructeur
     * @param mot Le mot à stocker
     */
    public Mot(String mot) {
        this.mot = mot;
        this.indicesT1 = new ArrayList<>();
        this.indicesT2 = new ArrayList<>();
    }
    
    /**
     * Obtenir le mot
     * @return Le mot
     */
    public String getMot() {
        return mot;
    }
    
    /**
     * Obtenir les indices dans le texte 1
     * @return Liste des indices dans T1
     */
    public List<Integer> getIndicesT1() {
        return indicesT1;
    }
    
    /**
     * Obtenir les indices dans le texte 2
     * @return Liste des indices dans T2
     */
    public List<Integer> getIndicesT2() {
        return indicesT2;
    }
    
    /**
     * Ajouter un indice pour le texte 1
     * @param indice L'indice à ajouter
     */
    public void ajouterIndiceT1(int indice) {
        indicesT1.add(indice);
    }
    
    /**
     * Ajouter un indice pour le texte 2
     * @param indice L'indice à ajouter
     */
    public void ajouterIndiceT2(int indice) {
        indicesT2.add(indice);
    }
    
    @Override
    public String toString() {
        return "Mot{" +
                "mot='" + mot + '\'' +
                ", indicesT1=" + indicesT1 +
                ", indicesT2=" + indicesT2 +
                '}';
    }
}