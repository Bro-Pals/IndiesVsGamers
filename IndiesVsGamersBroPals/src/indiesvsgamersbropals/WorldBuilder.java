/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals;

import bropals.lib.simplegame.entity.BaseEntity;
import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.state.GameState;
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
    private int spawnSceneX, spawnSceneY, spawnPosX, spawnPosY;
    
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
                } else {
                    continue;
                }
            }
        
            reader.close();
            
        } catch(FileNotFoundException fnfe) {
            System.err.println("Could not find the file " + file);
        } catch(Exception e) {
            System.err.println(e);
        }
    }
    
    /**
     * Get the scene at the given position.
     * @param stateToBuildFor The game state to load the scene for
     * @param posX The x position of the scene.
     * @param posY LThe y position of the scene
     * @return The scene, or null if there was an error with loading it
     */
    public GameWorld<BaseEntity> buildWorld(GameState stateToBuildFor, int posX, int posY) {
        GameWorld<BaseEntity> world = new GameWorld<>(stateToBuildFor);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            String input = "";
            while ((input = reader.readLine()) != null) {
                if (input.length() == 0) {
                   continue;
                }
                
                if (input.startsWith("SPAWN")) {
                    
                }
            }
        
            reader.close();
            
            return world;
        } catch(FileNotFoundException fnfe) {
            System.err.println("Could not find the file " + file);
        } catch(Exception e) {
            System.err.println(e);
        }
        return null;
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
    
    
}
