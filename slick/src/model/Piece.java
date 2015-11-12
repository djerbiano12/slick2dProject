package model;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Piece {
	
	private float positionX,positionY;
	
	public Piece() {
		// TODO Auto-generated constructor stub
	}

 	public Piece(float positionX, float positionY) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
	}
 	
	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public void dessiner(Graphics g){
		g.setColor(new Color(206,206,206));
		g.fillOval(this.positionX, this.positionY, 10, 10);	
	}

}
