package com.mygdx.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.*;
import java.util.Arrays;


public class PauseScreen implements Screen, Serializable {


    final TankStars game;
    private GameScreen gscreen;
    private Stage stage;
    private Image tr;
    private Texture b;
    private Image pausescreen;
    private Image pausebutton;
    private OrthographicCamera camera;
    private Image resumebutton;
    private Image savebutton;
    private Image exitbutton;

    PauseScreen(TankStars game , GameScreen gamescreen){
        System.out.println("Entered from 2nd constructor");
        this.game=game;
        this.gscreen=gamescreen;

        stage = new Stage(new StretchViewport(game.Width,game.Height,game.camera));

        pausescreen = new Image(new Texture(Gdx.files.internal("pausescreen.png")));
        pausebutton = new Image(new Texture(Gdx.files.internal("pausebutton.png")));
        resumebutton = new Image(new Texture(Gdx.files.internal("resumebutton.png")));
        savebutton = new Image(new Texture(Gdx.files.internal("savebutton.png")));
        exitbutton = new Image(new Texture(Gdx.files.internal("exitbutton.png")));
        stage.addActor(pausebutton);
        stage.addActor(pausescreen);
        stage.addActor(resumebutton);
        stage.addActor(savebutton);
        stage.addActor(exitbutton);
    }


    @Override
    public void show() {
//        tr.setPosition(300,300);
//        tr.setSize(400,400);
        Gdx.input.setInputProcessor(stage);
        pausescreen.setPosition(500,50);
        pausescreen.setSize(600,800);
        pausebutton.setPosition(25,810); pausebutton.setSize(60,60);
        resumebutton.setPosition(630,570); resumebutton.setSize(350,120);
        savebutton.setPosition(630,400); savebutton.setSize(350,120);
        exitbutton.setPosition(630,230); exitbutton.setSize(350,120);



        initButtons();
    }

    public void initButtons() {

        pausebutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
//                stage.addActor(pausewindow);
                game.setScreen(gscreen);   // handle thissssss
            }
        });

        resumebutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event , float x, float y){
                System.out.println("Clicked resume");
                game.setScreen(gscreen);
            }
        });

        exitbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event , float x, float y){
                System.out.println("Clicked exit");
                gscreen.getMusic().stop();
                game.setScreen(game.getmainmenuscreen());
            }
        });

        savebutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x,float y){
                try{
                    savegame();
                }catch(IOException e){
                    System.out.println(e.getMessage());
                    System.out.println(Arrays.toString(e.getStackTrace()));
                    System.out.println("Error");
                }
            }
        });
    }

    public void savegame() throws IOException {
        ObjectOutputStream out = null;
        try{
            System.out.println("Save clicked");
            out = new ObjectOutputStream(new FileOutputStream("Savegame2.txt"));
            out.writeObject(game);
        }finally{
            if(out!=null){
                out.close();
            }
        }
    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0,0,1,1);
//        camera.update();
        stage.draw();
        game.batch.setProjectionMatrix(game.camera.combined);
//        game.batch.begin();
//        game.batch.draw(b,200,200,200,200);
//        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

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
