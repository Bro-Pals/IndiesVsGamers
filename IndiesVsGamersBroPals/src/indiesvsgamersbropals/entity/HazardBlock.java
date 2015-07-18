/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.TexturedBlock;

/**
 *
 * @author Kevin
 */
public class HazardBlock extends TexturedBlock {

    private int damage;
    
    public HazardBlock(GameWorld parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
        damage = 1;
    }

    public int getDamage() {
        return damage;
    }


}
