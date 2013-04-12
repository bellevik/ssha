package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;

public class Menu extends BasicGameState implements ActionListener{
	
	int enemyHP = 100;
	
	private String mouse = "No input yet";
	Image bg;
	
	Image userImage;
	float imgX = 190;
	float imgY = 90;
	
	Image enemyImage;
	float enemyX = 600;
	float enemyY = 300;
	int eWidth;
	int eHeight;
	
	Image attackImage;
	float aImgX = 0;
	float aImgY = 0;
	
	boolean isColliding=false;
	
	/** The wav sound effect */
	private Audio wavEffect;
	
	private boolean startMusic = true;
	
	
	public Menu (int state){
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		enemyImage = new Image("res/awesomePinkSquare.png");
		userImage = new Image("res/awesomePinkSquare.png");
		attackImage = new Image("res/awesomeGreenSquare.png");
		try {
			wavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/bg-music.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		bg = new Image("res/bg.png");
		
		if(startMusic){
			wavEffect.playAsSoundEffect(1.0f, 1.0f, true);
			startMusic = false;
		}
		
		g.drawImage(bg, 0, 0);
		g.drawImage(bg, 500, 0);
		g.drawImage(bg, 1000, 0);
		g.drawImage(bg, 0, 500);
		g.drawImage(bg, 500, 500);
		g.drawImage(bg, 1000, 500);
		
		g.drawString("Are you ready to Super Slash some Hose!?", 50, 50);
		g.drawRect(50, 75, 365, 120); //x,y,width,height
		g.drawString(mouse, 50, 200);
		
		g.drawString("Enemy HP: "+enemyHP,500,500);
		
		g.drawImage(userImage, imgX,imgY);
		
		if(isAttacking&&!isColliding)
			g.drawImage(attackImage, aImgX,aImgY);

		

		Image playImg = new Image("res/start.png");
		
		g.drawImage(playImg, 135, 225);
		
		

		if(isColliding()){
			aImgY=1000;
			aImgX=1000;
			genDirAtt=0;
			enemyHP -= 10;

			playSound("res/154561__ecfike__hurt-argh-1.wav");
		}
		if(enemyHP>0){
			g.drawImage(enemyImage, enemyX, enemyY);
		}else{
			enemyX=-1000;
			enemyY=-1000;
		}
			
	}
	
	
	boolean isRunning = false;
	boolean isAttacking = false;
	
	
	float mouseXPosMove;
	float mouseYPosMove;
	double moveSpeed = 2;

	
	float mouseXPosAtt;
	float mouseYPosAtt;
	float attSpeed = (float)1;
	
	int moveCounter=0;//
	float xDirectionMove;//
	float yDirectionMove;//
	float genDirMove;//
	Double findNaN;
	
	int attCounter=0;//
	float xDirAtt;//
	float yDirAtt;//
	float genDirAtt;//

	float attackRange = 200;//
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
		int xPos = Mouse.getX();
		int yPos = 720 - Mouse.getY();
		
		mouse = "Mouse position: (" + xPos + "," + yPos + ")";
		
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_W)){imgY -= 1;}
		if(input.isKeyDown(Input.KEY_S)){imgY += 1;}
		if(input.isKeyDown(Input.KEY_A)){imgX -= 1;}
		if(input.isKeyDown(Input.KEY_D)){imgX += 1;}
		
		if((140<xPos && xPos<380) && (225<yPos && yPos<325)){
			if(input.isMouseButtonDown(0)){ // 0 = leftclick, 1 = rightclick
				sbg.enterState(1);
			}
		}
		
		if(input.isMouseButtonDown(1)){
			move();
		}
		
		if(input.isMouseButtonDown(0)){
			if ((135<xPos && 250<xPos) && (225<yPos && 270<yPos)){
				enemyHP= 100;
				enemyX = 600;
				enemyY = 300;	
			}
				
			attack();
		}
		if(isRunning){
			isRunning();
		}
		if(isAttacking){
			isAttacking();
		}
		
	}
	
	private void isRunning(){
		imgX += xDirectionMove*moveSpeed;
		imgY += yDirectionMove*moveSpeed;
		if(findNaN.isNaN()){
			imgX = mouseXPosMove;
			imgY = mouseYPosMove;
			isRunning = false;
		}
		moveCounter++;
		if(moveCounter*moveSpeed >= genDirMove)
			isRunning = false;
	}
	
	private void isAttacking(){
		aImgX += xDirAtt*attSpeed;
		aImgY += yDirAtt*attSpeed;
		
		attCounter++;
		if(attCounter*attSpeed >= genDirAtt)
			isAttacking = false;
	}
	
	public boolean isColliding() throws SlickException{
		if((enemyX <= aImgX && aImgX <= enemyX + enemyImage.getWidth()) && (enemyY <= aImgY && aImgY <= enemyY + enemyImage.getHeight()) ){
			return true;
		}else
			return false;
	}
	
	public void move(){
		mouseXPosMove = Mouse.getX();
		mouseYPosMove = 720 - Mouse.getY();
		xDirectionMove = (mouseXPosMove - imgX);
		yDirectionMove = (mouseYPosMove - imgY);
		genDirMove = (float)Math.sqrt(xDirectionMove*xDirectionMove+yDirectionMove*yDirectionMove);
		findNaN = (double)genDirMove;
		xDirectionMove = xDirectionMove/genDirMove;
		yDirectionMove = yDirectionMove/genDirMove;
		
		moveCounter=0;
		
		isRunning = true;
	}
	
	public void attack(){
		
		if(!isAttacking){
			mouseXPosAtt = Mouse.getX();
			mouseYPosAtt = 720 - Mouse.getY();
			
			aImgX = imgX;
			aImgY = imgY;
			
			xDirAtt = (mouseXPosAtt - aImgX);
			yDirAtt = (mouseYPosAtt - aImgY);
			genDirAtt = (float)Math.sqrt(xDirAtt*xDirAtt+yDirAtt*yDirAtt);
			xDirAtt = xDirAtt/genDirAtt;
			yDirAtt = yDirAtt/genDirAtt;
			
			if(genDirAtt > attackRange){
				genDirAtt = attackRange;
			}
			
			attCounter=0;
			
			isAttacking = true;
			
			playSound("res/65733__erdie__bow01.wav");
		}
	}
	
	public int getID(){
		return 0;
	}
	
	public int getWidth(Image image){
		return image.getWidth();
	}
	
	public int getHeight(Image image){
		return image.getHeight();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String fileName = "res/154561__ecfike__hurt-argh-1.wav";
		if(e.getActionCommand() == "Taking damage"){
			playSound("res/154561__ecfike__hurt-argh-1.wav");
		} else if(e.getActionCommand() == "Sword"){
			playSound("res/77611__joelaudio__sfx-attack-sword-001.wav");
		} else if(e.getActionCommand() == "Bow"){
			playSound("res/65733__erdie__bow01.wav");
		} else if(e.getActionCommand() == "Fireball"){
			playSound("res/77691__joelaudio__sfx-magic-fireball-001.wav");
		} else if(e.getActionCommand() == "Music"){
			playSound("res/bg-music.wav");
		}
	}
	
	public static synchronized void playSound(String filename) {

		    try
		    {
		        Clip clip = AudioSystem.getClip();
		        clip.open(AudioSystem.getAudioInputStream(new File(filename)));
		        clip.start();
		    }
		    catch (Exception exc)
		    {
		        exc.printStackTrace(System.out);
		    }	
	}
}
