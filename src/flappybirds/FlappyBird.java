package flappybirds;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import pkg2dgamesframework.AFrameOnImage;
import pkg2dgamesframework.Animation;
import pkg2dgamesframework.GameScreen;

public class FlappyBird extends GameScreen{
	
	private BufferedImage birds;
	private Animation bird_anim;
	
	public static float gravity = 0.15f;
	
	private Bird bird;
	private Ground ground;
	private ChimneyGroup chimneyGroup;
	
	private int Point = 0;
	private int highscore = 0;
	
	private int BEGIN_SCREEN = 0;
	private int GAMEPLAY_SCREEN = 1;
	private int GAMEOVER_SCREEN = 2;
	
	private int Currenscreen = BEGIN_SCREEN;

	public FlappyBird() {
		
		super(800,600);
		setLocation(200,50);
		try {
			birds = ImageIO.read(new File("Asserts/birdT.jpg"));
        } catch (IOException e) {}
		
		bird_anim = new Animation(70);
		AFrameOnImage f;
		f = new AFrameOnImage(0,0,76,58);
		bird_anim.AddFrame(f);
		f = new AFrameOnImage(76,0,76,58);
		bird_anim.AddFrame(f);
		f = new AFrameOnImage(152,0,76,58);
		bird_anim.AddFrame(f);
		f = new AFrameOnImage(228,0,76,58);
		bird_anim.AddFrame(f);
		
		bird = new Bird(150, 250, 50, 50);
		ground = new Ground();
		chimneyGroup = new ChimneyGroup();
		
		BeginGame();
		
	
	}
	
	public static void main(String[] args) {
		
		new FlappyBird();
		
	}
	private void resetGame() {
		bird.setPos(150, 250);
		bird.setV(0);
		bird.setLive(true);
		Point = 0;
		chimneyGroup.resetChimneys();
	}

	@Override
	public void GAME_UPDATE(long deltaTime) {
		
		if (Currenscreen == BEGIN_SCREEN) {
			resetGame();
			
		} else if (Currenscreen == GAMEPLAY_SCREEN) {
			
			if (bird.getLive()) bird_anim.Update_Me(deltaTime);
			bird.update(deltaTime);
			ground.Update();
			chimneyGroup.update();
			
			for (int i = 0; i < ChimneyGroup.SIZE; i++) {
				if (bird.getRect().intersects(chimneyGroup.getChimney(i).getRect())) {
				if (bird.getLive()) bird.bupSound.play();
					bird.setLive(false);
					Currenscreen = GAMEOVER_SCREEN;
				
				}
				
			}
				for ( int i = 0; i < ChimneyGroup.SIZE; i++ ) {
					if (bird.getPosX() > chimneyGroup.getChimney(i).getPosX() && !chimneyGroup.getChimney(i).getIsbehindBird() 
							&& i%2==0) {
						Point ++;
						if(highscore < Point) highscore = Point;
						if (bird.getLive() ) {
						bird.getpointSound.play();
						} 
						chimneyGroup.getChimney(i).setIsBehindBird(true);
					} 
				}
		}
			
			
		if (bird.getPosY() + bird.getH() > ground.getYGround()) { 
			Currenscreen = GAMEOVER_SCREEN;
		}
			
}


	@Override
	public void GAME_PAINT(Graphics2D g2) {
		
		g2.setColor(Color.decode("#a1baef"));
		g2.fillRect(0, 0, MASTER_WIDTH, MASTER_HEIGHT);
		
		chimneyGroup.paint(g2);
		
		ground.Paint(g2);
		
		if (bird.getisFlying()) 
		    bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, -0.5f);
		else 
			bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);
		
		
		if (Currenscreen == BEGIN_SCREEN) {
			g2.setColor(Color.black);
			g2.drawString("PRESS SPACE TO PLAY GAME", 300, 300);
		}
		if(Currenscreen == GAMEOVER_SCREEN) {
			g2.setColor(Color.black);
			g2.drawString("GAME OVER !", 350, 300);
			g2.drawString("High Score" + highscore, 350, 350);
		}
		
		g2.setColor(Color.red);
		g2.drawString("POINT: " + Point, 40, 50);
		g2.drawString("HIGH SCORE: " + highscore, 40, 80);
			
			
		}
		

	@Override
	public void KEY_ACTION(KeyEvent e, int Event) {
		if(Event == KEY_PRESSED) {
			bird.fly();	
			
			if (Currenscreen == BEGIN_SCREEN) {
				Currenscreen = GAMEPLAY_SCREEN;
				
			}
			else if (Currenscreen == GAMEPLAY_SCREEN) {
				
				if (bird.getLive()) bird.fly();
				
			} else if (Currenscreen == GAMEOVER_SCREEN) {
				
				Currenscreen = BEGIN_SCREEN;
				
			}
			
		}
	}

}
