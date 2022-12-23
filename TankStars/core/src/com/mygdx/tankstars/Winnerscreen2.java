package com.mygdx.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Winnerscreen2 implements Screen {

    private TankStars game;

    private Image exitbutton;
    private Image background;

    private final Stage stage;

    public Winnerscreen2(TankStars game){
        this.game=game;
        this.stage = new Stage(new StretchViewport(game.Width,game.Height,game.camera));

        exitbutton = new Image(new Texture(Gdx.files.internal("exitbutton.png")));
        background = new Image(new Texture(Gdx.files.internal("winner2.png")));

        stage.addActor(background);
        stage.addActor(exitbutton);


    }
    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        background.setPosition(0,0);
        background.setSize(1600,900);

        background.addAction(sequence(alpha(0.6f),fadeIn(0.6f)));
        exitbutton.setPosition(1115,260); exitbutton.setSize(295,95);

        exitbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y) {

                game.setScreen(game.getmainmenuscreen());
            }
        });


    }

    @Override
    public void render(float delta) {
        stage.draw();
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
