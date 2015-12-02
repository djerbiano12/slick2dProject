package slick;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import animation.OutilsAnimation;

public class MaTiledMap extends TiledMap {
    private static final int EPSILON = 50;
    private int nombrePieces; // nombre de pièces à placer dans le labyrinthe
    private int xPerso, yPerso, direction;  // paramètres du personnage
    private int xPorte, yPorte; // paramètres de la porte
    private String niveauSuivant; // nom du fichier qui contient la prochaine carte

    public MaTiledMap(String cheminCarte) throws SlickException {
        super(cheminCarte);

        final String DEFAULT_PROPERTY_VALUE = "20";

        // On récupère le nombre de pieces
        this.nombrePieces = Integer.parseInt(this.getMapProperty("nombrePieces", DEFAULT_PROPERTY_VALUE));

        for (int objectID = 0; objectID < getObjectCount(0); objectID++) {
            // On récupère les infos de la porte
            if ("porte".equals(getObjectType(0, objectID))) {
                // On a besoin de connaitre les coordonnees du milieu de la porte
                xPorte = getObjectX(0, objectID) + getObjectWidth(0, objectID) / 2;
                yPorte = getObjectY(0, objectID) + getObjectHeight(0, objectID) / 2;
                niveauSuivant = getObjectProperty(0, objectID, "suivant", "");
            } else {
                // On recupere le infos du perso
                if ("perso".equals(getObjectType(0, objectID))) {
                    xPerso = getObjectX(0, objectID);
                    yPerso = getObjectY(0, objectID);
                    String dir = getObjectProperty(0, objectID, "direction", "bas").toLowerCase();
                    switch (dir) {
                        case "haut":
                            this.direction = OutilsAnimation.HAUT;
                            break;
                        case "gauche":
                            this.direction = OutilsAnimation.GAUCHE;
                            break;
                        case "droit":
                            this.direction = OutilsAnimation.DROITE;
                            break;
                        default:
                        case "bas":
                            this.direction = OutilsAnimation.BAS;
                            break;
                    }
                }
            }
        }
    }

    public int getNombrePieces() {
        return nombrePieces;
    }

    public int getxPerso() {
        return xPerso;
    }

    public int getyPerso() {
        return yPerso;
    }

    public int getDirection() {
        return direction;
    }

    public String getNiveauSuivant() {
        return niveauSuivant;
    }

    /**
     * Affiche la carte sans la porte
     */
    public void renderSansPorte() {
        this.render(0, 0, this.getLayerIndex("fond"));
        this.render(0, 0, this.getLayerIndex("murs"));
    }

    /**
     * Affiche la porte
     */
    public void renderPorte() {
        this.render(0, 0, this.getLayerIndex("porte"));
    }

    /**
     * Affiche la carte avec la porte
     */
    public void renderAvecPorte() {
        this.renderSansPorte();
        this.renderPorte();
    }

    /**
     * Détecte une collision avec une tolérance epsilon
     */
    public boolean isCollisionPorte(int xPerso, int yPerso) {
        return (Math.abs(xPerso - xPorte) < EPSILON && Math.abs(yPerso - yPorte) < EPSILON);
    }

    /**
     * Détecte s'il y a un mur aux coordonées x,y
     * x et y sont des entiers et correspondent aux tuiles de la carte
     */
    public boolean isMur(int x, int y) {
        int   logicLayer = getLayerIndex("murs");
        Image tile1      = null;
        tile1 = getTileImage(x, y, logicLayer);
        return (tile1 != null);

    }
}
