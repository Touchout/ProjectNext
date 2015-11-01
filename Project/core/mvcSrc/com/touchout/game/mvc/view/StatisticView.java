package com.touchout.game.mvc.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Config;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.touchout.game.mvc.controller.ArcadeGameController;
import com.touchout.game.mvc.controller.MainMenuController;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.event.GameEventArg;
import com.touchout.game.mvc.event.IGameEventHandler;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.MainMenuModel;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;
import com.touchout.game.mvc.view.actor.ProgressBar;
import com.touchout.game.mvc.view.actor.NumBlock;
import com.touchout.game.mvc.view.actor.NumBoard;
import com.touchout.game.mvc.view.actor.ResultPanel;
import com.touchout.game.mvc.view.actor.TextActor;
import com.touchout.game.mvc.view.actor.TextureRegionActor;

public class StatisticView  implements IView
{
	NumChaining _game;
	ArcadeGameModel _model;
	ArcadeGameController _controller;
	
	Logger logger = new Logger("StatisticView");

	//Stages
	Stage _mainStage;	
	
	TextureRegionActor _background;
	TextureRegionActor _logo;
	TextureRegionActor _startButton;
	TextureRegionActor _highScoreButton;
	TextActor _scoreLabel;
	TextActor _score;
	
	Table _layout;
	
	public StatisticView(ArcadeGameModel model, ArcadeGameController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		initializeActors();
		
		//Set Stage
		_mainStage = new Stage(new StretchViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		//_mainStage.addActor(_title);
		_mainStage.addActor(_background);
		_mainStage.addActor(_startButton);
		_mainStage.addActor(_highScoreButton);
		_mainStage.addActor(_layout);
		
		Gdx.input.setInputProcessor(_mainStage);
			
		//Set logger
		logger.setLevel(Logger.DEBUG);
	}
	
	public void reset() {
		Gdx.input.setInputProcessor(_mainStage);
	}
	
	private void initializeActors() 
	{		
		_background = new TextureRegionActor(Assets.statisticBackgroundTexture);
		_background.setBounds(0, 0, GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT);
		
		_logo = new TextureRegionActor(Assets.logoTexture);
		
		_startButton = new TextureRegionActor(Assets.LightButtonTexture);
		float buttonPadding = (GlobalConfig.FRUSTUM_WIDTH - _startButton.getWidth())/2;
		_startButton.setPosition(buttonPadding , 400);
		_startButton.setScale(1.8f);
		_startButton.Font = Assets.MainMenuButtonFontDark;
		_startButton.Text = "PLAY AGAIN";
		_startButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				_controller.restartGame();
			}
		});
		
		_highScoreButton= new TextureRegionActor(Assets.LightButtonTexture);
		buttonPadding = (GlobalConfig.FRUSTUM_WIDTH - _highScoreButton.getWidth())/2;
		_highScoreButton.setPosition(buttonPadding, 270);
		_highScoreButton.setScale(1.8f);
		_highScoreButton.Font = Assets.MainMenuButtonFontDark;
		_highScoreButton.Text = "SHARE YOUR SCORE";
		
		_scoreLabel = new TextActor(Assets.TimeTitleFont, "YOUR SCORE", 0, 0);
		_score = new TextActor(Assets.FinalScoreFont, String.valueOf(_model.getMetadata().getScore()), 0, 0);
		
		_layout = new Table();
		_layout.add(_logo);
		
		_layout.setBounds(0, 0, GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT);
		_layout.row();
		_layout.add(_scoreLabel).center();
		_layout.row();
		_layout.add(_score).center();
		
		_layout.top();
	}	
	
	public void resize(int width, int height) 
	{
		_mainStage.getViewport().update(width, height);
	}
	
	public void update()
	{
	}
	
	public void render()
	{
		//Clear
		Gdx.gl.glClearColor(0xF9, 0xF9, 0xF9, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
				
		//Draw gaming component (board...etc)
		//_camera.update();
//		_game.batch.begin();
//		_game.batch.draw(Assets.mainBackgroundTexture,0,0);
//		_game.batch.end();
		_mainStage.draw();
	}
}
