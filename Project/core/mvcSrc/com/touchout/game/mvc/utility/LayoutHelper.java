package com.touchout.game.mvc.utility;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class LayoutHelper 
{
	public static void centerizeX(Actor actor, float leftBound, float rightBound) 
	{
		float centerX = leftBound + ((rightBound-leftBound) - actor.getWidth())/2;
		actor.setX(centerX);
	}
	
	public static void centerizeY(Actor actor, float lowerBound, float upperBound) 
	{
		float centerY = lowerBound + ((upperBound-lowerBound) - actor.getHeight())/2;
		actor.setY(centerY);
	}
}
