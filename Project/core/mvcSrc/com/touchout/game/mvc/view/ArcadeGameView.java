package com.touchout.game.mvc.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.touchout.game.mvc.controller.ArcadeGameController;
import com.touchout.game.mvc.core.Assets;
import com.touchout.game.mvc.core.GlobalConfig;
import com.touchout.game.mvc.core.NumChaining;
import com.touchout.game.mvc.event.GameEventArg;
import com.touchout.game.mvc.event.IGameEventHandler;
import com.touchout.game.mvc.model.ArcadeGameModel;
import com.touchout.game.mvc.model.ArcadeGameModel.GameState;
import com.touchout.game.mvc.utility.LayoutHelper;
import com.touchout.game.mvc.view.actor.NumBlockBase;
import com.touchout.game.mvc.view.actor.ProgressBar;
import com.touchout.game.mvc.view.actor.NumBoard;
import com.touchout.game.mvc.view.actor.ScoreBanner;
import com.touchout.game.mvc.view.actor.TextActor;
import com.touchout.game.mvc.view.actor.TextureRegionActor;
import com.touchout.game.mvc.view.actor.TimeBannerActor;

public class ArcadeGameView implements IView
{
	//private static final boolean enableDebugDraw = true;
	private static final boolean enableDebugDraw = false;
	
	NumChaining _game;
	ArcadeGameModel _model;
	ArcadeGameController _controller;
	
	Logger logger = new Logger("ArcadeGameView");
	//OrthographicCamera _camera;

	//Stages
	Stage _gameStage;
	Stage _uiStage;
	
	//Actors
	NumBoard _board;
	//TextActor _gameTimeCounter;
	TextActor _comboCounter;
	TextActor _score;
	TextActor _timeBounusNotifier;
	ProgressBar _comboMeter; // ,_boostMeter;
	//ResultPanel _resultPanel;
	TimeBannerActor _timeBanner;
	ScoreBanner _scoreBanner;
	TextureRegionActor _restartBtn;
	
	//time after gameover
	float _gameOverWaitTime = 2f;
	
	public ArcadeGameView(ArcadeGameModel model, ArcadeGameController controller)
	{
		_model = model;
		_controller = controller;
		_game = controller.getGame();
		
		initializeActors();
		
		//Set Game Stage
		_gameStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
		_gameStage.addActor(_board);
		//_gameStage.addActor(_gameTimeCounter);
		_gameStage.addActor(_timeBanner);
		_gameStage.addActor(_comboCounter);
		_gameStage.addActor(_comboMeter);
		//_gameStage.addActor(_boostMeter);
		_gameStage.addActor(_scoreBanner);
		_gameStage.addActor(_timeBounusNotifier);
		_gameStage.addActor(_restartBtn);
		Gdx.input.setInputProcessor(_gameStage);
		
		//Set UI Stage
//		_uiStage = new Stage(new FitViewport(GlobalConfig.FRUSTUM_WIDTH, GlobalConfig.FRUSTUM_HEIGHT), _game.batch);
//		_uiStage.addActor(_resultPanel);
		
		//Register Events
		_model.getGameOverEvent().addTEventHandler(new IGameEventHandler() {			
			@Override
			public void handle(GameEventArg event) { switchToResulting(); }
		});
		
		//Set Camera
		//_camera = new OrthographicCamera();
		//_camera.setToOrtho(false, Config.FRUSTUM_WIDTH, Config.FRUSTUM_HEIGHT);
		
		//Set logger
		//logger.setLevel(Logger.DEBUG);
	}
	
	private void initializeActors() 
	{
		float boardPosX, timerPosX, cCounterPosX, comboBarPosX, boostMeterPosX, scoreX;
		float boardPosY, timerPosY, cCounterPosY, comboBarPosY, boostMeterPosY, scoreY;
		float boardWidth,boardHeight, timerWidth, cCounterWidth, comboBarWidth, comboBarHeight, boostMeterWidth, boostMeterHeight;
		
		//*** Compute centralize position ***//
		//Board
		boardWidth = GlobalConfig.BLOCK_WIDTH * GlobalConfig.COLUMN_COUNT + (GlobalConfig.COLUMN_COUNT-1) * GlobalConfig.BLOCK_MARGIN;
		boardHeight = GlobalConfig.BLOCK_HEIGHT * GlobalConfig.ROW_COUNT + (GlobalConfig.ROW_COUNT-1) * GlobalConfig.BLOCK_MARGIN;
		boardPosX= (GlobalConfig.FRUSTUM_WIDTH - boardWidth) / 2;
		boardPosY = GlobalConfig.BOARD_UPPER_BOUND - boardHeight;
		
		//Timer
//		timerWidth = Assets.TimeFont.getBounds("00:00").width;
//		timerPosX = (GlobalConfig.FRUSTUM_WIDTH - timerWidth) / 2;
//		timerPosY = GlobalConfig.TIMER_VPOSITION;
		
		//TimerNew
		_timeBanner = new TimeBannerActor();
		//_timeBanner.setScale(2);
		_timeBanner.setSize(_timeBanner.getWidth()*2, _timeBanner.getHeight()*1.5f);
		_timeBanner.setPosition((GlobalConfig.FRUSTUM_WIDTH - _timeBanner.getWidth())/2, 
				GlobalConfig.FRUSTUM_HEIGHT - _timeBanner.getHeight());
		_timeBanner.relayout();
				
		//TimeBounusNotifier
		_timeBounusNotifier = new TextActor(Assets.TimePlusFont, "+2 SECONDS", 0, _timeBanner.getY() - 25);
		float centerX = (GlobalConfig.FRUSTUM_WIDTH - _timeBounusNotifier.getWidth())/2;
		_timeBounusNotifier.setX(centerX);
		_timeBounusNotifier.setVisible(false);
		
		//ScoreBanner
		_scoreBanner = new ScoreBanner();
		LayoutHelper.centerizeX(_scoreBanner, 0, _timeBanner.getX());
		LayoutHelper.centerizeY(_scoreBanner, _timeBanner.getY(), _timeBanner.getTop());		
		_scoreBanner.relayout();
		
		//ComboCounter
		GlyphLayout layout = new GlyphLayout();
		layout.setText(Assets.ComboFont, "00 COMBOS");
		cCounterWidth = layout.width;
		cCounterPosX = (GlobalConfig.FRUSTUM_WIDTH - cCounterWidth)/2;
		cCounterPosY = GlobalConfig.BOARD_UPPER_BOUND + 110;
		
		//ComboBar
		comboBarWidth = boardWidth;
		comboBarHeight = 15;
		comboBarPosX = boardPosX;
		comboBarPosY = GlobalConfig.BOARD_UPPER_BOUND +70;
		
		//BoostMeter
//		boostMeterWidth = cCounterPosX - boardPosX - 50;
//		boostMeterHeight = 15;
//		boostMeterPosX = boardPosX;
//		boostMeterPosY = GlobalConfig.BOARD_UPPER_BOUND + 50;
		
		//*** Initialization ***//
		//_gameTimeCounter = new TextActor(Assets.TimeFont, "", timerPosX, timerPosY);
		_timeBanner.Text = "";
		
		_comboCounter = new TextActor(Assets.ComboFont, "", cCounterPosX, cCounterPosY);	
		
		_comboMeter = new ProgressBar(comboBarPosX, comboBarPosY, comboBarWidth, comboBarHeight);
		_comboMeter.setMax(_model.getMetadata().getRemianComboTimeSpec());
		_comboMeter.setMin(0);
		_comboMeter.setCurrent(_model.getMetadata().getRemainComboTime());
		_comboMeter.setShowUpperBound(true);
		_comboMeter.setAlwaysShow(true);
		
		_board = new NumBoard(new Vector2(boardPosX, boardPosY), 
				GlobalConfig.ROW_COUNT, 
				GlobalConfig.COLUMN_COUNT, 
				GlobalConfig.BLOCK_WIDTH, 
				GlobalConfig.BLOCK_HEIGHT);
		_board.addListener(new InputListener()
		{
			@Override
			public void touchUp(InputEvent event, float screenX, float screenY,int pointer, int button) 
			{
				Vector2 touchPos = new Vector2();
				touchPos.set(screenX, screenY);
				_board.localToStageCoordinates(touchPos);
				logger.debug(touchPos.x + "," + touchPos.y);
				
				NumBlockBase touchedBlock;
				if((touchedBlock = (NumBlockBase) _board.hit(touchPos.x, touchPos.y, false)) != null)
				{
					_controller.touchBlockUp(touchedBlock.Row, touchedBlock.Col);
				}
			}
			
			@Override
			public boolean touchDown(InputEvent event, float screenX, float screenY,int pointer, int button) 
			{
				Vector2 touchPos = new Vector2();
				touchPos.set(screenX, screenY);
				_board.localToStageCoordinates(touchPos);
				logger.debug(touchPos.x + "," + touchPos.y);
				
				NumBlockBase touchedBlock;
				if((touchedBlock = (NumBlockBase) _board.hit(touchPos.x, touchPos.y, false)) != null)
				{
					_controller.touchBlockDown(touchedBlock.Row, touchedBlock.Col);
				}
				
				return true;
			}
		});
		
		_board.bindCells(_model.getBboardEntity().getCells());
		
		_restartBtn = new TextureRegionActor(Assets.restartTexture);
		_restartBtn.setPosition(0, -50);
		_restartBtn.setScale(0.5f);
		_restartBtn.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float screenX, float screenY,int pointer, int button) 
			{
				_controller.backToMainMenu();
				return false;
			}
		});
		
		_model.getBlockSolvedEvent().addTEventHandler(new IGameEventHandler() {
			@Override
			public void handle(GameEventArg event) {
				Vector2 coord = (Vector2)event.Tag;
				_board.getBlock((int)coord.x, (int)coord.y).performClick();
			}
		});
		
		_model.getBoardUnlockEvent().addTEventHandler(new IGameEventHandler() {
			@Override
			public void handle(GameEventArg event) {
				_board.getBlock((Integer)(event.Tag)).performHint();
			}});
		
		_model.getMetadata().timePlusEvent.addTEventHandler(new IGameEventHandler() {			
			@Override
			public void handle(GameEventArg event) {
				_timeBounusNotifier.addAction(Actions.sequence(
						Actions.scaleTo(0.1f, 0.1f),
						Actions.visible(true),
						Actions.scaleTo(1.2f, 1.2f, 0.1f, Interpolation.linear),
						Actions.scaleTo(1f, 1f, 0.15f, Interpolation.linear),
						Actions.delay(0.5f),
						Actions.visible(false)));
			}
		});
	}
	
	public void reset()
	{
		//_resultPanel.initialize();
		Gdx.input.setInputProcessor(_gameStage);
	}
	
	private void switchToResulting() 
	{
		//_resultPanel.setScore(_model.getMetadata().getScoreString());
		//_resultPanel.setHighScore(0);
		//_controller.goToStatistics();
		//Gdx.input.setInputProcessor(_uiStage);
		Gdx.input.setInputProcessor(null);
		
	}
	
	public void resize(int width, int height) 
	{
		_gameStage.getViewport().update(width, height);
		//_uiStage.getViewport().update(width, height);
	}
	
	public void update()
	{
		//_gameTimeCounter.setText(_model.getMetadata().getGameTimeString());
		_timeBanner.Text = _model.getMetadata().getGameTimeString();
		_scoreBanner.Text = _model.getMetadata().getScoreString();
		_comboCounter.setText(String.format("%3d COMBOS",_model.getMetadata().getComboCount()));
		_comboMeter.setMax(_model.getMetadata().getRemianComboTimeSpec());
		_comboMeter.setCurrent(_model.getMetadata().getRemainComboTime());
		_comboMeter.setScaleFactor((_model.getMetadata().getCcomboBonusCount()+1)/10f);
		//_boostMeter.setCurrent(_model.getMetadata().getCcomboBonusCount() % _model.getMetadata().getComboBonusTarget());
	}
	
	public void render()
	{
		//Clear
		Gdx.gl.glClearColor(0xF9, 0xF9, 0xF9, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
				
		//Draw gaming component (board...etc)
		//_camera.update();	

		//Debug>>>>>>>>>>>>>>>>>>
		if(enableDebugDraw)
		{
			ShapeRenderer sr = new ShapeRenderer();
			sr.setProjectionMatrix(_gameStage.getCamera().combined);
			sr.setAutoShapeType(true);
			sr.begin();
			_timeBanner.setDebug(true);
			_timeBanner.drawDebug(sr);
			_scoreBanner.setDebug(true);
			_scoreBanner.drawDebug(sr);
			sr.set(ShapeType.Line);
			sr.setColor(Color.BLACK);
			sr.line(0, _timeBanner.getY(), 1080, _timeBanner.getY());	
			sr.end();
		}
		//<<<<<<<<<<<<<<<<<<<<<<<
		
		_gameStage.draw();
		_gameStage.act();
		
		if(_model.getState() == GameState.Resulting)
		{
			//_uiStage.draw();
			//_uiStage.act();
			
			_gameOverWaitTime -= Gdx.app.getGraphics().getDeltaTime();
			if(_gameOverWaitTime <= 0)
				_controller.goToStatistics();
		}
		
//		ShapeRenderer renderer = new ShapeRenderer();
//		renderer.setProjectionMatrix(_stage.getCamera().combined);
//		renderer.setColor(Color.WHITE);
//		renderer.begin(ShapeType.Line);
//		renderer.line(0, Config.BOARD_UPPER_BOUND, Config.FRUSTUM_WIDTH, Config.BOARD_UPPER_BOUND);
//		renderer.end();
//		renderer.dispose();
	}
}
