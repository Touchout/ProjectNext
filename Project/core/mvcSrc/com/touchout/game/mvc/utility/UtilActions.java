package com.touchout.game.mvc.utility;

import com.badlogic.gdx.scenes.scene2d.Action;

public class UtilActions 
{
	public static Action CenteralizeAction(final float left, final float right) 
	{
		Action action = new Action() 
		{
			@Override
			public boolean act(float delta) 
			{
				LayoutHelper.centerizeX(actor, left, right);
				return true;
			}
		};		
		
		return action;
	}
}
