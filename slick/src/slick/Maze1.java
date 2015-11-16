package slick;

import java.util.ArrayList;
import java.util.List;

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
import animation.AnimationPerso;
import animation.OutilsAnimation;


public class Maze1 extends BasicGameState {

	private TiledMap map;
	private float xPerso, yPerso;
	private int direction = OutilsAnimation.BAS;
	private boolean moving = false;
	private Animation[] animations = new Animation[8];
	private List<Piece> pieces;
	private int[][] matrice;
	private long chrono = 0,chrono2;
    private int tempsEcoule = 180,ancienneDuree;
    private Audio a;
    
	public Maze1(String cheminCarte, int xTuilePerso, int yTuilePerso, int[][] matrice) throws SlickException {
		this.map = new TiledMap(cheminCarte);
		this.xPerso=xTuilePerso*map.getTileWidth();
		this.yPerso=yTuilePerso*map.getTileHeight();
		this.matrice = matrice;
	}


	@Override
	public void init(GameContainer arg0, StateBasedGame game) throws SlickException {
		// on adapte la taille de la fenetre à la taille de la map
	    StateGame.container.setDisplayMode(map.getWidth() * map.getTileWidth(), map.getHeight() * map.getTileHeight(),false);
		this.animations=AnimationPerso.getInstance();
		remplirLabyrinthe();
		chrono = java.lang.System.currentTimeMillis() ;
		a = Audio.getSon("Sons/24118.wav");
		a.loop();
	}

	

	@Override
	public void render(GameContainer arg0, StateBasedGame game, Graphics graphic) throws SlickException {
		this.map.render(0, 0);
		graphic.setColor(Color.white);
		chrono2 = java.lang.System.currentTimeMillis() ;
		int duree = (int)((chrono2 - chrono)*0.001) - this.ancienneDuree;
		this.tempsEcoule = this.tempsEcoule - duree;
		graphic.drawString("Time = "+this.tempsEcoule, 30, 40);
		graphic.setColor(new Color(0, 0, 0, .5f));
		graphic.fillOval(xPerso + 16, yPerso + 52, 32, 16);
		graphic.drawAnimation(animations[direction + (moving ? 4 : 0)], xPerso, yPerso);
		detecterCollisionPiece();
		for(int i=0; i<pieces.size();i++){
			pieces.get(i).dessiner(graphic);
		}
		this.ancienneDuree = (int)((chrono2 - chrono)*0.001);
		if(this.tempsEcoule == 0) {
			a.Stop();
			game.enterState(States.LOST);
			}
		verifierGain(game);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame game, int delta) throws SlickException {
		if (this.moving) {
			// on commence par calculer les coordonnees du prochain point
	        float futurX = getFuturX(delta);
	        float futurY = getFuturY(delta);
	        // puis on regarde s'il y a une collision avec un mur
	        boolean collision = isCollision(futurX, futurY);
	        if (collision) {
	            this.moving = false;
	        } else {
	            this.xPerso = futurX;
	            this.yPerso = futurY;
	        }
	    }
	}

	public void verifierGain(StateBasedGame game){
		if(pieces.size() == 0) {
			a.Stop();
			game.enterState(States.WIN);}
	}
	
	private boolean isCollision(float x, float y) {
		int tileW = this.map.getTileWidth();
		int tileH = this.map.getTileHeight();
		int logicLayer = this.map.getLayerIndex("murs");
		Image tile = null;
		switch (this.direction){
			case OutilsAnimation.HAUT:tile=this.map.getTileImage((int) (x+tileW*1) / tileW, (int) (y+tileH) / tileH, logicLayer);break;
			case OutilsAnimation.BAS:tile=this.map.getTileImage((int) (x+tileW*1) / tileW, (int) (y+tileH*2) / tileH, logicLayer);break;
			case OutilsAnimation.GAUCHE:tile=this.map.getTileImage((int) (x+tileW*0.5) / tileW, (int) (y+tileH*1.5) / tileH, logicLayer);break;
			case OutilsAnimation.DROITE:tile=this.map.getTileImage((int) (x+tileW*1.5) / tileW, (int) (y+tileH*1.5) / tileH, logicLayer);break;
		}
		
		boolean collision = tile != null;
		return collision;
	}

	private float getFuturX(int delta) {
		float futurX = this.xPerso;
		switch (this.direction) {
		case OutilsAnimation.GAUCHE:
			futurX = this.xPerso - .1f * delta;
			break;
		case OutilsAnimation.DROITE:
			futurX = this.xPerso + .1f * delta;
			break;
		}
		return futurX;
	}

	private float getFuturY(int delta) {
		float futurY = this.yPerso;
		switch (this.direction) {
		case OutilsAnimation.HAUT:
			futurY = this.yPerso - .1f * delta;
			break;
		case OutilsAnimation.BAS:
			futurY = this.yPerso + .1f * delta;
			break;
		}
		return futurY;
	}
	
	public void remplirLabyrinthe(){
		pieces = new ArrayList<Piece>();
		for(int i=0; i<24; i++){
			for(int j=0; j<24; j++){
				if(this.matrice[i][j]==0) pieces.add(new Piece(i*map.getTileWidth(),j*map.getTileHeight()));
			}
		}
		
	}
	
	public void detecterCollisionPiece(){
		for(int i=0; i<pieces.size();i++){
			if((int)(this.xPerso/map.getTileWidth()) == (int)(this.pieces.get(i).getPositionX()/map.getTileWidth() -1) && (int)(this.yPerso/map.getTileHeight()) == (int)(this.pieces.get(i).getPositionY()/map.getTileHeight() -1))
				this.pieces.remove(i);
				
		}
	}

	@Override
	public int getID() {
		return States.GAME;
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			this.direction = OutilsAnimation.HAUT;
			this.moving = true;
			break;
		case Input.KEY_LEFT:
			this.direction = OutilsAnimation.GAUCHE;
			this.moving = true;
			break;
		case Input.KEY_DOWN:
			this.direction = OutilsAnimation.BAS;
			this.moving = true;
			break;
		case Input.KEY_RIGHT:
			this.direction = OutilsAnimation.DROITE;
			this.moving = true;
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		this.moving = false;
	}

}
