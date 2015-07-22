/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity.enem;

import bropals.lib.simplegame.math.Vector2D;
import indiesvsgamersbropals.entity.SwordEntity;
import indiesvsgamersbropals.entity.SwordEntityComponent;

/**
 *
 * @author Kevin
 */
public class RogueEntity extends SwordEntity {

    public RogueEntity(SwordEntityComponent body, 
            SwordEntityComponent[] swords) {
        super(body, swords);
    }
    
    @Override
    public void update(int i) {
        // Try to move above the player and charge down
        
        super.update(i);
    }
    

}
