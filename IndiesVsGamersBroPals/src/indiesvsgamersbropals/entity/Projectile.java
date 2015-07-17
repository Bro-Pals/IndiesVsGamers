/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package indiesvsgamersbropals.entity;

import bropals.lib.simplegame.entity.GameWorld;
import bropals.lib.simplegame.entity.block.TexturedBlock;

/**
 * A projectile that moves and damages
 * @author Kevin
 */
public class Projectile extends TexturedBlock {

    private int damage = 100;
    
    public Projectile(GameWorld parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }

    public int getDamage() {
        return damage;
    }

    
    
}
