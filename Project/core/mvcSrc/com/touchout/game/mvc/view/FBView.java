package com.touchout.game.mvc.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
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
import com.touchout.game.mvc.controller.FBController;
import com.touchout.game.mvc.controller.MainMenuController;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.event.GameEventArg;
import com.touchout.game.mvc.event.IGameEventHandler;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.FBModel;
import com.touchout.game.mvc.model.MainMenuModel;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;
import com.touchout.game.mvc.view.actor.FbPhotoActor;
import com.touchout.game.mvc.view.actor.ProgressBar;
import com.touchout.game.mvc.view.actor.NumBlock;
import com.touchout.game.mvc.view.actor.NumBoard;
import com.touchout.game.mvc.view.actor.ResultPanel;
import com.touchout.game.mvc.view.actor.TextActor;
import com.touchout.game.mvc.view.actor.TextureRegionActor;

public class FBView  implements IView
{
	NumChaining _game;
	FBModel _model;
	FBController _controller;
	
	Logger logger = new Logger("StatisticView");

	//Stages
	Stage _mainStage;	
	
	TextureRegionActor _background;
	TextureRegionActor _logo;
	TextureRegionActor _startButton;
	FbPhotoActor _fbPhoto;
	TextActor _name;
	TextActor _id;
	
	Table _layout;
	
	FreeTypeFontGenerator fontGenerator;
	FreeTypeFontParameter parameter;
	BitmapFont nameFont;
	
	public FBView(FBModel model, FBController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DroidSansFallback.ttf"));
		parameter = new FreeTypeFontParameter();
		
		initializeActors();
		
		//Set Stage
		_mainStage = new Stage(new StretchViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		//_mainStage.addActor(_title);
		_mainStage.addActor(_background);
		_mainStage.addActor(_startButton);
		_mainStage.addActor(_layout);
		_mainStage.addActor(_fbPhoto);
		
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
		_startButton.Text = "LOGIN";
		_startButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				_controller.fbLogin(new FBModel.FbLoginCallback(){
					@Override
					public void success() {
						//_name.setText("LOGIN AS NAME: " + "你好 " + _model.userName);
						_name.setText("你好");
						_id.setText("LOGIN AS ID: " + _model.userId);
						
						Gdx.app.debug("FBView", "userName is [" + _model.userName + "]");
						//nameFont = fontGenerator.generateFont(25, fontGenerator.DEFAULT_CHARS + "你好嗎", false);
						//parameter.characters = fontGenerator.DEFAULT_CHARS + "你好";
						parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "你好";
						parameter.size = 25;
						Assets.UtilityFont2 = fontGenerator.generateFont(parameter);
						//Assets.UtilityFont2.setColor(Color.WHITE);
						
						_name.setFont(Assets.UtilityFont2);
					}
				});				
			}
		});
		
		_fbPhoto = new FbPhotoActor("https://graph.facebook.com/4/picture");
		_fbPhoto.setPosition(200, 200);
		
		_name = new TextActor(Assets.UtilityFont, "PLASE LOGIN", 0, 0);
		_id  = new TextActor(Assets.UtilityFont, "PLASE LOGIN", 0, 0);
		
		_layout = new Table();
		_layout.add(_logo);
		
		_layout.setBounds(0, 0, GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT);
		_layout.row();
		_layout.add(_name).left();
		_layout.row();
		_layout.add(_id).center();
		//_layout.row();
		//_layout.add(_fbPhoto);
		
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
