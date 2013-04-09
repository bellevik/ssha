package Model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player {
	
	private Image userImage;
	private float imgX;
	private float imgY;
	
	private Image attackImage;
	private float attImgX;
	private float attImgY;
	
	private int HP;
	private String name;
	private int damage;
	private int armor;
	private int kills;
	private int deaths;
	private int coins;
	private Skill[] skillList = new Skill[10];
	
	//Movement variables
	float mouseXPosMove;
	float mouseYPosMove;
	double moveSpeed = 1;
	
	int moveCounter=0;
	float xDirMove;
	float yDirMove;
	float genDirMove;
	Double findNaN;
	
	//Attack variables
	float mouseXPosAtt;
	float mouseYPosAtt;
	double attSpeed = 0.5;
	
	int attCounter=0;
	float xDirAtt;
	float yDirAtt;
	float genDirAtt;

	float attackRange = 200;
	
	public Player(float x, float y){
		try {
			userImage = new Image("res/awesomePinkSquare.png");
			attackImage = new Image("res/awesomeGreenSquare.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		imgX = x;
		imgY = y;
	}
	
	//Getters for the movements
	
	public Image getImage(){
		return userImage;
	}
	public float getX(){
		return imgX;
	}
	public float getY(){
		return imgY;
	}
	
	public void addX(float x){
		imgX += x;
	}
	public void addY(float y){
		imgY += y;
	}
	
	public Image getAttImage(){
		return attackImage;
	}
	public float getAttX(){
		return attImgX;
	}
	public float getAttY(){
		return attImgY;
	}
	
	
	
	public void addAttX(float x){
		attImgX += x;
	}
	public void addAttY(float y){
		attImgY += y;
	}
	public void setAttX(float x){
		attImgX = x;
	}
	public void setAttY(float y){
		attImgY = y;
	}
	
	public void resetShot(){
		attImgX = imgX;
		attImgY = imgY;
	}
	
	public float getMouseXPosMove(){
		return mouseXPosMove;
	}
	public float getMouseYPosMove(){
		return mouseYPosMove;
	}
	public double getMoveSpeed(){
		return moveSpeed;
	}
	public int getMoveCounter(){
		return moveCounter;
	}
	public void incMoveCounter(){
		moveCounter++;
	}
	public void resetMoveCounter(){
		moveCounter = 0;
	}
	
	public float getXDirMove(){
		return xDirMove;
	}
	public float getYDirMove(){
		return yDirMove;
	}
	public float getGenDirMove(){
		return genDirMove;
	}
	public void setXDirMove(float dir){
		xDirMove = dir;
	}
	public void setYDirMove(float dir){
		yDirMove = dir;
	}
	public void setGenDirMove(float dir){
		genDirMove = dir;
	}
	
	
	public double getFindNaN(){
		return findNaN;
	}
	//Getters for the attacks
	public float getMouseXPosAtt(){
		return mouseXPosAtt;
	}
	public float getMouseYPosAtt(){
		return mouseYPosAtt;
	}
	public double getAttSpeed(){
		return attSpeed;
	}
	public int getAttCounter(){
		return attCounter;
	}
	public float getXDirAtt(){
		return xDirAtt;
	}
	public float getYDirAtt(){
		return yDirAtt;
	}
	public float getGenDirAtt(){
		return genDirAtt;
	}
	public float getAttackRange(){
		return attackRange;
	}
}
