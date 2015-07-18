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
    
    public EnemyManager() {
        enemiesSave = new HashMap<>();
    }
    
    private String getKey(int sceneX, int sceneY) {
        return sceneX + "_" + sceneY;
    }
    
    /**
     * Save the enemy to the enemies
     * @param sceneX The secene's y position
     * @param sceneY The scene's x position
     * @param enemy The enemy to save
     */
    public void saveEnemy(int sceneX, int sceneY, SwordEntity enemy) {
        String location = getKey(sceneX, sceneY);
        if (enemiesSave.get(location) == null) {
            enemiesSave.put(location, new ArrayList<>());
        }
        enemiesSave.get(location).add(enemy);
    }
    
    public ArrayList<SwordEntity> getEnemies(int sceneX, int sceneY) {
        String location = getKey(sceneX, sceneY);
        if (enemiesSave.get(location) == null) {
            enemiesSave.put(location, new ArrayList<>());
        }
        return enemiesSave.get(location);
    }
    
    public void removeEnemy(int sceneX, int sceneY, SwordEntity enemy) {
        String location = getKey(sceneX, sceneY);
        if (enemiesSave.get(location) == null) {
            return;
        }
        enemiesSave.get(location).remove(enemy);
    }
    
}
