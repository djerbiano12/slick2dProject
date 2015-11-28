package slick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Personnage;
import model.Piece;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import Son.Audio;
import animation.OutilsAnimation;

public class MazeGame extends BasicGameState {

	private final int NBR_PIECE = 20;
	private final int TEMPS_JEU = 90; // en secondes
	public static final String COUCHE_LOGIQUE = "murs";	// nom de la couche qui contient les murs de la carte
	private final int EPSILON = 40; // tolerance pour la collision pièce - perso
	public static final String URL = "map/maze";
	public static final int NOMBRE_DE_NIVEAUX = 2;
	public static final String SON_FILE = "Sons/24118.wav";
	private TiledMap map;
	Personnage perso;
	private List<Piece> pieces;
	private long chrono = 0, chrono2;
	private int tempsEcoule, ancienneDuree;
	private Audio audio;
	public int niveauActuel;
	Random rand = new Random();
	

	public MazeGame(String cheminCarte, int xTuilePerso, int yTuilePerso)
			throws SlickException {
		this.map = new TiledMap(cheminCarte);
		int xPerso = xTuilePerso * map.getTileWidth();
		int yPerso = yTuilePerso * map.getTileHeight();
		this.perso=new Personnage(xPerso, yPerso);	
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame game)
			throws SlickException {
		this.niveauActuel = 1;
		remplirLabyrinthe();

	}

	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		super.enter(container, game);
		this.ajusteTailleFenetre();
		chrono = java.lang.System.currentTimeMillis();
		this.tempsEcoule=TEMPS_JEU;
		audio = Audio.getSon(SON_FILE);
		audio.loop();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame game, Graphics graphic)
			throws SlickException {
		this.map.render(0, 0);
		
		graphic.setColor(new Color(0, 0, 0, .5f));
		graphic.fillOval(perso.getX() + 16, perso.getY() + 52, 32, 16);
		graphic.drawAnimation(perso.getAnimations()[perso.getDirection() + (perso.isMoving() ? 4 : 0)], perso.getX(),
				perso.getY());
		graphic.drawRect(perso.getX(),perso.getY(), 64, 64);
		
		CollisionPiecePerso();
		for (int i = 0; i < pieces.size(); i++) {
			pieces.get(i).dessiner(graphic);
			
			//////////////////////////////////////
			///   Marquage de debug
			//////////////////////////////////////
			graphic.setColor(Color.red);
			graphic.fillOval(pieces.get(i).getX() , pieces.get(i).getY() , 7, 7);
			graphic.setColor(Color.black);
			graphic.drawRect(pieces.get(i).getX(), pieces.get(i).getY(), 32, 32);
		}
		
		
		
		graphic.setColor(Color.white);
		chrono2 = java.lang.System.currentTimeMillis();
		int duree = (int) ((chrono2 - chrono) * 0.001) - this.ancienneDuree;
		this.tempsEcoule = this.tempsEcoule - duree;
		graphic.drawString("Time = " + this.tempsEcoule, 30, 40);
		this.ancienneDuree = (int) ((chrono2 - chrono) * 0.001);
		if (this.tempsEcoule == 0) {
			audio.Stop();
			game.enterState(States.LOST);
		}
		verifierGain(game);	
		//////////////////////////////////////
		///   Marquage de debug
		//////////////////////////////////////
		graphic.setColor(Color.red);
		graphic.fillOval(perso.getX(), perso.getY() , 7, 7);
		graphic.setColor(Color.black);
		graphic.drawRect(perso.getX(), perso.getY(), 64, 64);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame game, int delta)
			throws SlickException {
			// on commence par calculer les coordonnees du prochain point
		this.perso.update(delta, map);
		
	}

	public void verifierGain(StateBasedGame game) {
		if (pieces.size() == 0) {
			audio.Stop();
			if(this.niveauActuel == NOMBRE_DE_NIVEAUX) game.enterState(States.WIN);
			else{
				try {
				audio.loop();
				passerNiveau(trouverNiveau(this.niveauActuel), 10, 1);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				}
		}
	}

	private boolean detecterMurs(int x, int y) {
		int logicLayer = this.map.getLayerIndex("murs");
		Image tile1 = null;
		tile1 = this.map.getTileImage(x, y, logicLayer);
		return (tile1 != null);

	}

  public String trouverNiveau(int niveauActuel){
	  niveauActuel++;
	  return URL+niveauActuel+".tmx";
  }
	public void remplirLabyrinthe() {
		pieces = new ArrayList<Piece>();
		int posxAleatoire, posyAleatoire;
		int tileW = map.getTileWidth(); // largeur d'une tuile 
		int tileH = map.getTileHeight();// hauteur d'une tuile
		for (int i = 0; i < NBR_PIECE; i++) {

			/********************************************************************************
			 * Generer aleatoirement les positions X et Y des boules a manger
			 * formule utilisee -> int valeur = valeurMin + r.nextInt(valeurMax
			 * - valeurMin)
			 ********************************************************************************/
			do {
				posxAleatoire = 1+rand.nextInt(this.map.getWidth()-3);
				posyAleatoire = 1+rand.nextInt(this.map.getWidth()-3);
			} while (detecterMurs(posxAleatoire, posyAleatoire));
			pieces.add(new Piece(posxAleatoire*tileW, posyAleatoire*tileH));
		}

	}

	public void CollisionPiecePerso() {
		// Pour détecter une collision entre le perso et une pièce, on considère le centre des deux images
		for (int i = 0; i < pieces.size(); i++) {
			Piece piece = this.pieces.get(i);
			int xPiece = piece.getX()+piece.getWidth()/2;
			int yPiece = piece.getY()+piece.getHeight()/2;
			int xPerso = (int)perso.getX() +perso.getWidth()/2;
			int yPerso = (int)perso.getY() +perso.getHeight()/2;
			// on regarde s'ils sont proche avec une tolérance epsilon
			if (Math.abs(xPerso - xPiece)<EPSILON && Math.abs(yPerso - yPiece)<EPSILON)
				this.pieces.remove(i);
		}
	}
	
	public void passerNiveau(String cheminCarte,int X, int Y) throws SlickException{
		this.map = new TiledMap(cheminCarte);
		StateGame.container.setDisplayMode(map.getWidth() * map.getTileWidth(),map.getHeight() * map.getTileHeight(), false);
		this.perso.setX(X * map.getTileWidth());
		this.perso.setY(Y * map.getTileHeight());
		remplirLabyrinthe();
		this.tempsEcoule = TEMPS_JEU;
		this.niveauActuel ++;
	}

	

	@Override
	public int getID() {
		return States.GAME;
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP: perso.setDirection( OutilsAnimation.HAUT ) ;break;
		case Input.KEY_LEFT: perso.setDirection(  OutilsAnimation.GAUCHE);break;
		case Input.KEY_DOWN: perso.setDirection( OutilsAnimation.BAS); break;
		case Input.KEY_RIGHT: perso.setDirection(  OutilsAnimation.DROITE); break;
		}
		perso.setMoving(true);
	}

	@Override
	public void keyReleased(int key, char c) {
		perso.setMoving(false);
	}
	
	
	
	/*
	 * Redimentionne la fenetre pour qu'elle s'adapte à la taille du labyrinthe
	 */
	private void ajusteTailleFenetre() 
	{
		try {
			StateGame.container.setDisplayMode(map.getWidth() * map.getTileWidth(),
					map.getHeight() * map.getTileHeight(), false);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			System.out.println("impossible de redimentionner la fenetre");
			e.printStackTrace();
		}
	}

}
