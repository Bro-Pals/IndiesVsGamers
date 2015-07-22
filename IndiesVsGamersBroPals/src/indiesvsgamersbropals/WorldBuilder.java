/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals;

import bropals.lib.simplegame.animation.Animation;
import bropals.lib.simplegame.animation.Track;
import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.TexturedBlock;
import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.state.GameState;
import indiesvsgamersbropals.entity.HazardBlock;
import indiesvsgamersbropals.entity.SwordEntity;
import indiesvsgamersbropals.entity.SwordEntityFactory;
import indiesvsgamersbropals.entity.enem.GuardEntity;
import indiesvsgamersbropals.entity.enem.KnightDownEntity;
import indiesvsgamersbropals.entity.enem.WhiteGhostEntity;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

/**
 * Takes a file and builds it into a world
 * @author Kevin
 */
public class WorldBuilder {
    
    /**
     * The world file to read data from
     */
    private File file;
    private int worldWidth; // how many scenes wide the world is
    private int worldHeight; // how many scenes high the world is
    private int spawnSceneX, spawnSceneY, spawnPosX, spawnPosY, goalSceneX, goalSceneY;
    private BufferedImage lastBackgroundImage;
    private SwordEntity goalEnemy;
    
    public WorldBuilder(File worldFile) {
        file = worldFile;
        worldHeight = 0;
        worldWidth = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            String input = "";
            while ((input = reader.readLine()) != null) {
                if (input.length() == 0) {
                   continue;
                }
                
                if (input.startsWith("ROW")) {
                    String[] tokens = input.split(" ");
                    worldWidth = Integer.parseInt(tokens[1]);
                } else if (input.startsWith("COL")) {
                    String[] tokens = input.split(" ");
                    worldHeight = Integer.parseInt(tokens[1]);
                } else if (input.startsWith("PLAYER_SPAWN")){
                    String[] tokens = input.split(" ");
                    spawnSceneX = Integer.parseInt(tokens[1]);
                    spawnSceneY = Integer.parseInt(tokens[2]);
                    spawnPosX = Integer.parseInt(tokens[3]);
                    spawnPosY = Integer.parseInt(tokens[4]);
                }
            }
        
            reader.close();
            
        } catch(FileNotFoundException fnfe) {
            System.err.println("Could not find the file " + file);
        } catch(Exception e) {
            System.err.println(e);
        }
    }
    
    public boolean sceneInBounds(int posX, int posY) {
        return posX >= 0 && posX < worldWidth && posY >= 0 && posY < worldHeight;
    }
    
    /**
     * Get the scene at the given position.
     * @param stateToBuildFor The game state to load the scene for
     * @param posX The x position of the scene.
     * @param posY LThe y position of the scene
     * @return The scene, or null if there was an error with loading it
     */
    public GameWorld<BaseEntity> buildWorld(GameState stateToBuildFor, int posX, int posY) {
        System.out.println("world width: " + worldWidth);
        System.out.println("world height: " + worldHeight);
        System.out.println("Scene position: " + posX + ", " + posY);
        if (!sceneInBounds(posX, posY)) {
            System.err.println("The scene location is outside of the given bounds");
            return null;
        }
        GameWorld<BaseEntity> world = new GameWorld<>(stateToBuildFor);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            String input = "";
            int line = 0;
            boolean onScene = false;
            while ((input = reader.readLine()) != null) {
                line++;
                if (input.length() == 0) {
                   continue;
                }
//                System.out.println("Reading line: " + input);

                if (onScene) {
                    if (input.startsWith("SCENE")) {
                        // if you were already on a scene, then finish it
                        return world;
                    } else if (input.startsWith("BLOCK") || input.startsWith("HAZARD")) {
                        String[] tokens = input.split(" ");
                        int x = Integer.parseInt(tokens[1]);
                        int y = Integer.parseInt(tokens[2]);
                        int width = Integer.parseInt(tokens[3]);
                        int height = Integer.parseInt(tokens[4]);
                        TexturedBlock block;
                        if (input.startsWith("HAZARD")) {
                            block = new HazardBlock(world, x, y, width, height);
                        } else {
                            block = new TexturedBlock(world, x, y, width, height);
                        }
                        world.addEntity(block);
                        block.setParent(world);
                        block.setAnchored(true);
                        if (tokens[5].equals("I")) {
                            block.setImage(stateToBuildFor.getAssetManager().getImage(tokens[6]));
                        } else if (tokens[5].equals("A")) {
                            Animation anim = new Animation();
                            anim.addTrack(new Track(
                                    stateToBuildFor.getAssetManager().getImage(tokens[6]), 
                                    Integer.parseInt(tokens[7]), 
                                    Integer.parseInt(tokens[8]), 
                                    Integer.parseInt(tokens[9])));
                            anim.setTrack(0);
                            block.setAnimation(anim);
                        } else {
                            System.err.println("Parsing error");
                            throw new Exception("Parsing error with the line line '" + line + "'");
                        }
                    } else if (input.startsWith("BACKGROUND")) {
                        String[] tokens = input.split(" ");
                        lastBackgroundImage = stateToBuildFor.getAssetManager().getImage(tokens[1]);
                    }
                } else {
                    // find the scene you're looking for
                    if (input.equals("SCENE " + posX + " " + posY)) {
                        onScene = true;
                        System.out.println("Found the scene. input: '" + input + "'");
                    }
                }
                
            }
        
            reader.close();
            
            return world;
        } catch(FileNotFoundException fnfe) {
            System.err.println("Could not find the file " + file);
        } catch(Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return null;
    }

    public void spawnEnemiesForQuest(File questFile, EnemyManager manager, 
            AssetManager assetManager, SwordEntity player) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(questFile));
            SwordEntityFactory factory = new SwordEntityFactory(assetManager);
            
            // delete any previous scene goal
            goalSceneX = -1;
            goalSceneY = -1;
            
            String input = "";
            while ((input = reader.readLine()) != null) {
                if (input.length() == 0) {
                   continue;
                }
//                System.out.println("reading: " + input);
                
                if (input.startsWith("SPAWN") || input.startsWith("SPAWN_GOAL")) {
                    String[] tokens = input.split(" ");
                    String enemyType = tokens[1];
                    int sceneY = Integer.parseInt(tokens[2]);
                    int sceneX = Integer.parseInt(tokens[3]);
                    int x = Integer.parseInt(tokens[4]);
                    int y = Integer.parseInt(tokens[5]);
                    System.out.println("position: " + x + ", " + y);
                    // use the factory to make a new enemy
                    // add it to the enemy manager
                    SwordEntity entity = null;
                    if (enemyType.equals("GUARD")) {
                        entity = factory.makeGuardEnemy(x, y);
                        System.out.println("added a guard to the scene");
                    } else if (enemyType.equals("KNIGHT_D")) {
                        entity = factory.makeKnightDownEnemy(x, y);
                        System.out.println("added a knight facing down to the scene");
                    } else if (enemyType.equals("KNIGHT_U")) {
                        entity = factory.makeKnightUpEnemy(x, y);
                        System.out.println("added a knight facing up to the scene");
                    } else if (enemyType.equals("ROGUE")) {
                        entity = factory.makeRogueEnemy(x, y);
                        System.out.println("added a rogue to the scene");
                    } else if (enemyType.equals("WHITE_GHOST")) {
                        entity = factory.makeWhiteGhostBoss(x, y);
                        System.out.println("added a white ghost to the scene");
                    }
                    
                    if (entity != null) {
                        // give the enemy the reference to the player
                        // and save them to the enemy manager
                        entity.givePlayer(player);
                        manager.saveEnemy(sceneX, sceneY, entity);
                    }
                    
                    // set up the goal if this enemy was the goal
                    if (entity != null && input.startsWith("SPAWN_GOAL")) {
                        goalSceneX = sceneX;
                        goalSceneY = sceneY;
                        goalEnemy = entity;
                    }
                }
            }
        
            reader.close();
        } catch(FileNotFoundException fnfe) {
            System.err.println("Could not find the file " + questFile);
        } catch(Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
    
    public int getSpawnSceneX() {
        return spawnSceneX;
    }

    public int getSpawnSceneY() {
        return spawnSceneY;
    }
    
    public int getSpawnPosY() {
        return spawnPosY;
    }

    public int getSpawnPosX() {
        return spawnPosX;
    }

    public int getGoalSceneX() {
        return goalSceneX;
    }

    public int getGoalSceneY() {
        return goalSceneY;
    }

    public SwordEntity getGoalEnemy() {
        return goalEnemy;
    }

    
    
    public BufferedImage getLastBackgroundImage() {
        return lastBackgroundImage;
    }
    
    
}
