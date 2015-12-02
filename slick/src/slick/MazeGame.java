package slick;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Personnage;
import model.Piece;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Son.Audio;
import animation.OutilsAnimation;

public class MazeGame extends BasicGameState {

	private final int TEMPS_JEU = 90; // en secondes
	public static final String COUCHE_LOGIQUE = "murs"; // nom de la couche qui
														// contient les murs de
														// la carte
	private final int EPSILON = 40; // tolerance pour la collision pièce - perso
	
	public static final String SON_FILE = "Sons/24118.wav";
	private MaTiledMap map;
	Personnage perso;
	private List<Piece> pieces;
	private long chrono = 0, chrono2;
	private int tempsEcoule, ancienneDuree;
	private Audio audio;
	boolean fin;
	GameContainer container;
	StateBasedGame game;
	
	

	public MazeGame(String cheminCarte)
			throws SlickException {
	
		this.map = new MaTiledMap(cheminCarte);
		audio = Audio.getSon(SON_FILE);
		this.monInit();
	}



	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		this.container=arg0;
		this.game=arg1;
		
	}



	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		super.enter(container, game);
		this.ajusteTailleFenetre();
		chrono = java.lang.System.currentTimeMillis();
		this.tempsEcoule = TEMPS_JEU;
		audio.loop();
		

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame game, Graphics graphic)
			throws SlickException {
		
		
		/* On commence par dessiner la map*/
		if (fin)
		{
			/* Le joueur a terminé le niveau, on ouvre la porte et on regarde s'il atteint la porte*/
			this.map.renderSansPorte();
			int xPerso = (int) perso.getX() + perso.getWidth() / 2;
			int yPerso = (int) perso.getY() + perso.getHeight() / 2;
			if (this.map.isCollisionPorte(xPerso,yPerso))
				this.changerNiveau();
			
		}
		else 
			 /* Le joueur n'a pas terminé le niveau, la porte reste fermee*/
			this.map.renderAvecPorte();
		
		dessinePersonnage(graphic);
		affichePieces(graphic);
		afficherChrono(graphic, game);


	}
	
	private void monInit(){
		remplirLabyrinthe();
		int xPerso = map.getxPerso();
		int yPerso =  map.getyPerso();
		int direction= map.getDirection();
		this.perso = new Personnage(xPerso, yPerso,direction);
		fin=false;
	}


	public void dessinePersonnage(Graphics graphic){
		/* On dessine le personnage, slick s'occupe de charger la bonne image de l'animation*/
		graphic.drawAnimation(perso.getAnimations()[perso.getDirection()
				+ (perso.isMoving() ? 4 : 0)], perso.getX(), perso.getY());
		
		/* On dessine l'ombre du personnage*/
		graphic.setColor(new Color(0, 0, 0, .5f));
		graphic.fillOval(perso.getX() + 16, perso.getY() + 52, 32, 16);
		
	}
	
	public void affichePieces(Graphics graphic){
		for (int i = 0; i < pieces.size(); i++)
			pieces.get(i).dessiner(graphic);
		
	}
	
	
	public void afficherChrono(Graphics graphic,StateBasedGame game){
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
		
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame game, int delta)
			throws SlickException {
		CollisionPiecePerso();
		verifierGain(game);
		// on commence par calculer les coordonnees du prochain point
		this.perso.update(delta, map);

	}

	public void verifierGain(StateBasedGame game) throws SlickException {
		if (pieces.size() == 0) {
			this.fin=true;

			
		}
	}

	private boolean detecterMurs(int x, int y) {
		int logicLayer = this.map.getLayerIndex("murs");
		Image tile1 = null;
		tile1 = this.map.getTileImage(x, y, logicLayer);
		return (tile1 != null);

	}


	public void remplirLabyrinthe() {
		pieces = new ArrayList<Piece>();
		int posxAleatoire, posyAleatoire;
		int tileW = map.getTileWidth(); // largeur d'une tuile
		int tileH = map.getTileHeight();// hauteur d'une tuile
		Random rand = new Random();
		for (int i = 0; i < map.getNombrePieces(); i++) {

			/********************************************************************************
			 * Generer aleatoirement les positions X et Y des boules a manger
			 * formule utilisee -> int valeur = valeurMin + r.nextInt(valeurMax
			 * - valeurMin)
			 ********************************************************************************/
			do {
				posxAleatoire = 1 + rand.nextInt(this.map.getWidth() - 3);
				posyAleatoire = 1 + rand.nextInt(this.map.getWidth() - 3);
			} while (detecterMurs(posxAleatoire, posyAleatoire));
			pieces.add(new Piece(posxAleatoire * tileW, posyAleatoire * tileH));
		}

	}

	public void CollisionPiecePerso() {
		// Pour détecter une collision entre le perso et une pièce, on considère
		// le centre des deux images
		for (int i = 0; i < pieces.size(); i++) {
			Piece piece = this.pieces.get(i);
			int xPiece = piece.getX() + piece.getWidth() / 2;
			int yPiece = piece.getY() + piece.getHeight() / 2;
			int xPerso = (int) perso.getX() + perso.getWidth() / 2;
			int yPerso = (int) perso.getY() + perso.getHeight() / 2;
			// on regarde s'ils sont proche avec une tolérance epsilon
			if (Math.abs(xPerso - xPiece) < EPSILON
					&& Math.abs(yPerso - yPiece) < EPSILON)
				this.pieces.remove(i);
		}
	}

	void changerNiveau() {
		audio.Stop();
		try {
			this.map = new MaTiledMap("map/"+this.map.getNiveauSuivant()+".tmx");
			monInit();
			enter(container, game);
			
		} catch (Exception e) {
			// Le chargement de la prochaine carte n'a pas réussi, on arrete le jeu
			game.enterState(States.WIN);
		}
		
		
		
	}

	@Override
	public int getID() {
		return States.GAME;
	}

	/* Quand on relache une touche de direction, la direction du personnage change et il se deplace*/
	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			perso.setDirection(OutilsAnimation.HAUT);
			break;
		case Input.KEY_LEFT:
			perso.setDirection(OutilsAnimation.GAUCHE);
			break;
		case Input.KEY_DOWN:
			perso.setDirection(OutilsAnimation.BAS);
			break;
		case Input.KEY_RIGHT:
			perso.setDirection(OutilsAnimation.DROITE);
			break;
		}
		perso.setMoving(true);
	}

	/* Quand on relache une touche de direction, le personnage s'arrete*/
	@Override
	public void keyReleased(int key, char c) {
		perso.setMoving(false);
	}

	/*
	 * Redimentionne la fenetre pour qu'elle s'adapte à la taille du labyrinthe
	 */
	private void ajusteTailleFenetre() {
		try {
			StateGame.container.setDisplayMode(
					map.getWidth() * map.getTileWidth(),
					map.getHeight() * map.getTileHeight(), false);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			System.out.println("impossible de redimentionner la fenetre");
			e.printStackTrace();
		}
	}




}
