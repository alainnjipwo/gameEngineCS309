package engine.managers.entities.creatures;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.gfx.Animation;
import engine.gfx.Assets;

public enum CreatureType {
	
	Player(0, true, new Animation(150, Assets.player_up), new Animation(150, Assets.player_down), new Animation(150, Assets.player_left), new Animation(150, Assets.player_right), Assets.player_notmoving),
	Guard(1, false, new Animation(150, Assets.guard_up), new Animation(150, Assets.guard_down), new Animation(150, Assets.guard_left), new Animation(150, Assets.guard_right), Assets.guard_notmoving),
	Prisoner(0, false, new Animation(150, Assets.player_up), new Animation(150, Assets.player_down), new Animation(150, Assets.player_left), new Animation(150, Assets.player_right), Assets.player_notmoving),
	Paramedic(0, false, new Animation(150, Assets.player_up), new Animation(150, Assets.player_down), new Animation(150, Assets.player_left), new Animation(150, Assets.player_right), Assets.player_notmoving);
	
	int type, speed;
	boolean player_controlled;
	Animation animUp, animDown, animLeft, animRight;
	BufferedImage[] notMoving;
	protected Rectangle bounds;
	
	CreatureType(int type, boolean player_controlled, Animation animUp, Animation animDown, Animation animLeft, Animation animRight, BufferedImage[] notMoving){
		this.type = type;
		this.player_controlled = player_controlled;
		
		//Animations
		this.animUp = animUp;
		this.animDown = animDown;
		this.animLeft = animLeft;
		this.animRight = animRight;
		this.notMoving = notMoving;
		
		//Bounds
		bounds = new Rectangle(20, 43, 23, 17);
	}
}
