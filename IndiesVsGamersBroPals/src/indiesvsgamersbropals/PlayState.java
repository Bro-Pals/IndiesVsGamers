/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indiesvsgamersbropals;

import bropals.lib.simplegame.KeyCode;
import bropals.lib.simplegame.controls.Controller;
import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.state.GameState;
import indiesvsgamersbropals.entity.SwordEntity;
import indiesvsgamersbropals.entity.SwordEntityFactory;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;

/**
 * The main play state
 * @author Kevin
 */
public class PlayState extends GameState {

    private final String WORLD_FILE_PATH = "assets/data/world/test.txt";
    
    private SwordEntity player;
    private GameWorld<BaseEntity> world;
    private EnemyManager enemyManager;
    private WorldBuilder builder;
    private long gameTime;
    private boolean w, a, s, d;
    private int currentSceneX, currentSceneY;
    
    @Override
    public void update(int i) {
        // don't continue if the player or the world is not yet there.
        if (player == null || world == null)
            return;
        
        // update the player
        if (w) {
            player.getDirection().setY(-1); // up
        }
        if (s) {
            player.getDirection().setY(1); // down
        }
        if (!s && !w) {
             player.getDirection().setY(0); // stop up or down
        }
        
        if (a) {
            player.getDirection().setX(-1); // left
        }
        if (d) {
            player.getDirection().setX(1); // right
        }
        if (!a && !d) {
             player.getDirection().setX(0); // stop left or right
        }
        
        // update the entities
        for (BaseEntity ent : world.getEntities()) {
            ent.update(i);
        }
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        
        if (player== null || world == null) {
            g2.drawString("Loading... ", 100, 100);
            return;
        }
        if (builder.getLastBackgroundImage() == null) {
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, 800, 600);
        } else {
            g2.drawImage(builder.getLastBackgroundImage(), 0, 0, null);
        }
        
        g2.setColor(Color.red);
        for (BaseEntity ent : world.getEntities()) {
            ent.render(o);
        }
    }

    @Override
    public void onEnter() {
        builder = new WorldBuilder(new File(WORLD_FILE_PATH));
        // the spawn scene
        loadScene(builder.getSpawnSceneX(), builder.getSpawnSceneY());
        if (world != null) {
            System.out.println("world was made - adding player");
            SwordEntityFactory factory = new SwordEntityFactory();
            player = factory.makePlayer(world);
            player.setX(builder.getSpawnPosX());
            player.setY(builder.getSpawnPosY());
            
            // create controllers
            addController(new PlayerControls(player));
        } else {
            System.err.println("Error with loading the world");
        }
    }

    @Override
    public void onExit() {
    }
 
    class PlayerControls extends Controller {

        private SwordEntity player;
        
        public PlayerControls(SwordEntity player) {
            this.player = player;
        }
        
        @Override
        public void key(int i, boolean bln) {
            if (bln) {
                switch (i) {
                    case KeyCode.KEY_W:
                        w=bln;
                        break;
                    case KeyCode.KEY_A:
                        a=bln;
                        break;
                    case KeyCode.KEY_S:
                        s=bln;
                        break;
                    case KeyCode.KEY_D:
                        d = bln;
                        break;
                }
            }
        }

        @Override
        public void mouse(int i, int i1, int i2, boolean bln) {
        }
        
    }
    
    private void loadScene(int posX, int posY) {
        GameWorld lastWorld = world;
        world = builder.buildWorld(this, posX, posY);
        if (world != null) {
            currentSceneX = posX;
            currentSceneY = posY;
        } else {
            // restore the old world without deleting
            world = lastWorld;
        }
    }
    
}
