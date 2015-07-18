/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.entity.GameWorld;

/**
 * A factory to build sword entities.
 * @author Kevin
 */
public class SwordEntityFactory {

    /**
     * make a player at origin
     * @param parent The player's parent
     * @return The player entity at origin
     */
    public SwordEntity makePlayer(GameWorld parent) {
        SwordEntityComponent body = new SwordEntityComponent(0, 0, 50, 50,false);
        SwordEntityComponent[] sword = {new SwordEntityComponent(50, 20, 30, 10, true)};
        return new SwordEntity(body, sword);
    }
    
    public SwordEntity makeGuardEnemy() {
        // TODO actually amke this make the proper enemy. THIS IS PLACEHOLDER
        SwordEntityComponent body = new SwordEntityComponent(0, 0, 50, 50,false);
        SwordEntityComponent[] sword = {new SwordEntityComponent(50, 20, 30, 10, true)};
        return new SwordEntity(body, sword);
    }
}
