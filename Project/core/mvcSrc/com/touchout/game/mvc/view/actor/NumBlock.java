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

public class NumBlock extends NumBlockBase
{
	public int Number;
	public int BgNumber;
	public int Row;
	public int Col;
	BitmapFont _font = Assets.BlockFont;

	public NumBlock(Integer number, int row, int col, float x, float y, float width, float height)
	{
		super(number, row, col, x, y, width, height);
//		Number = number;
//		BgNumber = 0;
//		Row = row;
//		Col = col;
//		setBounds(x, y, width, height);
	}
	
	@Override
	public void performClick()
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
}
