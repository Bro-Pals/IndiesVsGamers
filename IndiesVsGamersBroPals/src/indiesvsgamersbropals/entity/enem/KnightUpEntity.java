/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity.enem;

import indiesvsgamersbropals.entity.SwordEntity;
import indiesvsgamersbropals.entity.SwordEntityComponent;

/**
 *
 * @author Kevin
 */
public class KnightUpEntity extends SwordEntity {

    private SwordEntity player;
    
    public KnightUpEntity(SwordEntityComponent body, 
            SwordEntityComponent[] swords) {
        super(body, swords);
    }
    
    public void givePlayer(SwordEntity player) {
        this.player = player;
    }

}
