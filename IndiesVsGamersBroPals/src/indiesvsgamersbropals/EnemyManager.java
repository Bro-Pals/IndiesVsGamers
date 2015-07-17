/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals;

import indiesvsgamersbropals.entity.SwordEntity;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Kevin
 */
public class EnemyManager {

    HashMap<Point, ArrayList<SwordEntity>> enemiesSave;
    
    /**
     * Save the enemy to the enemies
     * @param sceneX The secene's y position
     * @param sceneY The scene's x position
     * @param enemy The enemy to save
     */
    public void saveEnemy(int sceneX, int sceneY, SwordEntity enemy) {
        
    }
    
    public ArrayList<SwordEntity> getEnemies(int sceneX, int sceneY) {
        ArrayList<SwordEntity> entities = new ArrayList<>();
        
        return entities;
    }
    
}
