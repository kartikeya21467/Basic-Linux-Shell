package com.mygdx.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
public class mainmenuscreen implements Screen , Serializable {
    private final TankStars game;

    private Stage stage;
    private Skin skin;

    private OrthographicCamera camera;


    private Image playbutton;
    private Image resumebutton;
    private Image exitbutton;
    private Image background;


    TextButton play;



    public mainmenuscreen(final TankStars game){
        this.game = game;


        this.stage = new Stage(new StretchViewport(game.Width,game.Height,game.camera));

        camera = new OrthographicCamera();
        camera.setToOrtho(false,1600,900);

        background = new Image(new Texture("mainmenubackground.png"));
        playbutton = new Image(new Texture("playbutton.png"));
        resumebutton = new Image(new Texture("resumebutton.png"));
        exitbutton = new Image(new Texture("exitbutton.png"));

        stage.addActor(background);
        stage.addActor(playbutton);
        stage.addActor(resumebutton);
        stage.addActor(exitbutton);






    }

    @Override
    public void show() {
        game.gameintro.play();
        Gdx.input.setInputProcessor(stage);
        background.setPosition(0,0);
        background.setSize(1600,900);

        background.addAction(sequence(alpha(0.3f),fadeIn(0.3f)));
        playbutton.setPosition(1115,500); playbutton.setSize(295,95);
        resumebutton.setPosition(1115,380); resumebutton.setSize(295,95);
        exitbutton.setPosition(1115,260); exitbutton.setSize(295,95);


        this.skin = new Skin();
        this.skin.addRegions(game.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font",game.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));



        initButtons();
    }


    private void initButtons(){

        playbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y) {
                game.setScreen(game.gettankselectscreen());
//                game.gamescreen=new GameScreen(game);
//                gameintro.stop();
            }
        });

        resumebutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                try{
                    loadgame();
                }catch (Exception e){
                    System.out.println("Error");
                }

            }
        });

        exitbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y) {
                Gdx.app.exit();
            }
        });


    }

    public void loadgame() throws ClassNotFoundException, IOException {
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream("Savegame1.txt"));
            TankStars lgame = (TankStars) in.readObject();
            game.setScreen(lgame.gamescreen);
        }finally{
            if(in!=null){
                in.close();
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.5f,1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        update(delta);
        stage.draw();
        game.batch.begin();

        game.batch.end();

    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();

    }
}
