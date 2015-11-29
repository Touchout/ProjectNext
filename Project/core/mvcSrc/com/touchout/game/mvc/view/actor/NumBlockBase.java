package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.model.NumBlockEntity;

public abstract class NumBlockBase extends Actor 
{
	public NumBlockEntity Entity;
	public int Number;
	public int BgNumber;
	public int Row;
	public int Col;
	BitmapFont _font = Assets.BlockFont;

	public NumBlockBase(Integer number, int row, int col, float x, float y, float width, float height)
	{
		Number = number;
		BgNumber = 0;
		Row = row;
		Col = col;
		setBounds(x, y, width, height);
	}
	
	public abstract void performClick();
	
	public abstract void performHint();
	
	protected Vector2 getNumberCoord() 
	{
		GlyphLayout layout = new GlyphLayout();
		layout.setText(_font, String.valueOf(this.Number));
		float width = layout.width;
		float height = layout.height;
		float paddingX = (int) ((this.getWidth() - width) / 2);
		float paddingY = (int) ((this.getHeight() + height) / 2);
		return new Vector2(this.getX() + paddingX, this.getY() + paddingY);
	}
	
	protected void onDraw()
	{
		//Set display number
		Number = Entity.Number;
		BgNumber = Entity.BgNumber;
		
		//Set tint color by status 
		if(Entity.IsLocked)
			setColor(Color.RED);
		else if(Entity.IsSolved)
			setColor(Color.GRAY);
		else
			setColor(Color.WHITE);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		this.onDraw();
		
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
		Vector2 numCoord = getNumberCoord();
		_font.draw(batch, String.valueOf(this.Number), numCoord.x, numCoord.y);
		
		batch.setColor(Color.WHITE);
	}
}
