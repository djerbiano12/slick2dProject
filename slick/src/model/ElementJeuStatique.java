package model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

abstract class ElementJeuStatique extends ElementJeu {

	Image image;
	public ElementJeuStatique(int positionX, int positionY, int width, int height, String cheminImage) {
		super(positionX,positionY, width, height);
		try {
			image= new Image(cheminImage,false,Image.FILTER_NEAREST);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors du chargement de l'image de la pièce");
			e.printStackTrace();
		}
	}

}