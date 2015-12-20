package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.touchout.game.mvc.core.Assets;

public class TimeBannerActor extends Actor 
{
	public TextureRegion backgroundTextureRegion;
	public BitmapFont FontTitle = Assets.TimeTitleFont;
	public BitmapFont Font = Assets.TimeFont;
	String Title = "TIME REMAIN";
	public String Text = "00:00";
	
	float titleCoordX, titleCoordY, timeCoordX, timeCoordY;
	
	public TimeBannerActor()
	{
		super();
		backgroundTextureRegion = Assets.timeBannerTexture;
		setWidth(backgroundTextureRegion.getRegionWidth());
		setHeight(backgroundTextureRegion.getRegionHeight());
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
	}	
	
	public void relayout() {
		//calculate title coord
		GlyphLayout layout = new GlyphLayout();
		layout.setText(FontTitle, Title);
		float width = layout.width;
		float height = layout.height;
		float paddingX = (int) ((this.getWidth() - width) / 2);
		float paddingY = (int) ((this.getHeight() + height) / 2);
		titleCoordX = this.getX() + paddingX;
		titleCoordY = this.getY() + 150;
		
		layout.setText(Font, Text);
		width = layout.width;
		paddingX = (int) ((this.getWidth() - width) / 2);
		timeCoordX = this.getX() + paddingX;
		timeCoordY = this.getY() + 100;		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		batch.draw(backgroundTextureRegion
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
			FontTitle.draw(batch, Title, titleCoordX, titleCoordY);
			Font.draw(batch, Text, timeCoordX, timeCoordY);
		}
	}
}
