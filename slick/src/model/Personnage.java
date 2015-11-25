package model;


import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

import slick.Maze1;
import animation.AnimationPerso;

public class Personnage extends ElementJeuMobile {

	public Personnage(int positionX, int positionY) {
		super(positionX, positionY,64,64);
		animations = AnimationPerso.getInstance();
	}

	
	public boolean isCollision(float x, float y, TiledMap map) {
		int tileW = map.getTileWidth(); // largeur d'une tuile 
		int tileH = map.getTileHeight();// hauteur d'une tuile
		int logicLayer = map.getLayerIndex(Maze1.COUCHE_LOGIQUE);
		// haut
		Image tile1 = map.getTileImage((int) (x + tileW * 1) / tileW, (int) (y + tileH) / tileH, logicLayer);
		// bas
		Image tile2 = map.getTileImage((int) (x + tileW * 1) / tileW, (int) (y + tileH * 2) / tileH, logicLayer);
		// gauche
		Image tile3 = map.getTileImage((int) (x + tileW * 0.5) / tileW, (int) (y + tileH * 1.5) / tileH, logicLayer);
		// droite
		Image tile4 = map.getTileImage((int) (x + tileW * 1.5) / tileW, (int) (y + tileH * 1.5) / tileH, logicLayer);

		return ((tile1 != null) || (tile2 != null) || (tile3 != null) || (tile4 != null));

	}
	
	
	
	
	
	
}
