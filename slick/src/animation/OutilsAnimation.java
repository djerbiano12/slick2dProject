package animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public final class OutilsAnimation {



	public static final int HAUT = 0;
	public static final int GAUCHE = 1;
	public static final int BAS = 2;
	public static final int DROITE = 3;

	
	
	public static Animation[] initAnimation(int n, String fichier, int tuile_w, int tuile_h) throws SlickException {
		Animation[] animations = new Animation[n];
		SpriteSheet spriteSheet = new SpriteSheet("map/tuiles/perso.png",tuile_w,tuile_h);
		animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
		animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
		animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
		animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
		animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
		animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
		animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
		animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
		
		return animations;
	}
	
	
	private static Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}
}
