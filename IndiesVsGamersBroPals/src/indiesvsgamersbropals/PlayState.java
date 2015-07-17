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
import java.io.File;

/**
 * The main play state
 * @author Kevin
 */
public class PlayState extends GameState {

    private final String WORLD_FILE_PATH = "data/world/test/text";
    
    private SwordEntity player;
    private GameWorld<BaseEntity> world;
    private EnemyManager enemyManager;
    private WorldBuilder builder;
    private long gameTime;
    private boolean w, a, s, d;
    
    @Override
    public void update(int i) {
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
        
        for (BaseEntity ent : world.getEntities()) {
            ent.update(i);
        }
    }

    @Override
    public void render(Object o) {
    }

    @Override
    public void onEnter() {
        builder = new WorldBuilder(new File(WORLD_FILE_PATH));
        // the spawn scene
        world = builder.buildWorld(this, builder.getSpawnSceneX(), builder.getSpawnSceneY());
        
        if (world != null) {

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
                        
                        break;
                    case KeyCode.KEY_A:
                        player.getDirection().setX(-1); // left
                        break;
                    case KeyCode.KEY_S:
                        player.getDirection()
                        break;
                    case KeyCode.KEY_D:
                        player.getDirection().setX(1); // right
                        break;
                }
            }
        }

        @Override
        public void mouse(int i, int i1, int i2, boolean bln) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
}
