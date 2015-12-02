package model;


import org.newdawn.slick.Graphics;

public class Piece extends ElementJeuStatique{

	public static final String IMAGE_PIECE = "ressources/piece.png";
	public Piece(int positionX, int positionY) {
		super(positionX, positionY,32,32, Piece.IMAGE_PIECE);
	}
	//////////////////////////
	/// Mettre dans la vue 
	/////////////////////////
	public void dessiner(Graphics g){
	g.drawImage(image, this.positionX, this.positionY);
	}


}