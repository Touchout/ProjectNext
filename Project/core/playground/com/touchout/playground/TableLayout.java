package com.touchout.playground;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;
import com.touchout.game.mvc.view.actor.NumBlock;
import com.touchout.game.mvc.view.actor.TextureRegionActor;

public class TableLayout implements ApplicationListener {

    Stage stage;
    SpriteBatch spriteBatch;

    @Override
    public void create() 
    {
    	spriteBatch = new SpriteBatch();
        stage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), spriteBatch);
        
    	Assets.globalLogger = new Logger("Global");
		Assets.globalLogger.setLevel(Logger.DEBUG);
		Assets.Load();
		
		TextureRegionActor startButton;
		startButton = new TextureRegionActor(Assets.DarkButtonTexture);
		startButton.Font = Assets.MainMenuButtonFont;
		startButton.enlarge(1.8f);;
		startButton.Text = "START TO PLAY";
		
		TextureRegionActor button2;
		button2 = new TextureRegionActor(Assets.DarkButtonTexture);
		button2.Font = Assets.MainMenuButtonFont;
		button2.enlarge(1.8f);
		button2.Text = "TOTOTOTO";
		
		Table table = new Table();
		table.setDebug(true); // turn on all debug lines (table, cell, and widget)
		//table.setFillParent(true);
		table.setPosition(0, 0);
		table.setSize(GlobalConfig.FRUSTUM_WIDTH, 600);
		table.add(startButton);
		table.row();
		table.add(button2).padTop(100);
		
		stage.addActor(table);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
        stage.act();
        stage.draw();
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}