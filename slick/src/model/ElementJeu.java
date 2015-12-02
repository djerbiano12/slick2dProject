package model;

abstract class ElementJeu {

	
	protected int positionX,positionY; //positions de l'élément en pixels
	protected int width,height;
	


	public ElementJeu(int positionX, int positionY, int width, int height) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return positionX;
	}

	public void setX(int positionX) {
		this.positionX = positionX;
	}

	public int getY() {
		return positionY;
	}

	public void setY(int positionY) {
		this.positionY = positionY;
	}
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
