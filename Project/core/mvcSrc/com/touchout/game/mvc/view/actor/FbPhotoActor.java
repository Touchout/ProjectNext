package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.touchout.game.mvc.utility.WebTexture;

public class FbPhotoActor extends Actor 
{
	public Texture PhotoTexture;
	private String _url;
	private WebTexture _webTexture;
	
	public FbPhotoActor(String url)
	{
		super();
		_webTexture = new WebTexture(url, null);
		
		setWidth(100);
		setHeight(100);
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		if(_webTexture.getTexture() != null)
		{
			Gdx.app.debug("FbPhotoActor", "got texture, drawing");
			batch.draw(_webTexture.getTexture()
					,this.getX()
					,this.getY()
					,this.getOriginX()
					,this.getOriginY()
					,this.getWidth()
					,this.getHeight()
					,this.getScaleX()
					,this.getScaleY());
		}
	}
}
