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
import bropals.lib.simplegame.math.Vector2D;
import bropals.lib.simplegame.state.GameState;
import indiesvsgamersbropals.entity.SwordEntity;
import indiesvsgamersbropals.entity.SwordEntityFactory;
import indiesvsgamersbropals.screens.DeathScreenState;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    
    private GameJoltDataHolder gameJoltDataHolder;
    
    public PlayState(GameJoltDataHolder holder) {
        gameJoltDataHolder = holder;
    }
    
    @Override
    public void update(int i) {
        if (player.getParent() == null) {
            System.out.println("Oh noes the player died");
            // go to the next screen, passing in important values
            getGameStateRunner().setState(
                    new DeathScreenState(getTimePassedString(), 
                            gameTime, gold, gameJoltDataHolder));
            return;
        }
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
        
        float padding = 10;
        
        // left
        if (player.getCenterX() < - padding) {
            if (builder.sceneInBounds(currentSceneX, currentSceneY - 1)) {
                loadScene(currentSceneX, currentSceneY - 1);
                player.setX(player.getX() + screenWidth);
            } else {
                player.getDirection().setX(0); // stop moving in the x direction
                player.setX(-(player.getWidth()/2));
            }
        // right
        } else if (player.getCenterX() > screenWidth + padding) {
            if (builder.sceneInBounds(currentSceneX, currentSceneY + 1)) {
                loadScene(currentSceneX, currentSceneY + 1);
                player.setX(player.getX() - screenWidth);
            } else {
                player.getDirection().setX(0); // stop moving in the x direction
                player.setX(screenWidth - (player.getWidth()/2));
            }
        // top
        } else if (player.getCenterY() < -padding) {
            if (builder.sceneInBounds(currentSceneX - 1, currentSceneY)) {
                loadScene(currentSceneX - 1, currentSceneY);
                player.setY(player.getY() + screenHeight);
            } else {
                player.getDirection().setY(0); // stop moving in the y direction
                player.setY(-player.getHeight()/2);
            }
        // down
        }  else if (player.getCenterY() > screenHeight + padding) {
            if (builder.sceneInBounds(currentSceneX + 1, currentSceneY)) {
                loadScene(currentSceneX + 1, currentSceneY);
                player.setY(player.getY() - screenHeight);
            } else {
                player.getDirection().setY(0); // stop moving in the y direction
                player.setY(screenHeight - (player.getHeight()/2));
            }
        }
        
        // player gets precedence over everything
        player.update(i);
        
        // update the entities
        for (int h=0; h< world.getEntities().size(); h++) {
            BaseEntity ent = world.getEntities().get(h);
            // player has already updated
            if (ent == player) {
                continue;
            }
//            if (ent.getParent() != world || ent.getParent() == null) {
//                continue;
//            }
            
            if (ent.getParent() != null) {
                ent.update(i);
            }
            
            // remove enemies from the enemy manager as they die
            if (ent.getParent() == null && ent != player && ent instanceof SwordEntity) {
                System.out.println("ENEMY DIED");
                SwordEntity enemy = (SwordEntity)ent;
                enemyManager.removeEnemy(currentSceneX, currentSceneY, enemy);
                world.getEntities().remove(enemy);
                gold += enemy.getGoldAmount(gameTime);
                
                // if you killed the goal enemy..
                if (enemy == builder.getGoalEnemy()) {
                    System.out.println("Completed the quest!");
                    questOn++;
                    if (questOn < QUESTS_FILES.length) {
                        loadQuest(questOn);
                    } else {
                        System.out.println("NO MORE QUESTS TO COMPLETE");
                    }
                }
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
        
        // draw the arrow pointing towards the quest goal
        int dirX = builder.getGoalSceneX() - currentSceneX;
        int dirY = builder.getGoalSceneY() - currentSceneY;
        
        // if you aren't at the goal scene draw the arrow
        if (!(dirX == 0 && dirY == 0) && builder.getGoalSceneX() != -1 && 
                builder.getGoalSceneY() != -1) {
            Vector2D goalDirection = new Vector2D(dirX, dirY);
            goalDirection.normalizeLocal();
            double angle = Math.acos(goalDirection.getX());
            if (dirY > 0) {
                angle *= -1; //reverse angle direction
            }
            float distance = 100;
            float arrowX = player.getCenterX() - (float)(Math.sin(angle) * distance);
            float arrowY = player.getCenterY() + (float)(Math.cos(angle) * distance);
            
            g2.translate(arrowX, arrowY);
            g2.rotate(angle);
            BufferedImage arrowImage = getAssetManager().getImage("arrow");
            g2.drawImage(arrowImage, -arrowImage.getWidth()/2, -arrowImage.getHeight()/2, null);
            g2.rotate(-angle);
            g2.translate(-arrowX, -arrowY);
            
        }
        
        g2.setFont(new Font("Arial", Font.PLAIN, 32));
        g2.setColor(Color.WHITE);
        // display the gold that you earned so far
        g2.drawString("Gold: " + gold, 10, 40);
        
        // display the time that has passed
        g2.drawString("Time: " + getTimePassedString(), 10, 80);
        
        // developer things
        g2.drawString("Scene: [" + currentSceneY + ", " + currentSceneX + "]", 10, 120);
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
        builder.spawnEnemiesForQuest(new File(QUESTS_FILES[questOn]), enemyManager,
                getAssetManager(), player);
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
