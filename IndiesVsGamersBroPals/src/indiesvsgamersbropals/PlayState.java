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
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;

/**
 * The main play state
 * @author Kevin
 */
public class PlayState extends GameState {

    private final String WORLD_FILE_PATH = "assets/data/world/world.txt";
    private final String[] QUESTS_FILES = {
        "assets/data/quest/test.txt"
    };
    private int questOn;
    
    private SwordEntity player;
    private GameWorld<BaseEntity> world;
    private EnemyManager enemyManager;
    private WorldBuilder builder;
    
    private boolean w, a, s, d;
    private int currentSceneX, currentSceneY;
    
    // values important to score
    private long gameTime;
    private int gold;
    
    @Override
    public void update(int i) {
        // update the game time
        gameTime += i;
        
        // don't continue if the player or the world is not yet there.
        if (player == null || world == null)
            return;
        
        // update the player's velocity for moving
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
        
        int screenWidth = 800;
        int screenHeight = 600;
        
        // check if the player is about to leave the scene and load 
        // a new scene ifneeded
        
        // left
        if (player.getX() < -(player.getWidth()/2) - 2 && 
                builder.sceneInBounds(currentSceneX - 1, currentSceneY)) {
            loadScene(currentSceneX, currentSceneY - 1);
            player.setX(player.getX() + screenWidth);
        // right
        } else if (player.getX() > screenWidth - (player.getWidth()/2) + 2 && 
                builder.sceneInBounds(currentSceneX + 1, currentSceneY)) {
            loadScene(currentSceneX, currentSceneY + 1);
            player.setX(player.getX() - screenWidth);
        // top
        } else if (player.getY() < -(player.getHeight()/2) - 2 && 
                builder.sceneInBounds(currentSceneX, currentSceneY - 1)) {
            loadScene(currentSceneX - 1, currentSceneY);
            player.setY(player.getY() + screenHeight);
        // down
        }  else if (player.getY() > screenHeight - (player.getHeight()/2) + 2 && 
                builder.sceneInBounds(currentSceneX, currentSceneY + 1)) {
            loadScene(currentSceneX + 1, currentSceneY);
            player.setY(player.getY() - screenHeight);
        }
        
        // update the entities
        for (BaseEntity ent : world.getEntities()) {
            if (ent.getParent() != world || ent.getParent() == null) {
                continue;
            }
            
            ent.update(i);
            // remove enemies from the enemy manager as they die
            if (ent.getParent() == null && ent != player && ent instanceof SwordEntity) {
                enemyManager.removeEnemy(currentSceneX, currentSceneY, (SwordEntity)ent);
                world.getEntities().remove(ent);
            }
        }
    }

    @Override
    public void render(Object o) {
        Graphics2D g2 = (Graphics2D) o;
        
        if (player== null || world == null) {
            g2.drawString("Loading... ", 100, 100);
            return;
        }
        
        // draw the background
        if (builder.getLastBackgroundImage() == null) {
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, 800, 600);
        } else {
            g2.drawImage(builder.getLastBackgroundImage(), 0, 0, null);
        }
        
        // draw all entities that are in the world
        for (BaseEntity ent : world.getEntities()) {
            if (ent.getParent() != world) {
                continue;
            }
            
            ent.render(o);
        }
        
        g2.setFont(new Font("Arial", Font.PLAIN, 32));
        g2.setColor(Color.WHITE);
        // display the gold that you earned so far
        g2.drawString("Gold: " + gold, 10, 40);
        
        // display the time that has passed
        g2.drawString("Time: " + getTimePassedString(), 10, 80);
    }

    @Override
    public void onEnter() {
        gold = 0; // start with no gold
        builder = new WorldBuilder(new File(WORLD_FILE_PATH));
        enemyManager = new EnemyManager();
        // load the quest before the scene

        // set up the player
        SwordEntityFactory factory = new SwordEntityFactory(getAssetManager());
        player = factory.makePlayer();
        player.setX(builder.getSpawnPosX());
        player.setY(builder.getSpawnPosY());

        // create controllers
        addController(new PlayerControls(player));
        
        // load the first quest
        loadQuest(0);
        // the spawn scene
        loadScene(builder.getSpawnSceneX(), builder.getSpawnSceneY());
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
                    d=bln;
                    break;
            }
        }

        @Override
        public void mouse(int i, int i1, int i2, boolean bln) {
        }
        
    }
    
    private void loadScene(int posX, int posY) {
        if (!builder.sceneInBounds(posX, posY)) {
            System.err.println("The scene at the position (" + posX + ", " + posY + ") is not valid");
            return;
        }
        GameWorld lastWorld = world;
        world = builder.buildWorld(this, posX, posY);
        if (world != null) {
            currentSceneX = posX;
            currentSceneY = posY;
        } else {
            // restore the old world without deleting
            world = lastWorld;
            return;
        }
        
        // add the player to the new scene
        world.addEntity(player);
        player.setParent(world);
        
        // add the enemies to the scene
        ArrayList<SwordEntity> enemies = enemyManager.getEnemies(posX, posY);
        for (int i=0; i<enemies.size(); i++) {
            if (!world.getEntities().contains(enemies.get(i))) {
                System.out.println("Added an enemy to the scene: "+posX+", "+posY);
                world.getEntities().add(enemies.get(i));
                enemies.get(i).setParent(world);
            }
        }
    }
    
    /**
     * Loads the enemies of the given quest
     * @param questNumber 
     */
    private void loadQuest(int questNumber) {
        questOn = questNumber;
        builder.spawnEnemiesForQuest(new File(QUESTS_FILES[questOn]), enemyManager, getAssetManager());
    }
    
    private String getTimePassedString() {
        int secondsPassed = (int)(gameTime / 1000);
        int minutesPassed = (int)(secondsPassed / 60);
        secondsPassed = secondsPassed - (minutesPassed * 60);
        String prefix = "";
        if (secondsPassed < 10) {
            prefix = "0";
        }
        return minutesPassed + ":" + prefix + secondsPassed;
    }
}
