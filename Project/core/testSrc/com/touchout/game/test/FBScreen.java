package com.touchout.game.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Logger;
import com.touchout.game.mvc.controller.ArcadeGameController;
import com.touchout.game.mvc.controller.FBController;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.FBModel;

public class FBScreen extends ScreenAdapter
{
	Logger logger = new Logger("MvcTestScreen");

	//Model
	FBModel _model;
	
	//controller
	FBController _controller;
	
	public FBScreen(NumChaining game)
	{
		//Initial model
		_model = new FBModel();
		
		//Initial controller
		_controller = new FBController(_model, game);

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		logger.setLevel(Logger.DEBUG);
	}
	
	public void reset() {
		
	}
	
	@Override
	public void resize(int width, int height) 
	{
		_controller.resize(width, height);
	}
	
	@Override
	public void render(float delta) 
	{
		_controller.update(delta);
		_controller.renderView();
	}	 
}
