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

    HashMap<String, ArrayList<SwordEntity>> enemiesSave;
    
    /**
     * Save the enemy to the enemies
     * @param sceneX The secene's y position
     * @param sceneY The scene's x position
     * @param enemy The enemy to save
     */
    public void saveEnemy(int sceneX, int sceneY, SwordEntity enemy) {
        String location = sceneX + "_" + sceneY;
        enemiesSave.get(location).add(enemy);
    }
    
    public ArrayList<SwordEntity> getEnemies(int sceneX, int sceneY) {
        String location = sceneX + "_" + sceneY;
        return enemiesSave.get(location);
    }
    
}
