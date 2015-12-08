package slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateStart extends BasicGameState {
	
	// TODO Implémenter
	/*
	 * Travail demandé:
	 *    Ajouter une image de fond avec un texte signalant qu'on doit taper un caractere quelconque au clavier pour 
	 * 	  démarrer le jeux.
	 * 
	 * Aides:
	 * 
	 * 1) La classe Image a pour constructeur Image(String lien): le lien se construit comme çela: ressources/nomImage
	 * 
	 * 2) La classe Image dispose d'une méthode de dessin d'images qui est:
	 * 	  void draw(int positionX, int positionY,int LargeurImage,int HauteurImage);
	 * 					positionX et positionY définissent la postion de l'image dans le conteneur du jeu.
	 * 
	 * 4) La classe Graphics dispose d'une méthode qui permet d'afficher un texte sur le conteneur du jeu à une postion donnée:
	 * 					void drawString(String texteAAfficher, int postionX, int positionY); 
	 * 								positionX = 300 et positionY = 300 dans notre cas
	 *
	 * 
	 * 5) La classe game possède une fonction qui permet d'entrer dans un nouvel état:
	 * 					void enterState(int idNouvelEtat)
	 * 					Utiliser les identifiants déclarés dans la classe States.java
	 * 
	 *  6) La fonction void keyReleased(int key, char c) est appelée lorsqu'on tape un caractère au clavier
	 */
	
    private Image          background;
    private StateBasedGame game;

    @Override
    public void init(GameContainer container, StateBasedGame game)
    throws SlickException {
        this.game = game;
        //this.background = new Image("ressources/background.png");
    }

    /**
     * Fonction d'affichage qui s'execute Ã  chaque boucle de jeu
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    throws SlickException {
        /*background.draw(0, 0, container.getWidth(), container.getHeight());
        g.drawString("Appuyer sur une touche", 300, 300);*/
    }

    /**
     * La fonction update sert Ã   mettre Ã  jour les donnÃ©es
     * Ici on n'a pas de donnÃ©es donc il n'y a rien Ã  faire
     */
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        //
    }

    @Override
    public void keyReleased(int key, char c) {
        //game.enterState(States.GAME);
    }

    /**
     * L'identifiant permet d'identifier les diffÃ©rentes boucles. Pour passer de
     * l'une Ã  l'autre.
     */
    @Override
    public int getID() {
        //return States.START;
    }
}
