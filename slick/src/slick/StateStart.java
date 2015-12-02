package slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateStart extends BasicGameState {

	private Image background;
	private StateBasedGame game;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.game = game;
		this.background = new Image("ressources/background.png");
	}

	/**
	 * Fonction d'affichage qui s'execute à chaque boucle de jeu
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0, container.getWidth(), container.getHeight());
		g.drawString("Appuyer sur une touche", 300, 300);
	}

	/**
	 * La fonction update sert à  mettre à jour les données 
	 * Ici on n'a pas de données donc il n'y a rien à faire
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
	}

	@Override
	public void keyReleased(int key, char c) {
		game.enterState(States.GAME);
		
		
	}

	/**
	 * L'identifiant permet d'identifier les différentes boucles. Pour passer de
	 * l'une à l'autre.
	 */

	@Override
	public int getID() {
		return States.START;
	}

}
