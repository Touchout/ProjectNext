package com.touchout.game.mvc.controller;

import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.FBModel;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;
import com.touchout.game.mvc.view.ArcadeGameView;
import com.touchout.game.mvc.view.FBView;
import com.touchout.game.mvc.view.IView;
import com.touchout.game.mvc.view.StatisticView;

public class FBController 
{
	NumChaining _game;
	FBModel _model;
	IView _view;

	public NumChaining getGame() { return _game; }
	
	public FBController(FBModel model, NumChaining game)
	{
		_model = model;
		_game = game;
		
		//Initial View
		_view = new FBView(_model, this);
		//_view = new StatisticView(_model, this);
	}
	
	public void resize(int width, int height) 
	{
		_view.resize(width, height);
	}
	
	public void update(float delta)
	{
	}
	
	public void renderView()
	{
		_view.update();
		_view.render();
	}
	
	public void backToMainMenu() {
		_game.backToMainMenu();
	}
	
	public void fbLogin(FBModel.FbLoginCallback callback) {
		_model.login(callback);
	}
}
