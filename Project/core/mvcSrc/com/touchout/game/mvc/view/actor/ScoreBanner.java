package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.touchout.game.mvc.core.Assets;

public class ScoreBanner extends Actor 
{
	public TextureRegion backgroundTextureRegion;
	public BitmapFont FontTitle = Assets.TimeTitleFont;
	public BitmapFont Font = Assets.ScoreFont;
	String Title = "SCORE";
	public String Text = "000";
	
	float titleCoordX, titleCoordY, scoreCoordX, scoreCoordY;
	
	public ScoreBanner()
	{
		super();
		backgroundTextureRegion = Assets.scoreBackgroundTexture;
		setWidth(backgroundTextureRegion.getRegionWidth());
		setHeight(backgroundTextureRegion.getRegionHeight());
		//setWidth(200);
		//setHeight(200);
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
	}	
	
	public void relayout() {
		GlyphLayout layout = new GlyphLayout();
				
		//calculate title coord
		layout.setText(FontTitle, Title);
		float width = layout.width;
		float height = layout.height;
		float paddingX = (int) ((this.getWidth() - width) / 2);
		float paddingY = (int) ((this.getHeight() + height) / 2);
		titleCoordX = this.getX() + paddingX;
		titleCoordY = this.getY() + 100;
		
		layout.setText(Font, Text);
		paddingX = (int) ((this.getWidth() - layout.width) / 2);
		scoreCoordX = this.getX() + paddingX;
		scoreCoordY = this.getY() + layout.height + 30;		
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
			relayout();
			FontTitle.draw(batch, Title, titleCoordX, titleCoordY);
			Font.draw(batch, Text, scoreCoordX, scoreCoordY);
		}
	}
}
