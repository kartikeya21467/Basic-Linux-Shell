package com.mygdx.tankstars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.Serializable;

public class TankStars extends Game implements Serializable {
	public static final String TITLE = "TankStars";
	public static final float VERSION = 0.1f;
	public final int Height = 900;
	public final int Width = 1600;
	transient public SpriteBatch batch;
	public BitmapFont font;
	transient public OrthographicCamera camera;
	transient public AssetManager assets;

	public GameScreen gamescreen;
	private mainmenuscreen mainmenuscreen;
	private PauseScreen pausescreen;
	private EntryScreen entryscreen;
	private TankSelectScreen tankSelectScreen;

	public Winnerscreen1 winnerscreen1;
	public Winnerscreen2 winnerscreen2;

	public Player1 player1;
	public Player2 player2 ;

	public Music gameintro;


	
	@Override
	public void create () {
		assets = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1600,900);
		batch = new SpriteBatch();
		initfonts();
//		font = new BitmapFont();

		player1 = new Player1();
		player2 = new Player2();



		gamescreen = new GameScreen(this);
		mainmenuscreen = new mainmenuscreen(this);
		entryscreen = new EntryScreen(this);
		tankSelectScreen = new TankSelectScreen(this);
		pausescreen=new PauseScreen(this,gamescreen);
		winnerscreen1 = new Winnerscreen1(this);
		winnerscreen2 = new Winnerscreen2(this);


		gameintro = Gdx.audio.newMusic(Gdx.files.internal("gameintro.mp3"));
		gameintro.setLooping(true);


		this.setScreen(entryscreen);


	}

	public GameScreen getGameScreen(){
		if (gamescreen==null){
			gamescreen=new GameScreen(this);
		}
		return gamescreen;
	}

	public mainmenuscreen getmainmenuscreen(){
		if (mainmenuscreen==null){
			gamescreen=new GameScreen(this);
		}
		return mainmenuscreen;
	}

	public EntryScreen getentryscreen(){
		if(entryscreen==null){
			entryscreen=new EntryScreen(this);
		}
		return entryscreen;
	}

	public TankSelectScreen gettankselectscreen(){
		if(tankSelectScreen==null){
			tankSelectScreen=new TankSelectScreen(this);
		}
		return tankSelectScreen;
	}

	public PauseScreen getPausescreen(){
		if(pausescreen==null){
			pausescreen=new PauseScreen(this,gamescreen);
		}
		return pausescreen;
	}


	public void updatescreens(){
		gamescreen=new GameScreen(this);
		pausescreen = new PauseScreen(this,gamescreen);
	}

	private void initfonts(){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arcon.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

		params.size=24;
		params.color= Color.YELLOW ;
		font = generator.generateFont(params);
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		assets.dispose();
		gamescreen.dispose();
		mainmenuscreen.dispose();
		entryscreen.dispose();

//		img.dispose();

	}
}
