package com.mygdx.tankstars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.Serializable;

public class EntryScreen implements Screen, Serializable {

    private final TankStars game;

    private Texture background;
    private float elapsed;
    private final ShapeRenderer shapeRenderer;
    private Stage stage;

    private float progress;

    public EntryScreen(TankStars game){
        this.game = game;

        this.shapeRenderer = new ShapeRenderer();

        background = new Texture(Gdx.files.internal("entryscreenback.jpg"));

        queueAssets();

    }
    private void queueAssets(){
        game.assets.load("entryscreenback.jpg",Texture.class);
        game.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    }

    @Override
    public void show() {
//        background.setPosition(0,0);
    }

    @Override
    public void render(float delta) {

        update(delta);


        game.batch.begin();
        game.batch.draw(background,0,0,1600,900);
        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch,"LOADING...",580,80);
        game.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(580,100,500,40);

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(580,100,progress *500,40);
        shapeRenderer.end();


    }

    private void update(float delta){
        progress = MathUtils.lerp(progress,game.assets.getProgress(),.1f);
//        System.out.println(progress + " " + game.assets.getProgress());
        if(game.assets.update() && progress+.000001f==game.assets.getProgress()){
            game.setScreen(game.getmainmenuscreen());
        }
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
        shapeRenderer.dispose();
        background.dispose();
    }
}
