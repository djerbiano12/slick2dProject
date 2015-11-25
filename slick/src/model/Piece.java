package model;


import org.newdawn.slick.Graphics;

public class Piece extends ElementJeuStatique{

	public Piece(int positionX, int positionY) {
		super(positionX, positionY,32,32, "ressources/piece.png");
	}
	//////////////////////////
	/// Mettre dans la vue 
	/////////////////////////
	public void dessiner(Graphics g){
	g.drawImage(image, this.positionX, this.positionY);
	}


}
