package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.touchout.game.mvc.core.Assets;

public class NumBlockBg extends Actor
{
	public int BgNumber;
	public int Row;
	public int Col;
	BitmapFont _font = Assets.BlockFont;

	public NumBlockBg(Integer bgNumber, float x, float y, float width, float height)
	{
		BgNumber = bgNumber;
		setBounds(0, 0, width, height);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		if(this.getColor() != Color.WHITE)
			batch.setColor(this.getColor());
		
		batch.draw(Assets.NumBlockBackgroundTextures[BgNumber]
				,this.getX()
				,this.getY()
				,this.getOriginX()
				,this.getOriginY()
				,this.getWidth()
				,this.getHeight()
				,this.getScaleX()
				,this.getScaleY()
				,this.getRotation());
		
		batch.setColor(Color.WHITE);
	}
}
