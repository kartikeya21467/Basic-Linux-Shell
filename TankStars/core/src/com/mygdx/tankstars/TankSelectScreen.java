package com.mygdx.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.Serializable;
import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class TankSelectScreen implements Screen , Serializable {
    private final TankStars game;
    final private Stage stage;

    private int tankcount=0;

    private Tank Atomic = new Atomic();
    private Tank Spectre = new Spectre();
    private Tank Tiger = new Tiger();
    private Image button1;
    private Image button2;
    private Image tank;

    private Image background;

    private Image selectbutton;
    private Image backbutton;

    private Image seltext;

    private Texture Tank;
    private BitmapFont font;
    private int animhandler=-1;

    private int playerchoose=1;
    private Tank tankselected = Tiger;

    private ArrayList<Tank> tanks;
    private ArrayList<Tank> tanks2;   // handle this

    public TankSelectScreen(TankStars game){
        this.game=game;
        font=new BitmapFont();
        tanks = new ArrayList<>();
        tanks2 = new ArrayList<>(); // handle this
        tanks.add(Tiger);
        tanks.add(Spectre);
        tanks.add(Atomic);
        this.stage = new Stage(new StretchViewport(game.Width,game.Height,game.camera));

        Tank t = new Tiger();
        t.setTanktexture(new Texture(Gdx.files.internal("Tiger_2.png")));
        tanks2.add(t);
        t=new Spectre();
        t.setTanktexture(new Texture(Gdx.files.internal("Spectre_2.png")));
        tanks2.add(t);
        t=new Atomic();
        t.setTanktexture(new Texture(Gdx.files.internal("Atomic_2.png")));
        tanks2.add(t);



        prepareUI();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
//        game.gameintro.play();


    }

    void prepareUI(){


        background = new Image(new Texture(Gdx.files.internal("tankselectbackground.png")));
        background.setPosition(0,0);
        background.setSize(game.Width,game.Height);
        stage.addActor(background);
        //constructor
        button1 = new Image(new Texture(Gdx.files.internal("nextbutton.png")));
        //show
        button1.setPosition(1450,350);
        button1.setSize(100,150);
        stage.addActor(button1); //const

        button2 = new Image(new Texture(Gdx.files.internal("prevbutton.png")));
        button2.setPosition(970,350);
        button2.setSize(100,150);
        stage.addActor(button2);

        backbutton=new Image(new Texture("prevbutton.png"));
        backbutton.setPosition(72,800);
        backbutton.setSize(40,50);
        stage.addActor(backbutton);

        selectbutton = new Image(new Texture("selectbutton.png"));
        selectbutton.setPosition(1090,110);
        selectbutton.setSize(350,130);
        stage.addActor(selectbutton);

        if(playerchoose==1) seltext = new Image(new Texture(Gdx.files.internal("player1choose.png")));
        else seltext = new Image(new Texture(Gdx.files.internal("player2choose.png")));
        seltext.setPosition(1000,600);
        seltext.setSize(500,100);
        stage.addActor(seltext);

        tank = new Image(tankselected.getTanktexture());

        tank.setSize(500,300);
        tank.setPosition(200,190);
//        tank.addAction(sequence(alpha(0),fadeIn(0.6f)));

        if(animhandler==0){
            tank.setPosition(150,190);
            tank.addAction(parallel(sequence(alpha(0),fadeIn(0.3f)),moveTo(200,190,0.3f)));
        }
        if(animhandler==1){
            tank.setPosition(250,190);
            tank.addAction(parallel(sequence(alpha(0),fadeIn(0.3f)),moveTo(200,190,0.3f)));
        }
        stage.addActor(tank); //const



        //initbuttons
        button1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y) {
                tankcount++;
                animhandler=0;
                if(tankcount==tanks.size()) tankcount=0;

                tankselected=tanks.get(tankcount);
                stage.clear();
                prepareUI();

            }
        });

        button2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y) {
                tankcount--;
                animhandler=1;
                if(tankcount==-1) tankcount=tanks.size()-1;

                int j=0;
                for(Tank t : tanks){
                    if(j==tankcount){
                        tankselected=t;
                    }
                }
//                tankselected=tanks.get(tankcount);
                stage.clear();
                prepareUI();

            }
        });

        backbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y) {

                game.setScreen(game.getmainmenuscreen());

            }
        });

        selectbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y) {
                if(playerchoose==1){
                    seltext=new Image(new Texture("player2text.png"));
                    playerchoose=2;
                    game.player1.setTank(tankselected);
                    System.out.println(game.player1.getTank().getName());
                }else{
                    playerchoose=1;
                    game.player2.setTank(tanks2.get(tankcount));
                    game.gameintro.stop();
//                    game.setScreen(new GameScreen(game));  //edited for pause
//                    game.gamescreen=new GameScreen(game);
//                    game.pausescreen = new PauseScreen(game,game.gamescreen);
                    game.updatescreens();
                    game.player1.getTank().setFuel(100);
                    game.player2.getTank().setFuel(100);
                    game.setScreen(game.gamescreen);

                }
                stage.clear();
                prepareUI();

            }
        });



    }

    @Override
    public void render(float delta) {
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        ScreenUtils.clear(0,0,0,0);

        update(delta);
        stage.draw();
        game.batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(3);
        font.draw(game.batch,tankselected.getName(),350,805);
        font.getData().setScale(2);
        font.draw(game.batch,"100",460,686);
        game.batch.end();


    }

    private void update(float delta){

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
