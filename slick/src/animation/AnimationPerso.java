package animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

public class AnimationPerso {
    private static AnimationPerso instance;
    private static Animation[]    animation;
    private static final int NOMBRE_ETATS_ANIMATION = 8;
    private static final int TUILE_LARGEUR          = 64;
    private static final int TUILE_HAUTEUR          = 64;

    private AnimationPerso() throws SlickException {
        super();
        animation = OutilsAnimation.initAnimation(NOMBRE_ETATS_ANIMATION, "map/tuiles/perso.png", TUILE_LARGEUR, TUILE_HAUTEUR);
    }

    public static Animation[] getInstance() {
        if (instance == null) {
            try {
                instance = new AnimationPerso();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        return animation;
    }
}
