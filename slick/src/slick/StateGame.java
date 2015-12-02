package slick;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Classe principale qui lance le jeu (fonction main)
 * et gère les différentes phases de jeu (cf initStatesList)
 */
public class StateGame extends StateBasedGame {
    public static AppGameContainer container;

    public StateGame() {
        super("Mon premier jeu");
    }

    /**
     * Cette methode permet d'ajouter des phases à notre jeu
     * À l'appel de la méthode, toutes les instances sont crées
     */
    @Override
    public void initStatesList(GameContainer arg0) throws SlickException {
        addState(new StateStart());
        addState(new MazeGame("map/maze1.tmx"));
        addState(new StateLoss());
        addState(new StateWin());
    }

    public static void main(String[] args) {
        final int FRAME_RATE = 60;

        try {
            container = new AppGameContainer(new StateGame());
            container.setTargetFrameRate(FRAME_RATE);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
