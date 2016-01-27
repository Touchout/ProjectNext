package com.touchout.game.mvc.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.touchout.game.mvc.controller.MainMenuController;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.model.MainMenuModel;
import com.touchout.game.mvc.utility.LayoutHelper;
import com.touchout.game.mvc.utility.UtilActions;
import com.touchout.game.mvc.view.actor.TextActor;
import com.touchout.game.mvc.view.actor.TextureRegionActor;

public class MainMenuView 
{
	NumChaining _game;
	MainMenuModel _model;
	MainMenuController _controller;
	
	Logger logger = new Logger("MainMenuView");
	//OrthographicCamera _camera;

	//Stages
	Stage _mainStage;
	Stage _bgStage;
	
	//Actors
	//TextActor _title;
	//TextButton _startButton;
	
	TextureRegionActor _background;
	TextureRegionActor _backgroundDark;
	TextureRegionActor _startButton;
	TextureRegionActor _highScoreButton;
	TextActor _subTitle;
	TextActor _countdown;
	
	public MainMenuView(MainMenuModel model, MainMenuController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		initializeActors();
		
		//Set Stage
		//_bgStage = new Stage(new StretchViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		_bgStage = new Stage(new FillViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		_bgStage.addActor(_backgroundDark);
		_bgStage.addActor(_background);
		
		_mainStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		//_mainStage.addActor(_title);
		//_mainStage.addActor(_background);
		_mainStage.addActor(_startButton);
		_mainStage.addActor(_highScoreButton);
		_mainStage.addActor(_subTitle);
		_mainStage.addActor(_countdown);
		
		Gdx.input.setInputProcessor(_mainStage);
			
		//Set logger
		logger.setLevel(Logger.DEBUG);
	}
	
	public void reset() 
	{
		_startButton.addAction(Actions.alpha(1));
		_highScoreButton.addAction(Actions.alpha(1));
		_subTitle.addAction(Actions.alpha(1));
		_background.addAction(Actions.alpha(1));
		_backgroundDark.addAction(Actions.alpha(0));
		
		_countdown.setText("3");
		_countdown.setVisible(false);
		LayoutHelper.centerizeX(_countdown, 0, GlobalConfig.FRUSTUM_WIDTH);
		
		Gdx.input.setInputProcessor(_mainStage);
	}
	
	private void initializeActors() 
	{
//		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//		float titleWidth, titleX, titleY; 
//		float startButtonWidth, startButtonHeight, startButtonX, startButtonY;
//		
//		//*** Compute centralize position ***//
//		//Title
//		titleWidth = Assets.MainTitleFont.getBounds(GlobalConfig.GAME_TITLE).width;
//		titleX = (GlobalConfig.FRUSTUM_WIDTH - titleWidth) / 2;
//		titleY = 800;
//		
//		
//		//StartButton	
//		startButtonWidth = titleWidth;
//		startButtonHeight = 100;
//		startButtonX = (GlobalConfig.FRUSTUM_WIDTH - startButtonWidth) / 2;
//		startButtonY = 600;
//		
//		//*** Initialization ***//
//		_title = new TextActor(Assets.MainTitleFont, GlobalConfig.GAME_TITLE, titleX, titleY);
//		_startButton = new TextButton("Start", skin);
//		_startButton.setSize(startButtonWidth, startButtonHeight);
//		_startButton.setPosition(startButtonX, startButtonY);
//		_startButton.getLabel().setFontScale(2);
//		_startButton.addListener(new InputListener(){
//			@Override
//			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				return true;
//			}
//			
//			@Override
//			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//				_controller.startArcadeGame();
//			}
//		});
		
		_background = new TextureRegionActor(Assets.mainBackgroundTexture);
		_background.setBounds(0, 0, GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT);
		
		_backgroundDark = new TextureRegionActor(Assets.mainBackgroundDarkTexture);
		_backgroundDark.setBounds(0, 0, GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT);
		_backgroundDark.setVisible(false);
		
		float titleCoordX, titleCoordY;
		String subtitle = "tap button with right sequence";
		GlyphLayout layout = new GlyphLayout();
		layout.setText(Assets.SubTitleFont, subtitle);
		titleCoordX = (GlobalConfig.FRUSTUM_WIDTH - layout.width)/2;
		titleCoordY = 680;
		_subTitle = new TextActor(Assets.SubTitleFont, subtitle, titleCoordX, titleCoordY);
		
		_countdown = new TextActor(Assets.CountdownFont, "3", 0, 500);
		_countdown.setVisible(false);
		LayoutHelper.centerizeX(_countdown, 0, GlobalConfig.FRUSTUM_WIDTH);
		 
		
		_startButton = new TextureRegionActor(Assets.DarkButtonTexture);
		float buttonPadding = (GlobalConfig.FRUSTUM_WIDTH - _startButton.getWidth())/2;
		_startButton.setPosition(buttonPadding , 400);
		_startButton.setScale(1.8f);
		_startButton.Font = Assets.MainMenuButtonFont;
		_startButton.Text = "START TO PLAY";
		_startButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) 
			{
				Gdx.input.setInputProcessor(null);
				
				_startButton.addAction(Actions.fadeOut(1.0f));
				_highScoreButton.addAction(Actions.fadeOut(1.0f));
				_subTitle.addAction(Actions.fadeOut(1.0f));
				_background.addAction(Actions.fadeOut(1.0f));
				_backgroundDark.addAction(Actions.sequence(
						Actions.parallel(Actions.alpha(0), Actions.show()),
						Actions.fadeIn(1.0f),
						Actions.delay(3.0f),
						new Action() 
						{
							@Override
							public boolean act(float delta) 
							{
								_controller.startArcadeGame();
								return true;
							}
						}));
				
				_countdown.addAction(Actions.sequence(
						Actions.parallel(Actions.alpha(0), Actions.show()),
						Actions.fadeIn(1.0f),
						_countdown.SetTextAction("2"), 
						Actions.delay(1),
						_countdown.SetTextAction("1"),
						Actions.delay(1),
						Actions.parallel(
							_countdown.SetTextAction("Go !"),
							UtilActions.CenteralizeAction(0, GlobalConfig.FRUSTUM_WIDTH)),
						Actions.delay(1)));
			}
		});
		
		_highScoreButton= new TextureRegionActor(Assets.LightButtonTexture);
		buttonPadding = (GlobalConfig.FRUSTUM_WIDTH - _highScoreButton.getWidth())/2;
		_highScoreButton.setPosition(buttonPadding, 270);
		_highScoreButton.setScale(1.8f);
		_highScoreButton.Font = Assets.MainMenuButtonFontDark;
		_highScoreButton.Text = "YOUR SCORE";
	}	
	
	public void resize(int width, int height) 
	{
		_mainStage.getViewport().update(width, height);
		_bgStage.getViewport().update(width, height);
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
		
		_bgStage.act();
		_bgStage.getViewport().apply();
		_bgStage.draw();
		
		_mainStage.act();
		_mainStage.getViewport().apply();
		_mainStage.draw();
		
	}
}
