package flappybirds;

import java.awt.Rectangle;
import java.io.File;

import pkg2dgamesframework.Objects;
import pkg2dgamesframework.SoundPlayer;

public class Bird extends Objects{
	
	private float v = 0;
	
	private boolean isFlying = false;
	
	private Rectangle rect;
	
	private boolean isLive = true;
	public SoundPlayer flapSound, bupSound, getpointSound;
	
	public Bird(int x, int y, int w, int h) {
		super(x,y,w,h);
		rect = new Rectangle(x, y, w, h);
		
		flapSound = new SoundPlayer(new File("Asserts/flap.wav"));
		bupSound = new SoundPlayer(new File("Asserts/bup.wav"));
		getpointSound = new SoundPlayer(new File("Asserts/getpoint.wav"));
		
	}
	
	public void setLive(boolean b) {
		isLive = b;
	}
	public boolean getLive() {
		return isLive;
	}
	
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void setV (float v) {
	this.v = v;	
	}
    public void update (long deltaTime){
	     v += FlappyBird.gravity; 
	     this.setPosY(getPosY() + v);
	     this.rect.setLocation((int)this.getPosX(), (int)this.getPosY());
	     
	     if (v<0) isFlying = true;
	     else isFlying = false;
}
    public void fly() {
    	v = -3;
    	flapSound.play();
    	
    }
    public boolean getisFlying() {
    	return isFlying;
    }
}
