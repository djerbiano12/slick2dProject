package slick;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateStart extends BasicGameState {
	
	// TODO Impl�menter
	/*
	 * Travail demand�:
	 *    Ajouter une image de fond avec un texte signalant qu'on doit taper un caractere quelconque au clavier pour 
	 * 	  d�marrer le jeux.
	 * 
	 * Aides:
	 * 
	 * 1) La classe Image a pour constructeur Image(String lien): le lien se construit comme �ela: ressources/nomImage
	 * 
	 * 2) La classe Image dispose d'une m�thode de dessin d'images qui est:
	 * 	  void draw(int positionX, int positionY,int LargeurImage,int HauteurImage);
	 * 					positionX et positionY d�finissent la postion de l'image dans le conteneur du jeu.
	 * 
	 * 4) La classe Graphics dispose d'une m�thode qui permet d'afficher un texte sur le conteneur du jeu � une postion donn�e:
	 * 					void drawString(String texteAAfficher, int postionX, int positionY); 
	 * 								positionX = 300 et positionY = 300 dans notre cas
	 *
	 * 
	 * 5) La classe game poss�de une fonction qui permet d'entrer dans un nouvel �tat:
	 * 					void enterState(int idNouvelEtat)
	 * 					Utiliser les identifiants d�clar�s dans la classe States.java
	 * 
	 *  6) La fonction void keyReleased(int key, char c) est appel�e lorsqu'on tape un caract�re au clavier
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
     * Fonction d'affichage qui s'execute à chaque boucle de jeu
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    throws SlickException {
        /*background.draw(0, 0, container.getWidth(), container.getHeight());
        g.drawString("Appuyer sur une touche", 300, 300);*/
    }

    /**
     * La fonction update sert à  mettre à jour les données
     * Ici on n'a pas de données donc il n'y a rien à faire
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
     * L'identifiant permet d'identifier les différentes boucles. Pour passer de
     * l'une à l'autre.
     */
    @Override
    public int getID() {
        //return States.START;
    }
}
