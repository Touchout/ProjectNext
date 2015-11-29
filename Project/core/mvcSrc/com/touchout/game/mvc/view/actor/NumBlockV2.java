package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Logger;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;

public class NumBlockV2 extends NumBlockBase
{
	Logger logger = new Logger("NumBlockV2");
	private float bgOffsetX, bgOffsetY;
	public boolean bgOnly;

	public NumBlockV2(Integer number, int row, int col, float x, float y, float width, float height)
	{
		super(number, row, col, x, y, width, height);
		
		bgOffsetX = (col - GlobalConfig.COLUMN_COUNT/2) * 4;
		bgOffsetY = (row - GlobalConfig.ROW_COUNT/2) * 4;
		bgOnly = false;
		
		logger.setLevel(Logger.DEBUG);
	}
	
	@Override
	public void performClick()
	{
		//bounce();
	}
	
	@Override
	public void performHint()
	{
		bounce();
	}
	
	public void bounce() 
	{
		//this.setTouchable(Touchable.disabled);
		this.getActions().clear();
		this.addAction(Actions.sequence(
				Actions.scaleTo(0.7f, 0.7f, 0.05f, Interpolation.linear),
				Actions.scaleTo(1f, 1f, 0.5f, Interpolation.elasticOut),
				Actions.touchable(Touchable.enabled)));
	
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
		//this.setScale(0.3f);
		//this.getActions().clear();
		//this.addAction(Actions.moveBy(50, 50, 1f));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		this.onDraw();
		
		if(this.getColor() != Color.WHITE)
			batch.setColor(this.getColor());
		
		batch.draw(Assets.NumBlockBackgroundDarkTextures[BgNumber]
				,this.getX()
				,this.getY()
				,this.getOriginX()
				,this.getOriginY()
				,this.getWidth()
				,this.getHeight()
				,this.getScaleX()
				,this.getScaleY()
				,this.getRotation());
		
		if(BgNumber < 4 && !Entity.IsOnHover)
		{
			batch.draw(Assets.NumBlockBackgroundTextures[BgNumber]
					,this.getX() + this.bgOffsetX
					,this.getY() + this.bgOffsetY
					,this.getOriginX()
					,this.getOriginY()
					,this.getWidth()
					,this.getHeight()
					,this.getScaleX()
					,this.getScaleY()
					,this.getRotation());
		}
		
		Vector2 numCoord = getNumberCoord();
		_font.draw(batch, String.valueOf(this.Number), numCoord.x, numCoord.y);
		
		batch.setColor(Color.WHITE);
	}
}
