package animation;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

public class AnimationPerso {
	private static  AnimationPerso instance;
	

	private static Animation[] animation;
	

	public AnimationPerso() throws SlickException {
		super();
		animation=OutilsAnimation.initAnimation(8,"map/tuiles/perso.png", 64, 64);
	}



	public static Animation[] getInstance()  {
  
            if (instance == null)
				try {
					instance = new AnimationPerso();
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
           
            return animation;
	}


	
	
}
