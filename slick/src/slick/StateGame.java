package slick;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/*
 * Classe principale qui lance le jeu (fonction main) 
 * et g�re les diff�rentes phases de jeu (cf initStatesList)
 */
public class StateGame extends StateBasedGame {
	
	public static final int WIDTH =  800;
	public static final int HEIGHT =  600;
	public static AppGameContainer container;

	public StateGame() {
		super("Mon premier jeu");
	} 


	/*
	 * Cette methode permet d'ajouter des phases à notre jeu 
	 * � l'appel de la méthode, toutes les instances sont crées
	 */
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new StateStart());
		addState(new MazeGame("map/maze1.tmx"));
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
		} 
	}

} // fin de classe
