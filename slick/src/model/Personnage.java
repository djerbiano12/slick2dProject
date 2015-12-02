package model;

import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

import slick.MazeGame;
import animation.AnimationPerso;

public class Personnage extends ElementJeuMobile {
    public Personnage(int positionX, int positionY, int direction) {
        super(positionX, positionY, 64, 64, direction);

        animations = AnimationPerso.getInstance();
    }

    /**
     * Detecte une collision entre le personnage et un mur
     */
    public boolean isCollision(float x, float y, TiledMap map) {
        int tileW      = map.getTileWidth(); // largeur d'une tuile
        int tileH      = map.getTileHeight(); // hauteur d'une tuile
        int logicLayer = map.getLayerIndex(MazeGame.COUCHE_LOGIQUE);

        Image tileHaut = map.getTileImage((int) (x + tileW) / tileW, (int) (y + tileH) / tileH, logicLayer);
        Image tileBas = map.getTileImage((int) (x + tileW) / tileW, (int) (y + tileH * 2) / tileH, logicLayer);
        Image tileGauche = map.getTileImage((int) (x + tileW * 0.5) / tileW, (int) (y + tileH * 1.5) / tileH, logicLayer);
        Image tileDroite = map.getTileImage((int) (x + tileW * 1.5) / tileW, (int) (y + tileH * 1.5) / tileH, logicLayer);

        return ((tileHaut != null) || (tileBas != null) || (tileGauche != null) || (tileDroite != null));
    }
}
