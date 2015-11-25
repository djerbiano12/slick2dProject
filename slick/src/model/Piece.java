package model;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Piece {
	
	private int positionX,positionY;
	
	public Piece() {
		// TODO Auto-generated constructor stub
	}

 	public Piece(int positionX, int positionY) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
	}
 	
	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public void dessiner(Graphics g){
		try {
			g.drawImage(loadImage("ressources/piece.png"), this.positionX, this.positionY);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Image loadImage(String path) throws SlickException{
		return new Image(path,false,Image.FILTER_NEAREST);
		
	}

}
