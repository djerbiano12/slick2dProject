package model;

import org.newdawn.slick.Animation;
import org.newdawn.slick.tiled.TiledMap;
import animation.OutilsAnimation;

public abstract class ElementJeuMobile extends ElementJeu {

	
	private int direction;
	private boolean moving;
	protected Animation[] animations;
	
	public ElementJeuMobile(int positionX, int positionY, int width, int height) {
		super(positionX, positionY, width, height);
		setDirection(OutilsAnimation.BAS);
		setMoving(false);
	}

	
	public Animation[] getAnimations() {
		return animations;
	}

	public void setAnimations(Animation[] animations) {
		this.animations = animations;
	}



	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public int getFuturX(int delta) {
		float futurX = this.getX();
		switch (this.direction) {
		case OutilsAnimation.GAUCHE:
			futurX = this.getX() - .1f * delta;
			break;
		case OutilsAnimation.DROITE:
			futurX = this.getX() + .15f * delta;
			break;
		}
		return (int) futurX;
	}
	
	public int getFuturY(int delta) {
		float futurY = this.getY();
		switch (this.direction) {
		case OutilsAnimation.HAUT:
			futurY =  this.getY() - .1f * delta;
			break;
		case OutilsAnimation.BAS:
			futurY = this.getY() + .15f * delta;
			break;
		}
		return (int) futurY;
	}
	
	
	public void update(int delta, TiledMap map ){
		if(this.moving)
		{
			int futurX = getFuturX(delta);
			int futurY = getFuturY(delta);
			// puis on regarde s'il y a une collision avec un mur
			boolean collision = isCollision(futurX, futurY, map);
			if (collision) {
				this.moving=false;
			} else {
				this.positionX = futurX;
				this.positionY = futurY;
			}
		}
		
		
	}
	
	public abstract boolean isCollision(float x, float y, TiledMap map);
	
	
}
