package com.mygdx.tankstars;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.Serializable;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GameScreen implements Screen, Serializable {
    private final TankStars game;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private float speed = 100000;
    private Vector2 movement = new Vector2();

    private Array<Body> tmpbodies = new Array<>();
    private Sprite p2sprite;
    private Sprite p1sprite;

    private Sprite bsprite;
    private final Stage stage;
    private OrthographicCamera camera;

    private InputMultiplexer multiplexer;

    private final ShapeRenderer shapeRenderer;

    private Image background;

    private Image pausebutton;
    private Image pausewindow;

    private Image terrain;
    private Image firebutton;

    private Image aim1;
    private Image aim2;

    private Sound tankshootsound;
    private Music ingamemusic;

    private Rectangle tanktiger;

//    private Tank player1;
//    private Tank player2;
//    private Body box;
//    private Body ball;

    private float progress;

    private int aim1phelper=0;
    private int aim2phelper=0;

    private boolean tank1move=true;
    private boolean tank2move=true;

    private int mv1helper=1;
    private int mv2helper=1;

    private int breaker=0;

    private int Fireturnmanager=0;

    private boolean firebuttonlock=true;

    private Body bullet;

    private BodyDef b= new BodyDef();
    
    private BodyDef p1 = new BodyDef();
    private BodyDef p2 = new BodyDef();
    
    private Body player1;
    private Body player2;

    private Body ground;

    public Music getMusic(){
        return ingamemusic;
    }

    public GameScreen(final TankStars game){
        System.out.println("Entered Gamescreen");
        this.game=game;
        this.shapeRenderer = new ShapeRenderer();
//        this.menuscreen=menuscreen;
//        camera = new graphicCamera();
        this.stage = new Stage(new StretchViewport(game.Width,game.Height,game.camera));

        multiplexer = new InputMultiplexer();

        background = new Image(new Texture(Gdx.files.internal("background.png")));
        terrain = new Image(new Texture(Gdx.files.internal("terrain_1.png")));

//        pausewindow = new Image(new Texture(Gdx.files.internal("playbutton.jpg")));
        pausebutton = new Image(new Texture(Gdx.files.internal("pausebutton.png")));

        firebutton = new Image(new Texture(Gdx.files.internal("Fireyellow.png")));
        aim1 = new Image(new Texture(Gdx.files.internal("aim.png")));
        aim2 = new Image(new Texture(Gdx.files.internal("aim.png")));
        stage.addActor(background);
        stage.addActor(terrain);
        stage.addActor(pausebutton);
        stage.addActor(firebutton);
        stage.addActor(aim1);
        stage.addActor(aim2);



        tankshootsound = Gdx.audio.newSound(Gdx.files.internal("Shootsound.mp3"));
        ingamemusic = Gdx.audio.newMusic(Gdx.files.internal("ingamemusic.mp3"));

        //starting gameintro immediately
        ingamemusic.setLooping(true);


        world = new World(new Vector2(0,-10f),true);

        debugRenderer = new Box2DDebugRenderer();

        handlingbodies();

        world.setContactListener(new Contactlistener(){

            @Override
            public void beginContact(Contact contact) {
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();
                if((a.getBody() == bullet || a.getBody() == player1 || a.getBody()==player2) && (b.getBody() == bullet || b.getBody() == player1 || b.getBody()==player2)){
                    bodyRemove(bullet);
                    if(Fireturnmanager==1) game.player2.setHealth(game.player2.getHealth()-10);
                    if(Fireturnmanager==0) game.player1.setHealth(game.player1.getHealth()-10);
                    if(game.player1.getHealth()==0){
                        getMusic().stop();
                        game.setScreen(game.winnerscreen2);
                    }
                    if(game.player2.getHealth()==0){
                        getMusic().stop();
                        game.setScreen(game.winnerscreen1);
                    }
                    firebuttonlock=true; tank1move=true ; tank2move=true;
                } else if ((a.getBody()==ground || a.getBody()==bullet) && (b.getBody()==ground || b.getBody()==bullet)) {
                    bodyRemove(bullet);
                    firebuttonlock=true; tank1move=true; tank2move=true;
                }


            }

            @Override
            public void endContact(Contact contact) {

            }
        });


    }

    public void bodyRemove(Body temp){
        final Body toRemove = temp;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                world.destroyBody(toRemove);
            }
        });
    }

    public void handlingbodies(){
        //body definition
        
        p1.type = BodyDef.BodyType.DynamicBody;
        p1.position.set(40,280);


        PolygonShape boxshape1 = new PolygonShape();
        boxshape1.setAsBox(30,30);



        //fixture density
        FixtureDef player1F = new FixtureDef();
//        fixtureDef.shape=ballshape;
        player1F.shape = boxshape1;
        player1F.density=1f;
        player1F.friction=0f;
        player1F.restitution = 0f;  //jumping

        player1 =world.createBody(p1);
        player1.createFixture(player1F);

//        ballsprite= new Sprite(new Texture("Spectre_HD.png"));

        System.out.printf("Calling above ballsprite %s\n",this.game.player1.getTank().getName());
        p1sprite = new Sprite(game.player1.getTank().getTanktexture());
        p1sprite.setSize(90,60);
        p1sprite.setOrigin(p1sprite.getWidth()/2,p1sprite.getHeight()/2);
        player1.setUserData(p1sprite);
//        ball.setUserData("Player1");
//        boxshape1.dispose();

        //Ground

        BodyDef bodyDef = new BodyDef();
        //bodydefinition
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,40);

        //ground shape
        ChainShape groundShape = new ChainShape();

        groundShape.createChain(new float[]{0,900,0,200,1600,200,1600,900});

        //fixture definition

        FixtureDef groundF = new FixtureDef();

        groundF.shape=groundShape;
        groundF.friction = 1f;
        groundF.restitution=0;

        ground=world.createBody(bodyDef);
        ground.createFixture(groundF);
        groundShape.dispose();

        //Box

        //bodydefinition


        p2.type = BodyDef.BodyType.DynamicBody;
        p2.position.set(1500,280);

        //boxshape
        PolygonShape boxshape = new PolygonShape();
        boxshape.setAsBox(30,30);


        FixtureDef player2F = new FixtureDef();
        player2F.shape=boxshape;
//        fixtureDef.shape = ballshape1;
        player2F.friction=0f;
        player2F.restitution = 0f;
        player2F.density = 1f;

        player2 = world.createBody(p2);
        player2.createFixture(player2F);

        p2sprite = new Sprite(game.player2.getTank().getTanktexture());
        p2sprite.setSize(90,60);
        p2sprite.setOrigin(p2sprite.getWidth()/2,p2sprite.getHeight()/2);
        player2.setUserData(p2sprite);


//        boxshape.dispose();



    }

    @Override
    public void show() {
//        Gdx.input.setInputProcessor(stage);
        System.out.printf("Calling in show %s\n",game.player1.getTank().getName());
        multiplexer.addProcessor(stage);
        ingamemusic.play();
        background.setPosition(0,0); background.setSize(1600,900);
//        terrain.setPosition(0,0); terrain.setSize(1600,290);
        terrain.setPosition(0,0); terrain.setSize(1600,240);
        pausebutton.setPosition(25,810); pausebutton.setSize(60,60);
//        pausewindow.setPosition(30,30); pausewindow.setSize(200,200);

        firebutton.setPosition(700,80); firebutton.setSize(200,100);



        Gdx.input.setInputProcessor(multiplexer);


        initButtons();

//        handleInput();

    }



    public void initButtons(){

        pausebutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
//                System.out.println("clicked");
//                stage.addActor(pausewindow);
                game.setScreen(game.getPausescreen());
            }
        });

        firebutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event , float x, float y){
                if(firebuttonlock){
                    fire();
                    tankshootsound.play();
                    aim1phelper=0;
                    aim2phelper=0;
                    game.player1.getTank().setFuel(100);
                    tank1move=true;
                    game.player2.getTank().setFuel(100);
                    tank2move=true;
                    if(Fireturnmanager==0) Fireturnmanager=1;
                    else{
                        Fireturnmanager=0;
                    }
                    firebuttonlock=false;
                    tank1move=false; mv1helper=0;
                    tank2move=false; mv2helper=0;
                }

            }

        });



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.5f, 1);
        game.batch.setProjectionMatrix(game.camera.combined);

        if(aim1phelper==0){
            aim1.setPosition(player1.getPosition().x+60,player1.getPosition().y); aim1.setSize(50,50);
        }
        if(aim2phelper==0){
            aim2.setPosition(player2.getPosition().x-110,player2.getPosition().y); aim2.setSize(50,50);
        }



        update(delta);

        stage.draw();
        game.batch.begin();
        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch,"PLAYER1",200,840);
        game.font.draw(game.batch,"PLAYER2",1000,840);
        game.font.draw(game.batch,game.player1.getHealth()+"%",540,840);
        game.font.draw(game.batch,game.player2.getHealth()+"%",1340,840);



        world.getBodies(tmpbodies);
        for(Body body : tmpbodies){
            if(body.getUserData() != null && body.getUserData() instanceof Sprite){
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x-sprite.getWidth()/2,body.getPosition().y-sprite.getHeight()/2);
//                sprite.setRotation(body.getAngle()* MathUtils.radiansToDegrees);
                sprite.draw(game.batch);
            }
        }
        game.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(200,770,400,40);
        shapeRenderer.rect(1000,770,400,40);
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.rect(200,770,4*game.player1.getHealth(),40);

        shapeRenderer.rect(1000,770,4*game.player2.getHealth(),40);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(100,150,200,30);
        shapeRenderer.rect(1300,150,200,30);

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(100,150,(float)2*game.player1.getTank().getFuel(),30);
        shapeRenderer.rect(1300,150,(float)2*game.player2.getTank().getFuel(),30);
        shapeRenderer.end();

        game.batch.begin();
        game.font.setColor(Color.BLACK);
        game.font.draw(game.batch,"FUEL PLAYER1",120,175);
        game.font.draw(game.batch,"FUEL PLAYER2",1320,175);
        game.batch.end();



        debugRenderer.render(world,game.camera.combined);
        world.step(1/60f,6,3);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            ingamemusic.stop();
            stage.dispose();
            game.setScreen(game.getmainmenuscreen());

        }

        handleInput(delta);

    }

    public void update(float delta){
        progress = MathUtils.lerp(progress,game.assets.getProgress(),.1f);
        stage.act(delta);

    }



    public void handleInput(float delta){



        if(player1.getPosition().x>800){
            tank1move=false; mv1helper=1;
        }
        if(player2.getPosition().x<800){
            tank2move=false; mv2helper=1;
        }

        multiplexer.addProcessor(new InputController() {
            @Override
            public boolean keyDown(int keycode) {
                switch(keycode){
                    case Input.Keys.A:
                        if(tank1move && Fireturnmanager==0){
                            player1.applyLinearImpulse(-speed,0,player1.getWorldCenter().x,player1.getWorldCenter().y,true);
                            mv1helper=1;
                        }
                        break;
                    case Input.Keys.D:
                        if(tank1move && Fireturnmanager==0){
                            player1.applyLinearImpulse(speed,0,player1.getWorldCenter().x,player1.getWorldCenter().y,true);
                            mv1helper=1;
                        }
                        break;
                    case Input.Keys.LEFT:
                        if(tank2move && Fireturnmanager==1){
                            player2.applyLinearImpulse(-speed,0,player2.getWorldCenter().x,player2.getWorldCenter().y,true);
                            mv2helper=1;
                        }
                        break;
                    case Input.Keys.RIGHT:
                        if(tank2move && Fireturnmanager==1){
                            player2.applyLinearImpulse(speed,0,player2.getWorldCenter().x,player2.getWorldCenter().y,true);
                            mv2helper=1;
                        }

                        break;

                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode){
                switch(keycode){
                    case Input.Keys.A:
                        if((tank1move || mv1helper==1) && Fireturnmanager==0){
                            player1.applyLinearImpulse(speed,0,player1.getWorldCenter().x,player1.getWorldCenter().y,true);
                            mv1helper=0;
                        }
                        break;
                    case Input.Keys.D:
                        if((tank1move || mv1helper==1) && Fireturnmanager==0){
                            player1.applyLinearImpulse(-speed,0,player1.getWorldCenter().x,player1.getWorldCenter().y,true);
                            mv1helper=0;
                        }
                        break;
                    case Input.Keys.LEFT:
                        if((tank2move || mv2helper==1)&& Fireturnmanager==1){
                            player2.applyLinearImpulse(speed,0,player2.getWorldCenter().x,player2.getWorldCenter().y,true);
                            mv2helper=0;
                        }
                        break;
                    case Input.Keys.RIGHT:
                        if((tank2move || mv2helper==1) && Fireturnmanager==1){
                            player2.applyLinearImpulse(-speed,0,player2.getWorldCenter().x,player2.getWorldCenter().y,true);
                            mv2helper=0;
                        }
                        break;
                }
                return true;
            }



        });


        if((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D))&&Fireturnmanager==0&&tank1move){
            game.player1.getTank().setFuel((int)(game.player1.getTank().getFuel()-0.000000000001*delta));
            if(game.player1.getTank().getFuel()==0){
                tank1move=false;
            }

        }

        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))&&Fireturnmanager==1&&tank2move){
            game.player2.getTank().setFuel((int)(game.player2.getTank().getFuel()-0.000000000001*delta));
            if(game.player2.getTank().getFuel()==0) {
                tank2move=false;
            }
        }


        if(Gdx.input.isKeyPressed(Input.Keys.T)){
            if(Fireturnmanager==0){
                aim1phelper=1;
                aim1.setPosition(aim1.getX(),aim1.getY()+3);
            }else{
                aim2phelper=1;
                aim2.setPosition(aim2.getX(),aim2.getY()+3);
            }

        }
        if(Gdx.input.isKeyPressed(Input.Keys.G)){
            if(Fireturnmanager==0){
                aim1phelper=1;
                aim1.setPosition(aim1.getX(),aim1.getY()-3);
            }else{
                aim2phelper=1;
                aim2.setPosition(aim2.getX(),aim2.getY()-3);
            }

        }
        if(Gdx.input.isKeyPressed(Input.Keys.F)){
            if(Fireturnmanager==0){
                aim1phelper=1;
                aim1.setPosition(aim1.getX()-3,aim1.getY());
            }else{
                aim2phelper=1;
                aim2.setPosition(aim2.getX()-3,aim2.getY());
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.H)){
            if(Fireturnmanager==0){
                aim1phelper=1;
                aim1.setPosition(aim1.getX()+3,aim1.getY());
            }else{
                aim2phelper=1;
                aim2.setPosition(aim2.getX()+3,aim2.getY());
            }
        }

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

        tankshootsound.dispose();
        ingamemusic.dispose();

        world.dispose();
        debugRenderer.dispose();
        p2sprite.getTexture().dispose();
        p1sprite.getTexture().dispose();
    }


    public void fire(){

        //missile

        b.type = BodyDef.BodyType.DynamicBody;
        CircleShape ballshape = new CircleShape();
        ballshape.setRadius(5);

        FixtureDef bulletF = new FixtureDef();
        bulletF.shape = ballshape;
        bulletF.density = 0.3f;
        bulletF.friction = 0.4f;
        bulletF.restitution = 0f;

        if(Fireturnmanager==0) b.position.set(player1.getPosition().x+40, player1.getPosition().y+25);
        if(Fireturnmanager==1) b.position.set(player2.getPosition().x-40,player2.getPosition().y+25);

        bullet = world.createBody(b);
        bullet.createFixture(bulletF);
        if(Fireturnmanager==0) bsprite = new Sprite(new Texture("missile_1.png"));
        else bsprite = new Sprite(new Texture("missile_2.png"));
        bsprite.setSize(20,10);
        bsprite.setOrigin(bsprite.getWidth()/2,bsprite.getHeight()/2);
        bullet.setUserData(bsprite);

        if(Fireturnmanager==0) bullet.applyLinearImpulse((aim1.getX()-player1.getPosition().x)*1000000000, (aim1.getY()-player1.getPosition().y)*1400000000, player1.getPosition().x+40, player1.getPosition().y+25, true);
        if(Fireturnmanager==1) bullet.applyLinearImpulse((aim2.getX()-player2.getPosition().x)*1000000000, (aim2.getY()-player2.getPosition().y)*1500000000, player2.getPosition().x-40, player2.getPosition().y+25, true);



    }
}
