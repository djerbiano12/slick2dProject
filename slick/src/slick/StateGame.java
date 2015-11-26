package slick;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class StateGame extends StateBasedGame {
	
	public static final int WIDTH =  1280;
	public static final int HEIGHT =  768;
	public static AppGameContainer container;

	public StateGame() {
		super("Mon premier jeu");
	} // le constructeur de la classe



	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		//addState(new Maze1( "map/maze1.tmx",1,1));
		addState(new Maze1( "map/maze1.tmx",10,1));
		addState(new StateLoss());
		addState(new StateWin());
	}
	
	public static void main(String[] args) {
		try {
			
			container = new AppGameContainer(new StateGame());
			container.setTargetFrameRate(60); // on règle le FrameRate
			container.start(); // on démarre le container
		} catch (SlickException e) {
			e.printStackTrace();
		} // l'exception de base de slick !!
	}

} // fin de classe
