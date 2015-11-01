package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.touchout.game.mvc.core.Assets;

public class TextureRegionActor extends Actor 
{
	public TextureRegion ContenTextureRegion;
	public BitmapFont Font = Assets.MainMenuButtonFont;
	public String Text = "";
	
	public TextureRegionActor(TextureRegion textureRegion)
	{
		super();
		ContenTextureRegion = textureRegion;
		setWidth(ContenTextureRegion.getRegionWidth());
		setHeight(ContenTextureRegion.getRegionHeight());
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
	}

	private Vector2 getTextCoord() 
	{
		GlyphLayout layout = new GlyphLayout();
		layout.setText(Font, Text);
		float width = layout.width;
		float height = layout.height;
		float paddingX = (int) ((this.getWidth() - width) / 2);
		float paddingY = (int) ((this.getHeight() + height) / 2);
		return new Vector2(this.getX() + paddingX, this.getY() + paddingY);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		batch.draw(ContenTextureRegion
				,this.getX()
				,this.getY()
				,this.getOriginX()
				,this.getOriginY()
				,this.getWidth()
				,this.getHeight()
				,this.getScaleX()
				,this.getScaleY()
				,this.getRotation());
		
		if(!Text.isEmpty())
		{
			Vector2 numCoord = getTextCoord();
			Font.draw(batch, Text, numCoord.x, numCoord.y);
		}
	}
}
