package com.touchout.game.mvc.view.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextActor extends Actor 
{
	BitmapFont _font;
	String _text;
	GlyphLayout layout = new GlyphLayout();
	
	public BitmapFont getFont() { return _font;}

	public void setFont(BitmapFont _font) 
	{	
		this._font = _font;
		layout.setText(_font, _text);
		this.setWidth(layout.width);
		this.setHeight(layout.height);
	}
	
	public String getText() { return _text;}

	public void setText(String _text) 
	{
		this._text = _text;
		layout.setText(_font, _text);
		this.setWidth(layout.width);
		this.setHeight(layout.height);
	}
	
	public void recalculate()
	{
		setBounds(this.getX(), this.getY(),  layout.width,  layout.height);
	}
	
	public TextActor(BitmapFont font, String text, float x, float y)
	{
		super();
		_font = font;
		_text = text;
		setX(x);
		setY(y);
		recalculate();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) 
	{
		_font.draw(batch, _text, getX(), getY());
	}
}
