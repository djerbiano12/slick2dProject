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
	private final int EPSILON = 40; // tolerance pour la collision pi�ce - perso
	
	public static final String SON_FILE = "Sons/24118.wav";
	private MaTiledMap map;
	Personnage perso;
	private List<Piece> pieces;
	private float tempsRestant; // en secondes
	private Audio audio;
	boolean fin; // si le joueur a récolté  toutes les pi�ces
	GameContainer container;
	StateBasedGame game;
	
	

	public MazeGame(String cheminCarte)
			throws SlickException {
		initNiveau(cheminCarte);
		audio = Audio.getSon(SON_FILE);
	}



	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		this.container=arg0;
		this.game=arg1;
		remplirLabyrinthe();
		
	}

	/* 
	 * Cette fonction est appelée a chaque changement de niveau
	 */
	public void initNiveau(String cheminCarte) throws SlickException{
		this.map = new MaTiledMap(cheminCarte);
		int xPerso = map.getxPerso();
		int yPerso =  map.getyPerso();
		int direction= map.getDirection();
		this.perso = new Personnage(xPerso, yPerso,direction);
		fin=false;
		
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		super.enter(container, game);
		this.ajusteTailleFenetre();
		this.tempsRestant = TEMPS_JEU;
		audio.loop();
		

	}

	/**
	 * Fonction d'affichage qui s'execute à chaque boucle de jeu
	 */
	@Override
	public void render(GameContainer arg0, StateBasedGame game, Graphics graphic)
			throws SlickException {
		
		
		/* On commence par dessiner la map*/
		if (fin)
		{
			/* Le joueur a terminé le niveau, on ouvre la porte et on regarde s'il atteint la porte*/
			this.map.renderSansPorte();

			
		}
		else 
			 /* Le joueur n'a pas terminé le niveau, la porte reste fermée*/
			this.map.renderAvecPorte();
		
		afficherPersonnage(graphic);
		afficherPieces(graphic);
		afficherChrono(graphic, game);


	}	
	
	/**
	 * La fonction update sert à  mettre à jour les données 
	 * Ici on n'a pas de données donc il n'y a rien à faire
	 */
	@Override
	public void update(GameContainer arg0, StateBasedGame game, int delta)
			throws SlickException {
		this.updateChrono(delta);
		this.perso.update(delta, map);	
		if(fin)
		{
			/* 
			 * si le joueur a ramass� toutes les pièces, on verifie s'il sort du niveau
			 * S'il sort on passe au niveau suivant
			*/
			if (collisionPortePerso())
				this.changerNiveau();
		}
		else
		{
			/* 
			 * si le joueur a n'a pas ramassé toutes les pièces, on gère la collision avec les pièces
			*/
			CollisionPiecePerso();		
		}

		verifierGain(game);
		

	}
	
	/*
	 * Détecte si le personnage passe par la porte pour passer au niveau suivant
	 */
	private boolean collisionPortePerso()
	{
		int xPerso = (int) perso.getX() + perso.getWidth() / 2;
		int yPerso = (int) perso.getY() + perso.getHeight() / 2;
		return (this.map.isCollisionPorte(xPerso,yPerso));
	}


	/*
	 * Affiche le personnage
	 */
	public void afficherPersonnage(Graphics graphic){
		/* On dessine le personnage, slick s'occupe de charger la bonne image de l'animation*/
		graphic.drawAnimation(perso.getAnimations()[perso.getDirection()
				+ (perso.isMoving() ? 4 : 0)], perso.getX(), perso.getY());
		
		/* On dessine l'ombre du personnage*/
		graphic.setColor(new Color(0, 0, 0, .5f));
		graphic.fillOval(perso.getX() + 16, perso.getY() + 52, 32, 16);
		
	}
	
	/*
	 * Affiche toutes les pièces
	 */
	public void afficherPieces(Graphics graphic){
		for (int i = 0; i < pieces.size(); i++)
			pieces.get(i).dessiner(graphic);
		
	}
	
	/*
	 * Affiche le chronomètre
	 */
	public void afficherChrono(Graphics graphic,StateBasedGame game){
		graphic.setColor(Color.white);
		graphic.drawString("Temps restant = " + (int)this.tempsRestant, 30, 40);	
		
	}
	
	/*
	 * Modifie le chronomètre
	 */
	public void updateChrono(int delta){
		this.tempsRestant =   (float) (tempsRestant - delta*0.001);
		if (this.tempsRestant <= 0) 
		{
			audio.Stop();
			game.enterState(States.LOST);
		}
		
	}
	

	/*
	 * Vérifie si le joueur a ramassé toutes les pièces
	 */
	public void verifierGain(StateBasedGame game) throws SlickException {
		if (pieces.size() == 0) {
			this.fin=true;
		}
	}




	/*
	 * Place aléatoirement des pièces dans le labyrinthe
	 * Le nombre de pièces est a déterminer dans les propriétés de la carte
	 */
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
			} while (map.isMur(posxAleatoire, posyAleatoire));
			pieces.add(new Piece(posxAleatoire * tileW, posyAleatoire * tileH));
		}

	}

	/*
	 * Detecte s'il y a une collision entre le personnage et une pièce
	 */
	public void CollisionPiecePerso() {
		// Pour détecter une collision entre le perso et une pièce, on considère le centre des deux images
		for (int i = 0; i < pieces.size(); i++) {
			Piece piece = this.pieces.get(i);
			int xPiece = piece.getX() + piece.getWidth() / 2;
			int yPiece = piece.getY() + piece.getHeight() / 2;
			int xPerso = (int) perso.getX() + perso.getWidth() / 2;
			int yPerso = (int) perso.getY() + perso.getHeight() / 2;
			// on regarde s'ils sont proche avec une tol�rance epsilon
			if (Math.abs(xPerso - xPiece) < EPSILON
					&& Math.abs(yPerso - yPiece) < EPSILON)
				this.pieces.remove(i);
		}
	}

	/*
	 * Appelé quand on veut changer de niveau
	 * La fonction cherche elle même la prochaine carte a charger
	 */
	void changerNiveau() {
		audio.Stop();
		try {
			initNiveau("map/"+this.map.getNiveauSuivant()+".tmx");
			enter(container, game);
			
		} catch (Exception e) {
			// Le chargement de la prochaine carte n'a pas réussi, on arrete le jeu
			game.enterState(States.WIN);
		}
		
		
		
	}
	
	
	/**
	 * L'identifiant permet d'identifier les différentes boucles. Pour passer de
	 * l'une à l'autre.
	 */
	@Override
	public int getID() {
		return States.GAME;
	}

	/* 
	 * Quand on relache une touche de direction, la direction du personnage change et il se deplace
	 */
	@Override
	public void keyPressed(int key, char c) {
		if(tempsRestant!=TEMPS_JEU) // si le jeu a deja commencé
		{
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
	}

	/* 
	 * Quand on relache une touche de direction, le personnage s'arrete
	 */
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
